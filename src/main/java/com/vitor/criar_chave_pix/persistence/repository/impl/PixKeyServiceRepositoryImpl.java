package com.vitor.criar_chave_pix.persistence.repository.impl;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.converter.ClienteConverter;
import com.vitor.criar_chave_pix.exceptions.ValidationException;
import com.vitor.criar_chave_pix.persistence.entity.ChavesEntity;
import com.vitor.criar_chave_pix.persistence.entity.ContaEntity;
import com.vitor.criar_chave_pix.persistence.repository.ChavePixServiceRepository;
import com.vitor.criar_chave_pix.persistence.repository.ChavesRepository;
import com.vitor.criar_chave_pix.persistence.repository.ContaRepository;
import com.vitor.criar_chave_pix.persistence.repository.dto.ChavePixContaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PixKeyServiceRepositoryImpl implements ChavePixServiceRepository {

    private final ContaRepository contaRepository;
    private final ChavesRepository chavesRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PixKeyServiceRepositoryImpl(ContaRepository contaRepository, ChavesRepository chavesRepository) {
        this.contaRepository = contaRepository;
        this.chavesRepository = chavesRepository;
    }


    @Override
    public Optional<ClienteChavePix> buscaAgenciaConta(Integer agencia, Integer conta) {
        Optional<ContaEntity> contaEntityOpt = contaRepository.findByAgenciaAndConta(agencia, conta);
        return contaEntityOpt.map(entity -> ClienteConverter.toDomain(entity));
    }

    @Override
    public Cliente insereCliente(Cliente cliente) {
        ContaEntity contaEntity = ClienteConverter.toEntity(cliente);
        ContaEntity savedEntity = contaRepository.save(contaEntity);
        return ClienteConverter.toClienteDomain(savedEntity);
    }

    @Override
    public boolean chavepixExistente(String chavePix) {
        return chavesRepository.existsByValorChave(chavePix);
    }

    @Override
    public ChavesPix salvaChavePix(ChavesPix chavePix, Long idConta) {
        ChavesEntity chavesEntity = ClienteConverter.toPixKeyEntity(chavePix);
        var contaentity = new ContaEntity();
        contaentity.setId(idConta);

        chavesEntity.setConta(contaentity);
        ChavesEntity savedEntity = chavesRepository.save(chavesEntity);

        return ClienteConverter.toPixKeyDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> findClienteById(Long id) {
        Optional<ContaEntity> contaEntityOpt = contaRepository.findById(id);
        return contaEntityOpt.map(ClienteConverter::toContaDomain);
    }

    @Override
    @Transactional
    public Cliente alteraDadosCliente(Cliente clienteAlteracao, boolean agenciaContaIgual) {
        try {
            if(agenciaContaIgual){
                contaRepository.atualizaDadosCliente(
                        clienteAlteracao.getId(),
                        clienteAlteracao.getNome(),
                        clienteAlteracao.getSobrenome(),
                        clienteAlteracao.getTipoConta()
                );
            }
            else {
                contaRepository.atualizaDadosCliente(
                        clienteAlteracao.getId(),
                        clienteAlteracao.getNome(),
                        clienteAlteracao.getSobrenome(),
                        clienteAlteracao.getTipoConta(),
                        clienteAlteracao.getAgencia(),
                        clienteAlteracao.getConta()
                );
            }


            entityManager.clear();

            ContaEntity updatedContaEntity = contaRepository.findById(clienteAlteracao.getId())
                                        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

            return ClienteConverter.toContaDomain(updatedContaEntity);

        }
        catch (DataIntegrityViolationException e){
            throw new ValidationException("Erro ao alterar dados do cliente. Agencia e Conta já existem para outro cliente.");
        }
        catch (Exception e){
            throw new IllegalArgumentException("Erro ao alterar dados do cliente.");
        }
    }

    @Override
    public Optional<ClienteChavePix> buscarPorIdChave(UUID uuidChave) {
        Optional<ChavePixContaDTO> chavesEntityOpt = chavesRepository.findByIdWithConta(uuidChave);
        return chavesEntityOpt.map(ChavePixContaDTO::toDomain);
    }

    @Override
    public List<ClienteChavePix> buscarChavesPixComFiltros(String tipoChave,
                                                           Integer agencia,
                                                           Integer conta,
                                                           String nomeCorrentista,
                                                           String sobrenomeCorrentista,
                                                           LocalDate dataInclusao, LocalDate dataInativacao) {

        LocalDateTime dataInclusaoInicio = (dataInclusao != null) ? dataInclusao.atStartOfDay() : null;
        LocalDateTime dataInclusaoFim = (dataInclusao != null) ? dataInclusao.atTime(LocalTime.MAX) : null;

        LocalDateTime dataInativacaoInicio = (dataInativacao != null) ? dataInativacao.atStartOfDay() : null;
        LocalDateTime dataInativacaoFim = (dataInativacao != null) ? dataInativacao.atTime(LocalTime.MAX) : null;



        List<ChavePixContaDTO> results = chavesRepository.findByAllFilters(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista,
                dataInclusaoInicio, dataInclusaoFim,
                dataInativacaoInicio, dataInativacaoFim);

        return results.stream()
                .map(ChavePixContaDTO::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteChavePix desativaChave(UUID id) {
        Optional<ChavesEntity> chavesEntityOpt = chavesRepository.findById(id);
        if(chavesEntityOpt.isEmpty()){
            throw new ValidationException("Chave nao enconstrada");
        }

        var chaveEncontrada = chavesEntityOpt.get();
        if(!chaveEncontrada.isAtivo()){
            throw new ValidationException("Chave ja desativada");
        }

        LocalDateTime agora = LocalDateTime.now();
        chavesRepository.desativarChave(id, agora);

        chaveEncontrada.setAtivo(false);
        chaveEncontrada.setDataInativacao(agora);

        ContaEntity conta = chaveEncontrada.getConta();


        return ClienteChavePix.builder()
                .idConta(conta.getId())
                .tipoConta(conta.getTipoConta())
                .agencia(conta.getAgencia())
                .conta(conta.getConta())
                .tipoPessoa(conta.getTipoPessoa())
                .nomeCorrentista(conta.getNomeCorrentista())
                .sobrenomeCorrentista(conta.getSobrenomeCorrentista())
                .chavesPix(new ChavesPix(
                        chaveEncontrada.getId(),
                        chaveEncontrada.getTipoChave(),
                        chaveEncontrada.getValorChave(),
                        chaveEncontrada.getDataRegistro(),
                        chaveEncontrada.getDataInativacao(),
                        chaveEncontrada.isAtivo()))
                .build();
    }
}
