package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Identificavel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Debito extends Identificavel {

    private int sequencial;
    private double valor;
    private boolean pago;
    private Date vencimento;
    private Operacao operacao;

    public Debito(int id, int sequencial, double valor, boolean pago, Date vencimento) {
        super(id);
        this.sequencial = sequencial;
        this.valor = valor;
        this.pago = pago;
        this.vencimento = vencimento;
    }

    public static Debito getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Debito(resultSet.getInt("id"), resultSet.getInt("sequencial"), resultSet.getDouble("valor"),
                resultSet.getBoolean("pago"), resultSet.getDate("vencimento"));
    }

    public static List<Debito> getListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Debito> debitos = new ArrayList<>();
        while(resultSet.next())
            debitos.add(getFromResultSet(resultSet));
        return debitos;
    }

    @Override
    public String toString() {
        return "Debito{" +
                "super=" + super.toString() +
                "sequencial=" + sequencial +
                ", valor=" + valor +
                ", pago=" + pago +
                ", vencimento=" + vencimento +
                ", operacao=" + operacao.getId() +
                '}';
    }
}
