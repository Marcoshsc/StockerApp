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

    public EnumFormaPagamento getFromString(String forma) {
        for (EnumFormaPagamento enumTipoOperacao : EnumFormaPagamento.values()) {
            if(enumTipoOperacao.getForma().equalsIgnoreCase(forma))
                return enumTipoOperacao;
        }
        throw new IllegalArgumentException("Forma de pagamento inválida.");
    }

}
