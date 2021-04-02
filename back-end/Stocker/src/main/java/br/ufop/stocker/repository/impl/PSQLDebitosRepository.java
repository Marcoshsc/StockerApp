package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Debito;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.factory.RepositoryFactory;
import br.ufop.stocker.repository.interfaces.DebitosRepository;

import java.util.Set;

public class PSQLDebitosRepository implements DebitosRepository {


    private final RepositoryFactory factory;

    public PSQLDebitosRepository(RepositoryFactory factory) {
        this.factory = factory;
    }

    @Override
    public Debito insert(Debito value) throws RepositoryActionException {
        return null;
    }

    @Override
    public Debito findOne(int id) throws RepositoryActionException {
        return null;
    }

    @Override
    public Set<Debito> findAll() throws RepositoryActionException {
        return null;
    }

    @Override
    public Debito update(int id, Debito value) throws RepositoryActionException {
        return null;
    }

    @Override
    public void delete(int id) throws RepositoryActionException {

    }
}
