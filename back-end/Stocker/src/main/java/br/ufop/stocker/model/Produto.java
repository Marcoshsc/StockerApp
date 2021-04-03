package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
public class Produto extends Identificavel {

    private String nome;
    private double preco;
    private int estoque;
    private String descricao;

    public Produto(int id, String nome, double preco, int estoque, String descricao) {
        super(id);
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.descricao = descricao;
    }

    public void aumentarEstoque(int quantidade) {
        estoque += quantidade;
    }

    public void diminuirEstoque(int quantidade) {
        estoque -= quantidade;
    }

    public static Produto getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Produto(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getDouble("preco"),
                resultSet.getInt("estoque"), resultSet.getString("descricao"));
    }

    public static Set<Produto> getListFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Produto> produtos = new HashSet<>();
        while (resultSet.next())
            produtos.add(getFromResultSet(resultSet));
        return produtos;
    }

}
