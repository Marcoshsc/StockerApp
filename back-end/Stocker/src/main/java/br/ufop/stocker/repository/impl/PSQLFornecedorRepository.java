package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.interfaces.FornecedorRepository;

import java.util.Set;

public class PSQLFornecedorRepository implements FornecedorRepository {


    @Override
    public Fornecedor insert(Fornecedor value) throws RepositoryActionException {
        return null;
    }

    @Override
    public Fornecedor findOne(int id) throws RepositoryActionException {
        return null;
    }

    @Override
    public Set<Fornecedor> findAll() throws RepositoryActionException {
        return null;
    }

    @Override
    public Fornecedor update(int id, Fornecedor value) throws RepositoryActionException {
        return null;
    }

    @Override
    public void delete(int id) throws RepositoryActionException {

    }
}
