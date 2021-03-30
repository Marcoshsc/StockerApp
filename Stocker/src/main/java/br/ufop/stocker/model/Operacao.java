package br.ufop.stocker.model;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class Operacao extends Identificavel {

    private Timestamp data;
    private double valorFinal;
    private EnumTipoOperacao tipo;
    private EnumFormaPagamento formaPagamento;
    private Cliente cliente;
    private Fornecedor fornecedor;
    private Set<ItemOperacao> itens;

    public Operacao(int id, Timestamp data, double valorFinal, EnumTipoOperacao tipo, EnumFormaPagamento formaPagamento,
                    Cliente cliente, Fornecedor fornecedor) {
        super(id);
        this.data = data;
        this.valorFinal = valorFinal;
        this.tipo = tipo;
        this.formaPagamento = formaPagamento;
        this.cliente = cliente;
        this.fornecedor = fornecedor;
    }
}
