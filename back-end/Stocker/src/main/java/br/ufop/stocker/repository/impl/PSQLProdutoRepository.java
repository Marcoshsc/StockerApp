package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

import java.util.Set;

public class PSQLProdutoRepository implements ProdutoRepository {


    @Override
    public Produto insert(Produto value) throws RepositoryActionException {
        return null;
    }

    @Override
    public Produto findOne(int id) throws RepositoryActionException {
        return null;
    }

    @Override
    public Set<Produto> findAll() throws RepositoryActionException {
        return null;
    }

    @Override
    public Produto update(int id, Produto value) throws RepositoryActionException {
        return null;
    }

    @Override
    public void delete(int id) throws RepositoryActionException {

    }
}
