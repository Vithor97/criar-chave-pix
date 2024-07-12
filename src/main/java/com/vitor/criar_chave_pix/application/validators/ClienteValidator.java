package com.vitor.criar_chave_pix.application.validators;

import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.exceptions.ValidationException;

public class ClienteValidator {

    public static void validarAlteracao(Cliente clienteExistente, Cliente clienteAlterado) {
        if (valoresSaoIguais(clienteExistente, clienteAlterado)) {
            throw new ValidationException("Nenhum valor foi alterado.");
        }

        if (clienteAlterado.getNome() != null && !clienteAlterado.getNome().equals(clienteExistente.getNome())) {
            clienteExistente.setNome(clienteAlterado.getNome());
        }

        if (clienteAlterado.getSobrenome() != null && !clienteAlterado.getSobrenome().equals(clienteExistente.getSobrenome())) {
            clienteExistente.setSobrenome(clienteAlterado.getSobrenome());
        }

        if (clienteAlterado.getTipoConta() != null && !clienteAlterado.getTipoConta().equals(clienteExistente.getTipoConta())) {
            clienteExistente.setTipoConta(clienteAlterado.getTipoConta());
        }

        if (clienteAlterado.getAgencia() != null && !clienteAlterado.getAgencia().equals(clienteExistente.getAgencia())) {
            clienteExistente.setAgencia(clienteAlterado.getAgencia());
        }

        if (clienteAlterado.getConta() != null && !clienteAlterado.getConta().equals(clienteExistente.getConta())) {
            clienteExistente.setConta(clienteAlterado.getConta());
        }
    }

    private static boolean valoresSaoIguais(Cliente clienteExistente, Cliente clienteAlterado) {
        boolean isNomeIgual = clienteAlterado.getNome() != null && clienteAlterado.getNome().equals(clienteExistente.getNome());
        boolean isSobrenomeIgual = clienteAlterado.getSobrenome() != null && clienteAlterado.getSobrenome().equals(clienteExistente.getSobrenome());
        boolean isTipoContaIgual = clienteAlterado.getTipoConta() != null && clienteAlterado.getTipoConta().equals(clienteExistente.getTipoConta());
        boolean isAgenciaIgual = clienteAlterado.getAgencia() != null && clienteAlterado.getAgencia().equals(clienteExistente.getAgencia());
        boolean isContaIgual = clienteAlterado.getConta() != null && clienteAlterado.getConta().equals(clienteExistente.getConta());

        if (isNomeIgual && isSobrenomeIgual && isTipoContaIgual && isAgenciaIgual && isContaIgual) {
            return true;
        }

        return false;
    }
}
