package br.ufop.stocker.repository.impl;

import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.general.PropertyError;
import br.ufop.stocker.model.*;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.OperacaoRepository;
import br.ufop.stocker.repository.util.DBUtils;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PSQLOperacaoRepository implements OperacaoRepository {

    private static final String INSERT_SQL = "INSERT INTO public.operacao (data, tipo, valor_final, forma_pagamento, id_cliente, id_fornecedor) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ONE_SQL = "SELECT * FROM public.operacao WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.operacao";
    private static final String DELETE_SQL = "DELETE FROM public.operacao WHERE id = ?";

    private final RepositoryFactory factory;

    public PSQLOperacaoRepository(RepositoryFactory factory) {
        this.factory = factory;
    }

    public Operacao findOne(int id) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_SQL))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Operacao operacao = getOperacaoFromResultSet(resultSet);
            operacao.setItens(getOperationItems(operacao));
            operacao.setDebitos(getDebitos(operacao));
            return operacao;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Set<Operacao> findAll() throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getOperations(resultSet);
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    @Override
    public Set<Operacao> findAll(EnumTipoOperacao tipo) throws RepositoryActionException {
        String SELECT_BY_TYPE_SQL = "select * from operacao where tipo = ?";
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_TYPE_SQL))
        {
            preparedStatement.setString(1, tipo.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            return getOperations(resultSet);
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Operacao insert(Operacao value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection())
        {
            connection.setAutoCommit(false);
            double total = 0;
            Set<Produto> produtos = new HashSet<>();
            for (ItemOperacao itemOperacao : value.getItens()) {
                if(value.getTipo() == EnumTipoOperacao.VENDA && itemOperacao.getQuantidade() > itemOperacao.getProduto().getEstoque())
                    throw new RepositoryActionException("Insufficient stock.");
                total += itemOperacao.getQuantidade() * itemOperacao.getProduto().getPreco();
                produtos.add(itemOperacao.getProduto());
                if(value.getTipo() == EnumTipoOperacao.COMPRA)
                    itemOperacao.getProduto().aumentarEstoque(itemOperacao.getQuantidade());
                else
                    itemOperacao.getProduto().diminuirEstoque(itemOperacao.getQuantidade());
            }
            factory.produto().saveAllTransactional(produtos, connection);
            value.setValorFinal(total);
            Operacao operacao = insertOperacao(value, connection);
            assert operacao != null;
            Set<ItemOperacao> itensInseridos = insertItensOperacao(operacao, connection);
            operacao.setItens(itensInseridos);
            // TODO  inserir debitos
            operacao.setCliente(value.getCliente());
            operacao.setFornecedor(value.getFornecedor());
            return operacao;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Set<ItemOperacao> insertItensOperacao(Operacao value, Connection connection) throws RepositoryActionException, SQLException {
        if(value.getItens().isEmpty())
            throw new RepositoryActionException("No itens in sale.");
        StringBuilder builder = new StringBuilder();
        builder.append("(?,?,?),".repeat(value.getItens().size()));
        builder.deleteCharAt(builder.length() - 1);
        String INSERT_OPERATION_ITEMS_SQL = "insert into itemoperacao (quantidade, id_produto, id_operacao) values " +
                builder.toString();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_OPERATION_ITEMS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (ItemOperacao itemOperacao : value.getItens()) {
                preparedStatement.setInt(i, itemOperacao.getQuantidade());
                preparedStatement.setInt(i + 1, itemOperacao.getProduto().getId());
                preparedStatement.setInt(i + 2, value.getId());
                i += 3;
            }
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return getItemOperacaoSetFromResultSet(value, resultSet);
        } catch (SQLException e) {
            connection.rollback();
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Set<ItemOperacao> getItemOperacaoSetFromResultSet(Operacao value, ResultSet resultSet) throws SQLException, RepositoryActionException {
        Set<ItemOperacao> itens = new HashSet<>();
        while(resultSet.next()) {
            ItemOperacao itemOperacao = ItemOperacao.getFromResultSet(resultSet);
            Produto produto = factory.produto().findOne(resultSet.getInt("id_produto"));
            itemOperacao.setOperacao(value);
            itemOperacao.setProduto(produto);
            itens.add(itemOperacao);
        }
        return itens;
    }

    private Operacao insertOperacao(Operacao value, Connection connection) throws SQLException, RepositoryActionException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, value.getData());
            preparedStatement.setString(2, value.getTipo().name());
            preparedStatement.setDouble(3, value.getValorFinal());
            preparedStatement.setString(4, value.getFormaPagamento().name());
            if(value.getTipo() == EnumTipoOperacao.COMPRA) {
                preparedStatement.setObject(5, null);
                preparedStatement.setInt(6, value.getFornecedor().getId());
            }
            else {
                preparedStatement.setInt(5, value.getCliente().getId());
                preparedStatement.setObject(6, null);
            }
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            return rs.next() ? Operacao.getFromResultSet(rs) : null;
        } catch (SQLException e) {
            connection.rollback();
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Operacao update(int id, Operacao value) throws RepositoryActionException {
        throw new RepositoryActionException("Update is not allowed in this repository.");
    }

    public void delete(int id) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Set<Debito> getDebitos(Operacao operacao) throws RepositoryActionException {
        String GET_DEBITS_SQL = "select * from debito where id_operacao = ?";
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_DEBITS_SQL))
        {
            preparedStatement.setInt(1, operacao.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Debito> debitos = new HashSet<>();
            while(resultSet.next()) {
                Debito debito = Debito.getFromResultSet(resultSet);
                debito.setOperacao(operacao);
                debitos.add(debito);
            }
            return debitos;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Set<Operacao> getOperations(ResultSet resultSet) throws SQLException, RepositoryActionException {
        Set<Operacao> operacoes = new HashSet<>();
        while(resultSet.next()) {
            Operacao operacao = getOperacaoFromResultSet(resultSet);
            if(operacao != null) {
                operacao.setItens(getOperationItems(operacao));
                operacao.setDebitos(getDebitos(operacao));
            }
            operacoes.add(operacao);
        }
        return operacoes;
    }

    private Set<ItemOperacao> getOperationItems(Operacao operacao) throws RepositoryActionException {
        String GET_OPERATION_ITEMS_SQL = "select * from itemoperacao where id_operacao = ?";
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_OPERATION_ITEMS_SQL))
        {
            preparedStatement.setInt(1, operacao.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return getItemOperacaoSetFromResultSet(operacao, resultSet);
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Operacao getOperacaoFromResultSet(ResultSet resultSet) throws SQLException, RepositoryActionException {
        Operacao operacao = resultSet.next() ? Operacao.getFromResultSet(resultSet) : null;
        setAssociatedObjects(resultSet, operacao);
        return operacao;
    }

    private void setAssociatedObjects(ResultSet resultSet, Operacao operacao) throws SQLException, RepositoryActionException {
        if(operacao == null)
            return;
        if(operacao.getTipo() == EnumTipoOperacao.VENDA) {
            int idCliente = resultSet.getInt("id_cliente");
            Cliente cliente = factory.cliente().findOne(idCliente);
            operacao.setCliente(cliente);
        }
        else {
            int idFornecedor = resultSet.getInt("id_fornecedor");
            Fornecedor fornecedor = factory.fornecedor().findOne(idFornecedor);
            operacao.setFornecedor(fornecedor);
        }
    }
}
