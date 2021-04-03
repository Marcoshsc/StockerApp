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

}
