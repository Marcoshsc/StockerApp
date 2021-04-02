package br.ufop.stocker.repository.impl;

import br.ufop.stocker.general.PropertyError;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;
import br.ufop.stocker.repository.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public Fornecedor findOne(int id) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_SQL))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Fornecedor fornecedor =  resultSet.next() ? Fornecedor.getFromResultSet(resultSet) : null;
            if(fornecedor == null)
                return fornecedor;
            List<Integer> idProdutosFornecidos = getIdProdutosFornecidos(fornecedor);
            Set<Produto> produtosFornecidos = factory.produto().findAllById(idProdutosFornecidos);
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
            if(value.getProdutosFornecidos().isEmpty())
                return fornecedor;
            insertProdutosFornecidos(value, connection, fornecedor);
            connection.commit();
            return fornecedor;
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
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.getProdutosFornecidos().size(); i++) {
            builder.append("(?,?),");
        }
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

    public Fornecedor update(int id, Fornecedor value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL))
        {
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
