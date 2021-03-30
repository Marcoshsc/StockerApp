package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.superclasses.Identificavel;

import java.util.Set;

public interface Repository<T extends Identificavel> {
	
	T insert(T value);
    T findOne(int id);
    Set<T> findAll();
    T update(int id, T value);
    void delete(int id);

}