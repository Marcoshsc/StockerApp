package br.ufop.stocker.model;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.enums.EnumTipoOperacao;

import java.sql.Timestamp;

public class Compra extends Operacao {

    public Compra(int id, Timestamp data, double valorFinal, EnumFormaPagamento formaPagamento) {
        super(id, data, valorFinal, EnumTipoOperacao.COMPRA, formaPagamento);
    }

    public Compra(Operacao operacao) {
        this(operacao.getId(), operacao.getData(), operacao.getValorFinal(), operacao.getFormaPagamento());
    }
}
