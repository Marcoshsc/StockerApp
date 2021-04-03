package br.ufop.stocker.repository.impl;

import br.ufop.stocker.general.PropertyError;
import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.model.Venda;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.ClienteRepository;
import br.ufop.stocker.repository.util.DBUtils;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PSQLClienteRepository implements ClienteRepository {

	private static final String INSERT_SQL = "INSERT INTO public.cliente (nome, cpf, descricao, endereco, telefone, email) "
													 + "VALUES (?,?,?,?,?,?)";
	private static final String UPDATE_SQL = "UPDATE public.cliente SET nome = ?, cpf = ?, descricao = ?, endereco = ?, telefone = ?, email = ?"
													 + " WHERE id = ? RETURNING *";
	private static final String SELECT_ONE_SQL = "SELECT * FROM public.cliente WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.cliente";
    private static final String DELETE_SQL = "DELETE FROM public.cliente WHERE id = ?";

    private final RepositoryFactory factory;

    public PSQLClienteRepository(RepositoryFactory factory) {
        this.factory = factory;
    }

    public Cliente findOne(int id, boolean complete) throws RepositoryActionException {
    	try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_SQL))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Cliente cliente = resultSet.next() ? Cliente.getFromResultSet(resultSet) : null;
            if(cliente == null)
                return null;
            if(!complete)
                return cliente;
            Set<Operacao> operacoes = factory.operacao().findAllByCliente(cliente);
            Set<Venda> vendas = new HashSet<>();
            for (Operacao operacao : operacoes)
                vendas.add(new Venda(operacao));
            cliente.setVendas(vendas);
            return cliente;
    	} catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Set<Cliente> findAll() throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Cliente> clientes = new HashSet<>();
            while(resultSet.next()) {
                Cliente cliente = Cliente.getFromResultSet(resultSet);
                Set<Operacao> operacoes = factory.operacao().findAllByCliente(cliente);
                Set<Venda> vendas = new HashSet<>();
                for (Operacao operacao : operacoes)
                    vendas.add(new Venda(operacao));
                cliente.setVendas(vendas);
                clientes.add(cliente);
            }
            return clientes;
    	} catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }
    

    public Cliente insert(Cliente value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getCpf());
            preparedStatement.setString(3, value.getDescricao());
            preparedStatement.setString(4, value.getEndereco());
            preparedStatement.setString(5, value.getTelefone());
            preparedStatement.setString(6, value.getEmail());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            return rs.next() ? Cliente.getFromResultSet(rs) : null;
    	} catch (SQLException | PropertyError e) {
            throw new RepositoryActionException(e.getMessage());
        }
    }

    public Cliente update(int id, Cliente value) throws RepositoryActionException {
        try(Connection connection = DBUtils.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL))
        {
            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getCpf());
            preparedStatement.setString(3, value.getDescricao());
            preparedStatement.setString(4, value.getEndereco());
            preparedStatement.setString(5, value.getTelefone());
            preparedStatement.setString(6, value.getEmail());
            preparedStatement.setInt(7, id);
            
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            Cliente cliente = resultSet.next() ? Cliente.getFromResultSet(resultSet) : null;
            assert cliente != null;
            Set<Operacao> operacoes = factory.operacao().findAllByCliente(cliente);
            Set<Venda> vendas = new HashSet<>();
            for (Operacao operacao : operacoes)
                vendas.add(new Venda(operacao));
            cliente.setVendas(vendas);
            return cliente;
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
}
