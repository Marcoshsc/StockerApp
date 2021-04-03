package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.repository.exception.RepositoryActionException;
import br.ufop.stocker.superclasses.Identificavel;

import java.util.Set;

public interface Repository<T extends Identificavel> {

    /**
     * Insere o objeto e retorna o objeto inserido.
     * @param value objeto a ser inserido
     * @return objeto inserido
     * @throws RepositoryActionException se algum erro ocorreu
     */
	T insert(T value) throws RepositoryActionException;

    /**
     * Procura o objeto e o retorna. Caso complete=true, carrega tambem todos os objetos associados.
     * @param id o id do objeto
     * @param complete se deve carregar objetos associados
     * @return o objeto carregado
     * @throws RepositoryActionException caso algum erro ocorra
     */
    T findOne(int id, boolean complete) throws RepositoryActionException;

    /**
     * Retorna todos os objetos do banco, juntamente com todos os objetos associados.
     * @return todos os objetos do banco
     * @throws RepositoryActionException se algum erro ocorreu
     */
    Set<T> findAll() throws RepositoryActionException;

    /**
     * Atualiza o objeto no banco e retorna o objeto atualizado
     * @param id o id do objeto
     * @param value o novo objeto
     * @return o objeto atualizado
     * @throws RepositoryActionException caso algum erro ocorra
     */
    T update(int id, T value) throws RepositoryActionException;

    /**
     * Deleta o objeto no banco. Se algum erro ocorrer, pode mostrar ao cliente que nao foi deletado porque havia
     * dependencia com outros objetos.
     * @param id id do objeto
     * @throws RepositoryActionException se algum erro ocorreu
     */
    void delete(int id) throws RepositoryActionException;

}
