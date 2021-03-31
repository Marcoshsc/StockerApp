package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.repository.interfaces.ClienteRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.HashSet;
import java.util.Set;

public class PSQLClienteRepository implements ClienteRepository {
	
	private Connection connection;
	private static final String INSERT_USERS_SQL = "INSERT INTO public.cliente (nome, cpf, descricao, endereco, telefone, email) " 
													 + "VALUES (?,?,?,?,?,?)";
	private static final String UPDATE_USERS_SQL = "UPDATE public.cliente SET nome = ?, cpf = ?, descricao = ?, endereco = ?, telefone = ?, email = ?"
													 + " WHERE id = ? RETURNING *";
	private static final String QUERY = "SELECT * FROM public.cliente WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.cliente";
    private static final String DELETE_USERS_SQL = "DELETE FROM public.cliente WHERE id = ?";
	
    public Cliente findOne(int id) {
    	try{
    		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/new_database", "postgres", "postgres");
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Cliente cliente = null;
            if (resultSet.next()){
            	cliente = new Cliente(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), 
            			resultSet.getString("endereco"), resultSet.getString("telefone"), resultSet.getString("email"), 
            			resultSet.getTimestamp("data_cadastro"), resultSet.getString("cpf"));
            }
            if(cliente == null) {
            	System.out.println("Cliente não encontrado!");
            }
            return(cliente);
    	}catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }

    public Set<Cliente> findAll() {
    	try{
    		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/new_database", "postgres", "postgres");
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Cliente> clienteSet = new HashSet<Cliente>();
            Cliente cliente;
            while (resultSet.next()) {
            	cliente = new Cliente(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), 
                		resultSet.getString("endereco"), resultSet.getString("telefone"), resultSet.getString("email"), 
                		resultSet.getTimestamp("data_cadastro"), resultSet.getString("cpf"));
            	clienteSet.add(cliente);
            }
            return(clienteSet);
    	}catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }
    

public Cliente insert(Cliente value) {
    	try{
    		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/new_database", "postgres", "postgres");
            
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getCpf());
            preparedStatement.setString(3, value.getDescricao());
            preparedStatement.setString(4, value.getEndereco());
            preparedStatement.setString(5, value.getTelefone());
            preparedStatement.setString(6, value.getEmail());

            Cliente c = null;
            preparedStatement.executeUpdate();
            try{
            	ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    c = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getString("endereco"),
                    		rs.getString("telefone"), rs.getString("email"), rs.getTimestamp("data_cadastro"), rs.getString("cpf"));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Cliente inserido com sucesso! id: " + c.getId());
            return(c);
    	}catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }

    public Cliente update(int id, Cliente value) {
    	try{
    		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/new_database", "postgres", "postgres");
    		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);
    		
            preparedStatement.setString(1, value.getNome());
            preparedStatement.setString(2, value.getCpf());
            preparedStatement.setString(3, value.getDescricao());
            preparedStatement.setString(4, value.getEndereco());
            preparedStatement.setString(5, value.getTelefone());
            preparedStatement.setString(6, value.getEmail());
            preparedStatement.setInt(7, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            Cliente cliente = null;
            if (resultSet.next()) {
            	cliente = new Cliente(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), 
            			resultSet.getString("endereco"), resultSet.getString("telefone"), resultSet.getString("email"), 
   						resultSet.getTimestamp("data_cadastro"), resultSet.getString("cpf"));
            }
            System.out.println("Cliente atualizado com sucesso!");
            return(cliente);
    	}catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }

    public void delete(int id) {
    	try{
    		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/new_database", "postgres", "postgres");
    		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);
    	    preparedStatement.setInt(1, id);
    	    int result = preparedStatement.executeUpdate();
            System.out.println("Cliente deletado com sucesso!");
    	    System.out.println("Número de registros afetados :: " + result);
    	}catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
    }
}
