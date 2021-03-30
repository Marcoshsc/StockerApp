package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Debito extends Identificavel {

    private int sequencial;
    private double valor;
    private boolean pago;
    private Timestamp vencimento;
    private Operacao operacao;

    public Debito(int id, int sequencial, double valor, boolean pago, Timestamp vencimento, Operacao operacao) {
        super(id);
        this.sequencial = sequencial;
        this.valor = valor;
        this.pago = pago;
        this.vencimento = vencimento;
        this.operacao = operacao;
    }
}
