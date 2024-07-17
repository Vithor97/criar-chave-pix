package com.vitor.criar_chave_pix.application.validators;

import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;

public class ClienteValidator {

    public static void validarAlteracao(Cliente clienteExistente, Cliente clienteAlterado) {
        if (valoresSaoIguais(clienteExistente, clienteAlterado)) {
            throw new ValidationException("Nenhum valor foi alterado.");
        }
    }

     static boolean valoresSaoIguais(Cliente clienteExistente, Cliente clienteAlterado) {
        boolean isNomeIgual = clienteAlterado.getNome() != null && clienteAlterado.getNome().equals(clienteExistente.getNome());
        boolean isSobrenomeIgual = clienteAlterado.getSobrenome() != null && clienteAlterado.getSobrenome().equals(clienteExistente.getSobrenome());
        boolean isTipoContaIgual = clienteAlterado.getTipoConta() != null && clienteAlterado.getTipoConta().equals(clienteExistente.getTipoConta());
        boolean isAgenciaIgual = clienteAlterado.getAgencia() != null && clienteAlterado.getAgencia().equals(clienteExistente.getAgencia());
        boolean isContaIgual = clienteAlterado.getConta() != null && clienteAlterado.getConta().equals(clienteExistente.getConta());

         return isNomeIgual && isSobrenomeIgual && isTipoContaIgual && isAgenciaIgual && isContaIgual;
     }
}
