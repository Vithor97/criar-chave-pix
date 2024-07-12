package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.application.validators.ChavePixValidator;
import com.vitor.criar_chave_pix.exceptions.ValidationException;
import com.vitor.criar_chave_pix.persistence.repository.ChavePixServiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ChavePixService implements ChaveServicePort {

    private final ContaServicePort contaServicePort;

    private final ChavePixServiceRepository chavePixServiceRepository;

    public ChavePixService(ContaServicePort contaServicePort, ChavePixServiceRepository chavePixServiceRepository) {
        this.contaServicePort = contaServicePort;
        this.chavePixServiceRepository = chavePixServiceRepository;
    }

    @Override
    public UUID criaChavePix(ClienteChavePix clienteChavePix) {

        var agencia = clienteChavePix.getAgencia();
        var conta = clienteChavePix.getConta();
        var chavePix = clienteChavePix.getChavesPix().getValorChave();
        var tipooChave = clienteChavePix.getChavesPix().getTipoChave();

        ChavePixValidator.validate(tipooChave, chavePix);


        //verificar se chave pix existe no repositorio, se ja existir lança exception
        if (chavePixServiceRepository.chavepixExistente(clienteChavePix.getChavesPix().getValorChave())) {
            throw new IllegalStateException("Chave Pix já cadastrada.");
        }

        //busca informações da conta
        Optional<ClienteChavePix> cliente = chavePixServiceRepository.buscaAgenciaConta(agencia, conta);


        //se não existir salva o cliente e a chave pix
        if(cliente.isEmpty()){
            String tipoPessoa = verificaTipoPessoa(clienteChavePix);
            var clienteCriado = chavePixServiceRepository.insereCliente(
                    new Cliente(clienteChavePix.getNomeCorrentista(),
                            clienteChavePix.getSobrenomeCorrentista(),
                            tipoPessoa,
                            agencia,
                            conta,
                            clienteChavePix.getTipoConta()
                    ));

            clienteChavePix.setIdConta(clienteCriado.getId());
            clienteChavePix.setTipoPessoa(tipoPessoa);
        }
        //caso contrario atualiza o id da conta, tipoPessoa a lista de chaves
        else {
            clienteChavePix.setIdConta(cliente.get().getIdConta());
            clienteChavePix.setTipoPessoa(cliente.get().getTipoPessoa());
            clienteChavePix.setChavesPixList(cliente.get().getChavesPixList());

            validaCpfExistente(clienteChavePix);
        }

        //verifica quantidade de chaves pix de acordo com o tipo pessoa
        validaLimiteChaves(clienteChavePix, clienteChavePix.getChavesPixList().size());

        //criar chave pix
        var newChavePix = new ChavesPix(null ,clienteChavePix.getChavesPix().getTipoChave(), clienteChavePix.getChavesPix().getValorChave(), null, null, true);
        var chavesPixCriada = chavePixServiceRepository.salvaChavePix(newChavePix, clienteChavePix.getIdConta());

        //adicionar a lista de chaves ao dominio
        clienteChavePix.getChavesPixList().add(chavesPixCriada);

        return chavesPixCriada.getUuidChave();
    }

    private void validaLimiteChaves(ClienteChavePix clienteChavePix, long count) {
        if ("PF".equals(clienteChavePix.getTipoPessoa()) && count >= 5) {
            throw new ValidationException("Limite de 5 chaves Pix para Pessoa Física atingido.");
        }
        else if ("PJ".equals(clienteChavePix.getTipoPessoa()) && count >= 20) {
            throw new ValidationException("Limite de 20 chaves Pix para Pessoa Jurídica atingido.");
        }
    }

    //verifica tipoCliente
    private String verificaTipoPessoa(ClienteChavePix chavePix){
        return contaServicePort.verificaTipoPessoa(chavePix.getAgencia(), chavePix.getConta());
    }

    // Método para validar CPF existente
    private void validaCpfExistente(ClienteChavePix clienteChavePix) {
        if ("cpf".equals(clienteChavePix.getChavesPix().getTipoChave()) && !clienteChavePix.getChavesPixList().isEmpty()) {
            var novoCpf = clienteChavePix.getChavesPix().getValorChave();
            for (ChavesPix chave : clienteChavePix.getChavesPixList()) {
                if ("cpf".equals(chave.getTipoChave()) && !chave.getValorChave().equals(novoCpf)) {
                    throw new ValidationException("Já existe um CPF cadastrado para este cliente.");
                }
            }
        }
    }
}
