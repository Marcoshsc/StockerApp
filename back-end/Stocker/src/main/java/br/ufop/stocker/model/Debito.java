package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
@ToString(callSuper = true)
public class Debito extends Identificavel {

    private int sequencial;
    private double valor;
    private boolean pago;
    private Timestamp vencimento;
    private Operacao operacao;

    public Debito(int id, int sequencial, double valor, boolean pago, Timestamp vencimento) {
        super(id);
        this.sequencial = sequencial;
        this.valor = valor;
        this.pago = pago;
        this.vencimento = vencimento;
    }

    public static Debito getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Debito(resultSet.getInt("id"), resultSet.getInt("sequencial"), resultSet.getDouble("valor"),
                resultSet.getBoolean("pago"), resultSet.getTimestamp("vencimento"));
    }
}
