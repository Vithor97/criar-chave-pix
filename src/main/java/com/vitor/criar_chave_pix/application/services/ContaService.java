package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.adapter.exceptions.NaoEncontradoException;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.application.ports.persistence.ChavePixServicePersistencePort;
import com.vitor.criar_chave_pix.application.validators.ClienteValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class ContaService implements ContaServicePort {

    private final ChavePixServicePersistencePort chavePixServiceRepository;

    public ContaService(ChavePixServicePersistencePort chavePixServiceRepository) {
        this.chavePixServiceRepository = chavePixServiceRepository;
    }

    @Override
    public String verificaTipoPessoa(Integer agencia, Integer conta) {
        //chama API externa para saber se PF ou PJ
        return new Random().nextBoolean() ? "PF" : "PJ";
    }

    @Override
    public Cliente alteraDadosConta(Long id, Cliente clienteAlteracao) {

        Optional<Cliente> clienteBase = chavePixServiceRepository.findClienteById(id);
        if (clienteBase.isEmpty()){
            throw new NaoEncontradoException("Cliente não encontrado");
        }
        var clientEncontrado = clienteBase.get();

        var agenciaContaIgual = (clientEncontrado.getAgencia().equals(clienteAlteracao.getAgencia()) &&
                clientEncontrado.getConta().equals(clienteAlteracao.getConta()));

        ClienteValidator.validarAlteracao(clientEncontrado, clienteAlteracao);
        clienteAlteracao.setId(id);

        return chavePixServiceRepository.alteraDadosCliente(clienteAlteracao,agenciaContaIgual);
    }


}
