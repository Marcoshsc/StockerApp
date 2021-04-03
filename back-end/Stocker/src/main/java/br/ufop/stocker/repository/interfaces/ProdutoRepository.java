package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.model.Produto;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProdutoRepository extends Repository<Produto> {

    /**
     * Retorna todos os produtos com o id em questao. Objetos associados não são carregados.
     * @param ids os ids dos produtos
     * @return todos os produtos com o id em questao. Objetos associados não são carregados
     * @throws RepositoryActionException se algum erro ocorreu.
     */
    Set<Produto> findAllById(List<Integer> ids) throws RepositoryActionException;

    /**
     * Simplesmente atualiza todos os produtos recebidos, usando o id do objeto. Caso algum erro ocorra, da rollback na
     * conexão recebida. A conexão não pode estar em modo auto-commit.
     * @param produtos produtos a serem atualizados
     * @param connection a conexão que está segurando a transação.
     * @throws RepositoryActionException caso algum erro ocorra.
     * @throws SQLException caso ocorra algum erro no rollback.
     */
    void saveAllTransactional(Collection<Produto> produtos, Connection connection) throws RepositoryActionException, SQLException;

}
