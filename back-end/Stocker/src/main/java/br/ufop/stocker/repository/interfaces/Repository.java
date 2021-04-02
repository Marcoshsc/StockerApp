package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.superclasses.Identificavel;

import java.util.Set;

public interface Repository<T extends Identificavel> {
	
	T insert(T value) throws RepositoryActionException;
    T findOne(int id) throws RepositoryActionException;
    Set<T> findAll() throws RepositoryActionException;
    T update(int id, T value) throws RepositoryActionException;
    void delete(int id) throws RepositoryActionException;

}
