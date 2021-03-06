package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class ItemOperacao extends Identificavel {

    private int quantidade;
    private Produto produto;
    private Operacao operacao;

    public ItemOperacao(int id, int quantidade) {
        super(id);
        this.quantidade = quantidade;
    }

    public static ItemOperacao getFromResultSet(ResultSet resultSet) throws SQLException {
        return new ItemOperacao(resultSet.getInt("id"), resultSet.getInt("quantidade"));
    }

    @Override
    public String toString() {
        return "ItemOperacao{" +
                "super=" + super.toString() +
                "quantidade=" + quantidade +
                ", produto=" + produto.getId() +
                ", operacao=" + operacao.getId() +
                '}';
    }
}
