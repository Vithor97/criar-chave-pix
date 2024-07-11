package com.vitor.criar_chave_pix.persistence.repository.impl;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.converter.ClienteConverter;
import com.vitor.criar_chave_pix.persistence.entity.ChavesEntity;
import com.vitor.criar_chave_pix.persistence.entity.ContaEntity;
import com.vitor.criar_chave_pix.persistence.repository.ChavePixServiceRepository;
import com.vitor.criar_chave_pix.persistence.repository.ChavesRepository;
import com.vitor.criar_chave_pix.persistence.repository.ContaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PixKeyServiceRepositoryImpl implements ChavePixServiceRepository {

    private final ContaRepository contaRepository;
    private final ChavesRepository chavesRepository;

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
}
