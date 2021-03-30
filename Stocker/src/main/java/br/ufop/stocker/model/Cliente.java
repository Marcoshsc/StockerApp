package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Descritivel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Cliente extends Descritivel {

    private final String cpf;

    public Cliente(int id, String nome, String descricao, String endereco, String telefone, String email,
                   Timestamp dataCadastro, String cpf) {
        super(id, nome, descricao, endereco, telefone, email, dataCadastro);
        this.cpf = cpf;
    }
}
