package br.ufop.stocker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class NomeDebito {

    private String nome;
    private double valor;

    public static List<NomeDebito> fromResultSet(ResultSet resultSet) throws SQLException {
        List<NomeDebito> nomeDebitos = new ArrayList<>();
        while(resultSet.next())
            nomeDebitos.add(new NomeDebito(resultSet.getString("nome"), resultSet.getDouble("valor")));
        return nomeDebitos;
    }

}
