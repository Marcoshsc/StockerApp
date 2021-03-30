package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.repository.interfaces.OperacaoRepository;

import java.util.Set;

public class PSQLOperacaoRepository implements OperacaoRepository {

    @Override
    public Operacao findOne(int id) {
        return null;
    }

    @Override
    public Set<Operacao> findAll() {
        return null;
    }

    @Override
    public Operacao update(int id, Operacao value) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

}
