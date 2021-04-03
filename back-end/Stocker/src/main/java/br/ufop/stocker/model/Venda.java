package br.ufop.stocker.model;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.enums.EnumTipoOperacao;

import java.sql.Timestamp;

public class Venda extends Operacao {

    public Venda(int id, Timestamp data, double valorFinal, EnumFormaPagamento formaPagamento) {
        super(id, data, valorFinal, EnumTipoOperacao.VENDA, formaPagamento);
    }

    public Venda(Operacao operacao) {
        this(operacao.getId(), operacao.getData(), operacao.getValorFinal(), operacao.getFormaPagamento());
    }

}
