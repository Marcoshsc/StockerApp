package br.ufop.stocker.repository.factory;

import br.ufop.stocker.repository.impl.*;
import br.ufop.stocker.repository.interfaces.*;

public class RepositoryFactory {

    public ProdutoRepository produto() {
        return new PSQLProdutoRepository();
    }

    public ClienteRepository cliente() {
        return new PSQLClienteRepository();
    }

    public DebitosRepository debitos() {
        return new PSQLDebitosRepository();
    }

    public FornecedorRepository fornecedor() {
        return new PSQLFornecedorRepository();
    }

    public OperacaoRepository operacao() {
        return new PSQLOperacaoRepository();
    }

}
