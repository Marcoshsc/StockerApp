package br.ufop.stocker.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum EnumFormaPagamento {

    DINHEIRO("Dinheiro"), PRAZO("Prazo");

    private final String forma;

}
