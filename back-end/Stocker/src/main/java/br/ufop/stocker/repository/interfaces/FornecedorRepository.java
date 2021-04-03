package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.util.Set;

public interface FornecedorRepository extends Repository<Fornecedor> {

    Set<Fornecedor> findAllByProduto(Produto produto) throws RepositoryActionException;

}
