package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.Cliente;
import br.ufop.stocker.model.Fornecedor;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.util.Set;

public interface OperacaoRepository extends Repository<Operacao> {

    Set<Operacao> findAll(EnumTipoOperacao tipo) throws RepositoryActionException;
    Set<Operacao> findAllByCliente(Cliente cliente) throws RepositoryActionException;
    Set<Operacao> findAllByFornecedor(Fornecedor fornecedor) throws RepositoryActionException;

}
