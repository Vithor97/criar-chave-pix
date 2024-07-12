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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
