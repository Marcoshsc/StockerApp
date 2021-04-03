package br.ufop.stocker.repository.factory.impl;

import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.impl.PSQLClienteRepository;
import br.ufop.stocker.repository.impl.PSQLDebitosRepository;
import br.ufop.stocker.repository.impl.PSQLOperacaoRepository;
import br.ufop.stocker.repository.impl.PSQLProdutoRepository;
import br.ufop.stocker.repository.interfaces.*;

public class RepositoryFactoryImpl implements RepositoryFactory {

    public ProdutoRepository produto() {
        return new PSQLProdutoRepository(this);
    }

    public ClienteRepository cliente() {
        return new PSQLClienteRepository(this);
    }

    public DebitosRepository debitos() {
        return new PSQLDebitosRepository(this);
    }

    public FornecedorRepository fornecedor() {
        return new PSQLFornecedorRepository(this);
    }

    public OperacaoRepository operacao() {
        return new PSQLOperacaoRepository(this);
    }

}
