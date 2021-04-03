package br.ufop.stocker.repository.interfaces;

import br.ufop.stocker.enums.EnumTipoOperacao;
import br.ufop.stocker.model.Operacao;
import br.ufop.stocker.repository.exception.RepositoryActionException;

import java.util.Set;

public interface OperacaoRepository extends Repository<Operacao> {

    Set<Operacao> findAll(EnumTipoOperacao tipo) throws RepositoryActionException;

}
