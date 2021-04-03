package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProdutoRepository extends Repository<Produto> {

    Set<Produto> findAllById(List<Integer> ids) throws RepositoryActionException;

    void saveAllTransactional(Collection<Produto> produtos, Connection connection) throws RepositoryActionException, SQLException;

}
