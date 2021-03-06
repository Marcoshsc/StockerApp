package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.*;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.util.List;
import java.util.Set;

public interface OperacaoRepository extends Repository<Operacao> {

    /**
     * Retorna todas as operações por tipo (COMPRA, VENDA), com todos os objetos associados.
     * @param tipo o tipo das operações.
     * @return todas as operações por tipo (COMPRA, VENDA), com todos os objetos associados.
     * @throws RepositoryActionException caso algum erro ocorra.
     */
    Set<Operacao> findAll(EnumTipoOperacao tipo) throws RepositoryActionException;

    /**
     * Retorna todas as operações (VENDAS) do cliente, sem os objetos associados.
     * @param cliente o cliente desejado
     * @return todas as operações (VENDAS) do cliente, sem os objetos associados.
     * @throws RepositoryActionException caso algum erro ocorra.
     */
    Set<Operacao> findAllByCliente(Cliente cliente) throws RepositoryActionException;

    /**
     * Retorna todas as operações (COMPRAS) do fornecedor, sem os objetos associados.
     * @param fornecedor o fornecedor desejado
     * @return todas as operações (COMPRAS) do fornecedor, sem os objetos associados.
     * @throws RepositoryActionException caso algum erro ocorra.
     */
    Set<Operacao> findAllByFornecedor(Fornecedor fornecedor) throws RepositoryActionException;

    void pagarDebito(Debito debito) throws RepositoryActionException;

    List<NomeDebito> getNomeDebitos(EnumTipoOperacao tipoOperacao) throws RepositoryActionException;

    List<Debito> getAllDebitosAtivos(Cliente cliente) throws RepositoryActionException;

    List<Debito> getAllDebitosAtivos(Fornecedor fornecedor) throws RepositoryActionException;

}
