package br.ufop.stocker.repository.factory;

import br.ufop.stocker.repository.factory.impl.RepositoryFactoryImpl;
import br.ufop.stocker.repository.interfaces.ClienteRepository;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;
import br.ufop.stocker.repository.interfaces.OperacaoRepository;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

public interface RepositoryFactory {

    ProdutoRepository produto();

    ClienteRepository cliente();

    FornecedorRepository fornecedor();

    OperacaoRepository operacao();

    static RepositoryFactory create() {
        return new RepositoryFactoryImpl();
    }

}
