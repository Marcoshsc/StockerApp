package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.interfaces.ProdutoRepository;

import java.util.Set;

public class PSQLProdutoRepository implements ProdutoRepository {

    @Override
    public Produto findOne(int id) {
        return null;
    }

    @Override
    public Set<Produto> findAll() {
        return null;
    }

    @Override
    public Produto update(int id, Produto value) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

}
