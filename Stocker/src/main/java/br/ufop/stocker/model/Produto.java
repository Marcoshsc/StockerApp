package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
