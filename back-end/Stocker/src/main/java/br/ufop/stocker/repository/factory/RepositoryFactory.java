package br.ufop.stocker.repository.factory;

import br.ufop.stocker.repository.factory.impl.RepositoryFactoryImpl;
import br.ufop.stocker.repository.interfaces.*;

public interface RepositoryFactory {

    ProdutoRepository produto();

    ClienteRepository cliente();

    DebitosRepository debitos();

    FornecedorRepository fornecedor();

    OperacaoRepository operacao();

    static RepositoryFactory create() {
        return new RepositoryFactoryImpl();
    }

}
