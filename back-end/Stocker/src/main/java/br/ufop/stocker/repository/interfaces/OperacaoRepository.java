package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Debito;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.repository.exception.RepositoryActionException;

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

}
