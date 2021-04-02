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
public class Fornecedor extends Descritivel {

    private String cnpj;

    private Set<Produto> produtosFornecidos = new HashSet<>();

    public Fornecedor(int id, String nome, String descricao, String endereco, String telefone, String email,
                      Timestamp dataCadastro, String cnpj) {
        super(id, nome, descricao, endereco, telefone, email, dataCadastro);
        this.cnpj = cnpj;
    }

    public void addProduto(Produto produto) {
        produtosFornecidos.add(produto);
    }

    public void removeProduto(Produto produto) {
        produtosFornecidos.remove(produto);
    }

    public static Fornecedor getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Fornecedor(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("descricao"), resultSet.getString("endereco"),
                resultSet.getString("telefone"), resultSet.getString("email"), resultSet.getTimestamp("data_cadastro"), resultSet.getString("cnpj"));
    }

    public static Set<Fornecedor> getListFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Fornecedor> fornecedores = new HashSet<>();
        while (resultSet.next())
            fornecedores.add(getFromResultSet(resultSet));
        return fornecedores;
    }

}
