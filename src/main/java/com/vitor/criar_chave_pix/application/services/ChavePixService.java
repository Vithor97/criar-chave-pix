package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.application.ports.persistence.ChavePixServicePersistencePort;
import com.vitor.criar_chave_pix.application.validators.ChavePixValidatorChain;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChavePixService implements ChaveServicePort {

    private final ContaServicePort contaServicePort;

    private final ChavePixServicePersistencePort chavePixServiceRepository;

    private final ChavePixValidatorChain chavePixValidatorChain;

    public ChavePixService(ContaServicePort contaServicePort, ChavePixServicePersistencePort chavePixServiceRepository) {
        this.contaServicePort = contaServicePort;
        this.chavePixServiceRepository = chavePixServiceRepository;
        this.chavePixValidatorChain = new ChavePixValidatorChain();
    }

    @Override
    public UUID criaChavePix(ClienteChavePix clienteChavePix) {

        var agencia = clienteChavePix.getAgencia();
        var conta = clienteChavePix.getConta();
        var chavePix = clienteChavePix.getChavesPix().getValorChave();
        var tipooChave = clienteChavePix.getChavesPix().getTipoChave();

        chavePixValidatorChain.validate(tipooChave, chavePix);
        //ChavePixValidator.validate(tipooChave, chavePix);


        //verificar se chave pix existe no repositorio, se ja existir lança exception
        if (chavePixServiceRepository.chavepixExistente(clienteChavePix.getChavesPix().getValorChave())) {
            throw new ValidationException("Chave Pix já cadastrada.");
        }

        //busca informações da conta
        //Optional<ClienteChavePix> cliente = chavePixServiceRepository.buscaAgenciaConta(agencia, conta);
        chavePixServiceRepository.buscaAgenciaConta(agencia, conta).ifPresentOrElse(cliente ->{
            clienteChavePix.setIdConta(cliente.getIdConta());
            clienteChavePix.setTipoPessoa(cliente.getTipoPessoa());
            clienteChavePix.setChavesPixList(cliente.getChavesPixList());

            validaCpfExistente(clienteChavePix);
        }, ()->{
            String tipoPessoa = verificaTipoPessoa(clienteChavePix);
            var clienteEntity = geraClienteEntity(clienteChavePix, agencia, conta, tipoPessoa);

            var clienteCriado = chavePixServiceRepository.insereCliente(clienteEntity);

            clienteChavePix.setIdConta(clienteCriado.getId());
            clienteChavePix.setTipoPessoa(tipoPessoa);
        });


        //se não existir salva o cliente e a chave pix
//        if(cliente.isEmpty()){
//            String tipoPessoa = verificaTipoPessoa(clienteChavePix);
//            var clienteCriado = chavePixServiceRepository.insereCliente(
//                    new Cliente(clienteChavePix.getNomeCorrentista(),
//                            clienteChavePix.getSobrenomeCorrentista(),
//                            tipoPessoa,
//                            agencia,
//                            conta,
//                            clienteChavePix.getTipoConta()
//                    ));
//
//            clienteChavePix.setIdConta(clienteCriado.getId());
//            clienteChavePix.setTipoPessoa(tipoPessoa);
//        }
//        //caso contrario atualiza o id da conta, tipoPessoa a lista de chaves
//        else {
//            clienteChavePix.setIdConta(cliente.get().getIdConta());
//            clienteChavePix.setTipoPessoa(cliente.get().getTipoPessoa());
//            clienteChavePix.setChavesPixList(cliente.get().getChavesPixList());
//
//            validaCpfExistente(clienteChavePix);
//        }

        //verifica quantidade de chaves pix de acordo com o tipo pessoa
        validaLimiteChaves(clienteChavePix, clienteChavePix.getChavesPixList().size());

        //criar chave pix
        var newChavePix = new ChavesPix(null ,clienteChavePix.getChavesPix().getTipoChave(), clienteChavePix.getChavesPix().getValorChave(), null, null, true);
        var chavesPixCriada = chavePixServiceRepository.salvaChavePix(newChavePix, clienteChavePix.getIdConta());

        //adicionar a lista de chaves ao dominio
        //clienteChavePix.getChavesPixList().add(chavesPixCriada);

        return chavesPixCriada.getUuidChave();
    }

    private Cliente geraClienteEntity(ClienteChavePix clienteChavePix, Integer agencia, Integer conta, String tipoPessoa) {
        return new Cliente(clienteChavePix.getNomeCorrentista(),
                clienteChavePix.getSobrenomeCorrentista(),
                tipoPessoa,
                agencia,
                conta,
                clienteChavePix.getTipoConta()
        );
    }


    public Optional<ClienteChavePix> consultaChave(UUID uuidChave) {
        return chavePixServiceRepository.buscarPorIdChave(uuidChave);
    }

    @Override
    public List<ClienteChavePix> buscarChavesPixComFiltros(String tipoChave,
                                                           Integer agencia,
                                                           Integer conta,
                                                           String nomeCorrentista,
                                                           String sobrenomeCorrentista,
                                                           LocalDate dataInclusao,
                                                           LocalDate dataInativacao) {

        if(dataInclusao != null && dataInativacao != null){
            throw new ValidationException("Somente data de inclusão ou de inativação para a consulta");
        }
        return chavePixServiceRepository.buscarChavesPixComFiltros(tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao);
    }

    @Override
    public ClienteChavePix desativaChave(UUID id) {
        return chavePixServiceRepository.desativaChave(id);
    }

    private void validaLimiteChaves(ClienteChavePix clienteChavePix, long count) {
        if ("PF".equals(clienteChavePix.getTipoPessoa()) && count >= 5) {
            throw new ValidationException("Limite de 5 chaves Pix para Pessoa Física atingido.");
        }
        else if ("PJ".equals(clienteChavePix.getTipoPessoa()) && count >= 20) {
            throw new ValidationException("Limite de 20 chaves Pix para Pessoa Jurídica atingido.");
        }
    }


    private String verificaTipoPessoa(ClienteChavePix chavePix){
        return contaServicePort.verificaTipoPessoa(chavePix.getAgencia(), chavePix.getConta());
    }

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
