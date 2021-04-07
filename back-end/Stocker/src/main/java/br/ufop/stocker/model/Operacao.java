package br.ufop.stocker.model;

import br.ufop.stocker.enums.EnumFormaPagamento;
import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
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
    private Set<ItemOperacao> itens = new HashSet<>();
    private Set<Debito> debitos = new HashSet<>();

    public Operacao(int id, Timestamp data, double valorFinal, EnumTipoOperacao tipo, EnumFormaPagamento formaPagamento) {
        super(id);
        this.data = data;
        this.valorFinal = valorFinal;
        this.tipo = tipo;
        this.formaPagamento = formaPagamento;
    }

    public void addItem(ItemOperacao item) {
        itens.add(item);
        item.setOperacao(this);
    }

    public void removeItem(ItemOperacao item) {
        itens.remove(item);
        item.setOperacao(null);
    }
    
    public void addDebito(Debito debito) {
        debitos.add(debito);
        debito.setOperacao(this);
    }

    public void removeDebito(Debito debito) {
        debitos.remove(debito);
        debito.setOperacao(null);
    }

    public static Operacao getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Operacao(resultSet.getInt("id"), resultSet.getTimestamp("data"),
                resultSet.getDouble("valor_final"), EnumTipoOperacao.valueOf(resultSet.getString("tipo")),
                EnumFormaPagamento.valueOf(resultSet.getString("forma_pagamento")));
    }

    @Override
    public String toString() {
        String client = cliente != null ? String.valueOf(cliente.getId()) : "none";
        String fornec = fornecedor != null ? String.valueOf(fornecedor.getId()) : "none";
        return "Operacao{" +
                "super=" + super.toString() +
                "data=" + data +
                ", valorFinal=" + valorFinal +
                ", tipo=" + tipo +
                ", formaPagamento=" + formaPagamento +
                ", cliente=" + client +
                ", fornecedor=" + fornec +
                ", itens=" + itens +
                ", debitos=" + debitos +
                '}';
    }

}
