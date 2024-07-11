package com.vitor.criar_chave_pix.application.validators;

public enum TipoChave {
    CELULAR("celular"),
    EMAIL("email"),
    CPF("cpf"),
    CNPJ("cnpj"),
    CHAVE_ALEATORIA("chave_aleatoria");

    private final String value;

    TipoChave(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String value) {
        for (TipoChave tipoChave : TipoChave.values()) {
            if (tipoChave.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
