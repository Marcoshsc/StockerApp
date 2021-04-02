package br.ufop.stocker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ItemOperacao {

    private int quantidade;
    private Produto produto;
    private Operacao operacao;

    public ItemOperacao(int quantidade, Produto produto, Operacao operacao) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.operacao = operacao;
    }
}
