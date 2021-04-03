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
public class Cliente extends Descritivel {

    private String cpf;
    private Set<Venda> vendas = new HashSet<>();

    public Cliente(int id, String nome, String descricao, String endereco, String telefone, String email,
                   Timestamp dataCadastro, String cpf) {
        super(id, nome, descricao, endereco, telefone, email, dataCadastro);
        this.cpf = cpf;
    }

    public static Cliente getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getString("endereco"),
                resultSet.getString("telefone"), resultSet.getString("email"), resultSet.getTimestamp("data_cadastro"), resultSet.getString("cpf"));
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "super=" + super.toString() +
                "cpf='" + cpf + '\'' +
                ", vendas=" + vendas +
                '}';
    }
}
