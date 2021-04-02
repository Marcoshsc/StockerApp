package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.repository.interfaces.OperacaoRepository;

import java.util.Set;

public class PSQLOperacaoRepository implements OperacaoRepository {


    @Override
    public Operacao insert(Operacao value) throws RepositoryActionException {
        return null;
    }

    @Override
    public Operacao findOne(int id) throws RepositoryActionException {
        return null;
    }

    @Override
    public Set<Operacao> findAll() throws RepositoryActionException {
        return null;
    }

    @Override
    public Operacao update(int id, Operacao value) throws RepositoryActionException {
        return null;
    }

    @Override
    public void delete(int id) throws RepositoryActionException {

    }
}
