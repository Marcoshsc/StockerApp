package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Descritivel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class Cliente extends Descritivel {

    private final String cpf;

    public Cliente(int id, String nome, String descricao, String endereco, String telefone, String email,
                   Timestamp dataCadastro, String cpf) {
        super(id, nome, descricao, endereco, telefone, email, dataCadastro);
        this.cpf = cpf;
    }

    public static Cliente getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getString("endereco"),
                resultSet.getString("telefone"), resultSet.getString("email"), resultSet.getTimestamp("data_cadastro"), resultSet.getString("cpf"));
    }

    public static Set<Cliente> getListFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Cliente> clienteSet = new HashSet<>();
        while (resultSet.next())
            clienteSet.add(getFromResultSet(resultSet));
        return clienteSet;
    }

	@Override
	public String toString() {
		return "Cliente [id = " + super.getId() + " |nome = " + super.getNome() + " |cpf = " + cpf + "]";
	}
    
    
}
