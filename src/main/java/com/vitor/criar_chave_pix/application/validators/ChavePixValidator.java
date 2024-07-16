package com.vitor.criar_chave_pix.application.validators;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class ChavePixValidator {

    public static void validate(String tipoChave, String valorChave) {

        if (!TipoChave.isValid(tipoChave)) {
            throw new ValidationException("Campo tipoChave inválido: " + tipoChave);
        }

        switch (tipoChave.toLowerCase()) {
            case "celular":
                validateNumber(valorChave);
                break;
            case "email":
                validateEmail(valorChave);
                break;
            case "cpf":
                validateCPF(valorChave);
                break;
            case "cnpj":
                validateCNPJ(valorChave);
                break;
            case "chave_aleatoria":
                validateEVP(valorChave);
                break;
            default:
                throw new ValidationException("Tipo de chave desconhecido: " + tipoChave);
        }
    }

    private static void validateNumber(String celular) {
        if (celular == null || !celular.matches("\\+\\d{1,2}\\d{2,3}\\d{9}")) {
            throw new ValidationException("Numero com formato inválido. Deve iniciar com “+” seguido de um DDD até 3 digitos, e de 9 digitos. Ex: +5511999999999");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@[\\w-\\.]+\\.\\w{2,}$") || email.length() > 77) {
            throw new ValidationException("Email inválidos");
        }
    }

    private static void validateCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new ValidationException("CPF inválido. Deve conter 11 digitos.");
        }
    }

    private static void validateCNPJ(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            throw new ValidationException("CNPJ inválido. Deve conter 14 digitos.");
        }
    }

    private static void validateEVP(String chaveAleatoria) {
        if (chaveAleatoria == null || !chaveAleatoria.matches("^[a-zA-Z0-9]{1,36}$")) {
            throw new ValidationException("Chave aleatória inválida. Deve conter apenas letras e números.");
        }
    }
}
