package br.ufop.stocker.repository.impl;

import br.ufop.stocker.general.PropertyError;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;
import br.ufop.stocker.repository.util.DBUtils;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PSQLProdutoRepository implements ProdutoRepository {

    private static final String INSERT_SQL = "INSERT INTO public.produto (nome, descricao, estoque, preco) "
            + "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE public.produto SET nome = ?, descricao = ?, estoque = ?, preco = ?"
            + " WHERE id = ? RETURNING *";
    private static final String SELECT_ONE_SQL = "SELECT * FROM public.produto WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.produto";
    private static final String DELETE_SQL = "DELETE FROM public.produto WHERE id = ?";

    private final RepositoryFactory factory;

    public PSQLProdutoRepository(RepositoryFactory factory) {
        this.factory = factory;
    }

    public Produto findOne(int id) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_SQL))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Produto.getFromResultSet(resultSet) : null;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Set<Produto> findAll() throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            return Produto.getListFromResultSet(resultSet);
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }


    public Produto insert(Produto value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getDescricao());
            preparedStatement.setInt(3, value.getEstoque());
            preparedStatement.setDouble(4, value.getPreco());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            return rs.next() ? Produto.getFromResultSet(rs) : null;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Produto update(int id, Produto value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL))
        {
            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getDescricao());
            preparedStatement.setInt(3, value.getEstoque());
            preparedStatement.setDouble(4, value.getPreco());
            preparedStatement.setInt(5, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            return resultSet.next() ? Produto.getFromResultSet(resultSet) : null;
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
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

    @Override
    public Set<Produto> findAllById(List<Integer> ids) throws RepositoryActionException {
        StringBuilder builder = new StringBuilder();
        builder.append("?,".repeat(ids.size()));
        builder.deleteCharAt(builder.length() - 1);
        String FIND_ALL_SQL = "select * from produto where id IN (" + builder.toString() + ")";
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL))
        {
            for (int i = 0; i < ids.size(); i++) {
                preparedStatement.setLong(i + 1, ids.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            return Produto.getListFromResultSet(resultSet);
        } catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    @Override
    public void saveAllTransactional(Collection<Produto> produtos, Connection connection) throws RepositoryActionException, SQLException {
        for (Produto produto : produtos) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
                preparedStatement.setString(1, produto.getNome());
                preparedStatement.setString(2, produto.getDescricao());
                preparedStatement.setInt(3, produto.getEstoque());
                preparedStatement.setDouble(4, produto.getPreco());
                preparedStatement.setInt(5, produto.getId());

                preparedStatement.executeUpdate();
            } catch(SQLException e) {
                connection.rollback();
                throw new RepositoryActionException(e.getMessage());
            }
        }
    }
}
