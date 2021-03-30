package br.ufop.stocker.model;

import br.ufop.stocker.superclasses.Descritivel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class Fornecedor extends Descritivel {

    private String cnpj;

    private Set<Produto> produtosFornecidos;

    public Fornecedor(int id, String nome, String descricao, String endereco, String telefone, String email,
                      Timestamp dataCadastro, String cnpj) {
        super(id, nome, descricao, endereco, telefone, email, dataCadastro);
        this.cnpj = cnpj;
    }
}
