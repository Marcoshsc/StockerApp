package br.ufop.stocker.repository.impl;

import br.ufop.stocker.general.PropertyError;
import br.ufop.stocker.model.Compra;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;
import br.ufop.stocker.repository.util.DBUtils;

import java.sql.*;
import java.util.*;

public class PSQLFornecedorRepository implements FornecedorRepository {

    private static final String INSERT_SQL = "INSERT INTO public.fornecedor (nome, cnpj, descricao, endereco, telefone, email) "
            + "VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE public.fornecedor SET nome = ?, cnpj = ?, descricao = ?, endereco = ?, telefone = ?, email = ?"
            + " WHERE id = ? RETURNING *";
    private static final String SELECT_ONE_SQL = "SELECT * FROM public.fornecedor WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.fornecedor";
    private static final String DELETE_SQL = "DELETE FROM public.fornecedor WHERE id = ?";

    private final RepositoryFactory factory;

    public PSQLFornecedorRepository(RepositoryFactory factory) {
        this.factory = factory;
    }

    public Fornecedor findOne(int id, boolean complete) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_SQL))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Fornecedor fornecedor =  resultSet.next() ? Fornecedor.getFromResultSet(resultSet) : null;
            if(fornecedor == null)
                return null;
            if(!complete)
                return fornecedor;
            List<Integer> idProdutosFornecidos = getIdProdutosFornecidos(fornecedor);
            Set<Produto> produtosFornecidos = factory.produto().findAllById(idProdutosFornecidos);
            Set<Operacao> operacoes = factory.operacao().findAllByFornecedor(fornecedor);
            Set<Compra> compras = new HashSet<>();
            for (Operacao operacao : operacoes)
                compras.add(new Compra(operacao));
            fornecedor.setCompras(compras);
            fornecedor.setProdutosFornecidos(produtosFornecidos);
            return fornecedor;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Set<Fornecedor> findAll() throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Fornecedor> fornecedores = Fornecedor.getListFromResultSet(resultSet);
            for (Fornecedor fornecedor : fornecedores) {
                List<Integer> idProdutosFornecidos = getIdProdutosFornecidos(fornecedor);
                Set<Produto> produtosFornecidos = factory.produto().findAllById(idProdutosFornecidos);
                fornecedor.setProdutosFornecidos(produtosFornecidos);
                Set<Operacao> operacoes = factory.operacao().findAllByFornecedor(fornecedor);
                Set<Compra> compras = new HashSet<>();
                for (Operacao operacao : operacoes)
                    compras.add(new Compra(operacao));
                fornecedor.setCompras(compras);
            }
            return fornecedores;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }


    public Fornecedor insert(Fornecedor value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection())
        {
            connection.setAutoCommit(false);
            Fornecedor fornecedor = insertFornecedor(value, connection);
            insertProdutosFornecidos(value, connection, fornecedor);
            connection.commit();
            return fornecedor;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Fornecedor update(int id, Fornecedor value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection())
        {
            connection.setAutoCommit(false);
            Fornecedor fornecedor = updateFornecedor(id, value, connection);
            Set<Integer> idProdutosFornecidos = new HashSet<>(getIdProdutosFornecidos(value));
            Set<Produto> alreadyInDatabase = new HashSet<>();
            Set<Produto> toInclude = new HashSet<>();
            for (Produto produto : value.getProdutosFornecidos()) {
                int idProduto = produto.getId();
                if(idProdutosFornecidos.contains(idProduto))
                    alreadyInDatabase.add(produto);
                else
                    toInclude.add(produto);
            }
            Set<Integer> toExclude = new HashSet<>();
            for (Integer productId : idProdutosFornecidos) {
                Produto fakeProduct = new Produto(productId, null, -1, -1, null);
                if(!value.getProdutosFornecidos().contains(fakeProduct) && !toInclude.contains(fakeProduct))
                    toExclude.add(productId);
            }
            assert fornecedor != null;
            deleteProdutosFornecidos(connection, toExclude, fornecedor);
            fornecedor.setProdutosFornecidos(toInclude);
            insertProdutosFornecidos(fornecedor, connection, fornecedor);
            fornecedor.getProdutosFornecidos().addAll(alreadyInDatabase);
            connection.commit();
            Set<Operacao> operacoes = factory.operacao().findAllByFornecedor(fornecedor);
            Set<Compra> compras = new HashSet<>();
            for (Operacao operacao : operacoes)
                compras.add(new Compra(operacao));
            fornecedor.setCompras(compras);
            return fornecedor;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public void delete(int id) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);)
        {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    @Override
    public Set<Fornecedor> findAllByProduto(Produto produto) throws RepositoryActionException {
        String FIND_ALL_BY_PRODUCT_SQL = "select * from fornecedor where id in (select id_fornecedor from produtofornecido where id_produto=?)";
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PRODUCT_SQL))
        {
            preparedStatement.setInt(1, produto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            return Fornecedor.getListFromResultSet(resultSet);
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Fornecedor insertFornecedor(Fornecedor value, Connection connection) throws RepositoryActionException, SQLException {
        try(PreparedStatement insertFornecedorStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            insertFornecedorStatement.setString(1, value.getNome());
            insertFornecedorStatement.setString(2, value.getCnpj());
            insertFornecedorStatement.setString(3, value.getDescricao());
            insertFornecedorStatement.setString(4, value.getEndereco());
            insertFornecedorStatement.setString(5, value.getTelefone());
            insertFornecedorStatement.setString(6, value.getEmail());

            insertFornecedorStatement.executeUpdate();
            ResultSet rs = insertFornecedorStatement.getGeneratedKeys();
            return rs.next() ? Fornecedor.getFromResultSet(rs) : null;
        } catch (SQLException e) {
            connection.rollback();
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private void insertProdutosFornecidos(Fornecedor value, Connection connection, Fornecedor recemInserido) throws SQLException, RepositoryActionException {
        if(value.getProdutosFornecidos().isEmpty())
            return;
        StringBuilder builder = new StringBuilder();
        builder.append("(?,?),".repeat(value.getProdutosFornecidos().size()));
        builder.deleteCharAt(builder.length() - 1);
        String INSERT_PRODUTOS_FORNECIDOS_SQL = "insert into produtofornecido (id_produto, id_fornecedor) values " + builder.toString();
        try(PreparedStatement insertProdutosStatement = connection.prepareStatement(INSERT_PRODUTOS_FORNECIDOS_SQL)) {
            int i = 1;
            for (Produto produto : value.getProdutosFornecidos()) {
                insertProdutosStatement.setInt(i, produto.getId());
                insertProdutosStatement.setInt(i + 1, recemInserido.getId());
                i += 2;
            }
            insertProdutosStatement.execute();
        } catch (SQLException e) {
            connection.rollback();
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private void deleteProdutosFornecidos(Connection connection, Collection<Integer> idProdutos, Fornecedor fornecedor)
            throws SQLException, RepositoryActionException {
        if(idProdutos.isEmpty())
            return;
        StringBuilder builder = new StringBuilder();
        builder.append("?,".repeat(idProdutos.size()));
        builder.deleteCharAt(builder.length() - 1);
        String DELETE_PRODUTOS_FORNECIDOS_SQL = "delete from produtofornecido where id_fornecedor = ? and id_produto in (" + builder.toString() + ")";
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUTOS_FORNECIDOS_SQL)) {
            preparedStatement.setInt(1, fornecedor.getId());
            int i = 2;
            for (Integer id : idProdutos) {
                preparedStatement.setInt(i, id);
                i++;
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            connection.rollback();
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private Fornecedor updateFornecedor(int id, Fornecedor value, Connection connection) throws SQLException, RepositoryActionException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getCnpj());
            preparedStatement.setString(3, value.getDescricao());
            preparedStatement.setString(4, value.getEndereco());
            preparedStatement.setString(5, value.getTelefone());
            preparedStatement.setString(6, value.getEmail());
            preparedStatement.setInt(7, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            return resultSet.next() ? Fornecedor.getFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            connection.rollback();
            throw new RepositoryActionException(e.getMessage());
        }
    }

    private List<Integer> getIdProdutosFornecidos(Fornecedor fornecedor) throws RepositoryActionException {
        String SQL = "select id_produto from produtofornecido where id_fornecedor = ?";
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL))
        {
            preparedStatement.setInt(1, fornecedor.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Integer> idList = new ArrayList<>();
            while(resultSet.next())
                idList.add(resultSet.getInt("id_produto"));
            return idList;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }
}
