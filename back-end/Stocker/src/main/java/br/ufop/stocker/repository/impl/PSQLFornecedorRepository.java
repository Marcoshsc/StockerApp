package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;

import java.util.Set;

public class PSQLFornecedorRepository implements FornecedorRepository {

    @Override
    public Fornecedor findOne(int id) {
        return null;
    }

    @Override
    public Set<Fornecedor> findAll() {
        return null;
    }

    @Override
    public Fornecedor update(int id, Fornecedor value) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

}
