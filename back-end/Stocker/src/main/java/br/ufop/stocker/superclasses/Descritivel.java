package br.ufop.stocker.superclasses;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public abstract class Descritivel extends Identificavel {

    private String nome;
    private String descricao;
    private String endereco;
    private String telefone;
    private String email;
    private Timestamp dataCadastro;

    protected Descritivel(int id, String nome, String descricao, String endereco, String telefone, String email,
                       Timestamp dataCadastro) {
        super(id);
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }
}
