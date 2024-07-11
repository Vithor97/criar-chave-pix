package com.vitor.criar_chave_pix.converter;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.persistence.entity.ChavesEntity;
import com.vitor.criar_chave_pix.persistence.entity.ContaEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteConverter {

    public static ClienteChavePix toDomain(ContaEntity entity) {
        List<ChavesPix> listaChavesPix = entity.getChavesPixList().stream()
                .map(ClienteConverter::toPixKeyDomain)
                .collect(Collectors.toList());

        return new ClienteChavePix(
                entity.getId(),
                entity.getTipoConta(),
                entity.getAgencia(),
                entity.getConta(),
                entity.getTipoPessoa(),
                entity.getNomeCorrentista(),
                entity.getSobrenomeCorrentista(),
                null,
                listaChavesPix
        );
    }

    public static ChavesPix toPixKeyDomain(ChavesEntity entity) {
        return new ChavesPix(
                entity.getId(),
                entity.getTipoChave(),
                entity.getValorChave(),
                entity.getDataRegistro(),
                entity.getDataInativacao(),
                entity.isAtivo()
        );
    }

    public static Cliente toClienteDomain(ContaEntity entity) {
        return new Cliente(
                entity.getId(),
                entity.getNomeCorrentista(),
                entity.getSobrenomeCorrentista(),
                entity.getTipoPessoa(),
                entity.getAgencia(),
                entity.getConta(),
                entity.getTipoConta()
        );
    }

    public static ContaEntity toEntity(Cliente cliente) {
        return new ContaEntity(
                cliente.getId(),
                cliente.getTipoConta(),
                cliente.getAgencia(),
                cliente.getConta(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getTipoPessoa(),
                null // Lista de chaves será preenchida separadamente
        );
    }

    public static ChavesEntity toPixKeyEntity(ChavesPix pixKey) {
        return new ChavesEntity(
                pixKey.getUuidChave(),
                null, // Será definido ao salvar a entidade
                pixKey.getTipoChave(),
                pixKey.getValorChave(),
                pixKey.getDataCriacao(),
                pixKey.getDataInativacao(),
                pixKey.isChaveAtivo()
        );
    }
}