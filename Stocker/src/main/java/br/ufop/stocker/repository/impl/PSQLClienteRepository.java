package br.ufop.stocker.repository.impl;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.repository.interfaces.ClienteRepository;

import java.util.Set;

public class PSQLClienteRepository implements ClienteRepository {

    @Override
    public Cliente findOne(int id) {
        return null;
    }

    @Override
    public Set<Cliente> findAll() {
        return null;
    }

    @Override
    public Cliente update(int id, Cliente value) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
