package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.util.Set;

public interface FornecedorRepository extends Repository<Fornecedor> {

    /**
     * Retorna todos os fornecedores que fornecem o produto, sem os objetos associados.
     * @param produto produto desejado
     * @return todos os fornecedores que fornecem o produto, sem os objetos associados.
     * @throws RepositoryActionException caso algum erro ocorra.
     */
    Set<Fornecedor> findAllByProduto(Produto produto) throws RepositoryActionException;

}
