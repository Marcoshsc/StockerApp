package br.ufop.stocker.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum EnumTipoOperacao {

    VENDA("VENDA"), COMPRA("COMPRA");

    private final String tipo;

    public EnumTipoOperacao getFromString(String tipo) {
        for (EnumTipoOperacao enumTipoOperacao : EnumTipoOperacao.values()) {
            if(enumTipoOperacao.getTipo().equalsIgnoreCase(tipo))
                return enumTipoOperacao;
        }
        throw new IllegalArgumentException("Tipo de operação inválido.");
    }

}
