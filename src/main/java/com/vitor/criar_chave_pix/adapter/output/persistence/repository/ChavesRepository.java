package com.vitor.criar_chave_pix.adapter.output.persistence.repository;

import com.vitor.criar_chave_pix.adapter.output.persistence.entity.ChavesEntity;
import com.vitor.criar_chave_pix.adapter.output.persistence.repository.dto.ChavePixContaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChavesRepository extends JpaRepository<ChavesEntity, UUID> {
    boolean existsByValorChave(String valorChave);
    @Query(value = "SELECT new com.vitor.criar_chave_pix.persistence.repository.dto.ChavePixContaDTO" +
            "(c.id, c.tipoChave, c.valorChave, " +
            "ac.tipoConta, ac.agencia, ac.conta, ac.nomeCorrentista, ac.sobrenomeCorrentista, " +
            "c.dataRegistro, c.dataInativacao) " +
            "FROM ChavesEntity c " +
            "JOIN c.conta ac " +
            "WHERE c.id = :id")
    Optional<ChavePixContaDTO> findByIdWithConta(@Param("id") UUID id);

    @Query("SELECT new com.vitor.criar_chave_pix.persistence.repository.dto.ChavePixContaDTO(" +
            "c.id, c.tipoChave, c.valorChave, " +
            "ac.tipoConta, ac.agencia, ac.conta, " +
            "ac.nomeCorrentista, ac.sobrenomeCorrentista, " +
            "c.dataRegistro, c.dataInativacao) " +
            "FROM ChavesEntity c " +
            "JOIN c.conta ac " +
            "WHERE (:tipoChave IS NULL OR c.tipoChave = :tipoChave) " +
            "AND (:agencia IS NULL OR ac.agencia = :agencia) " +
            "AND (:conta IS NULL OR ac.conta = :conta) " +
            "AND (:nomeCorrentista IS NULL OR ac.nomeCorrentista LIKE %:nomeCorrentista%) " +
            "AND (:sobrenomeCorrentista IS NULL OR ac.sobrenomeCorrentista LIKE %:sobrenomeCorrentista%) " +
            "AND (:dataInclusao IS NULL OR (c.dataRegistro BETWEEN :dataInclusao AND :dataInclusaoFim)) " +
            "AND (:dataInativacao IS NULL OR (c.dataInativacao BETWEEN :dataInativacao AND :dataInativacaoFim)) " +
            "ORDER BY c.dataRegistro ASC")
    List<ChavePixContaDTO> findByAllFilters(
            @Param("tipoChave") String tipoChave,
            @Param("agencia") Integer agencia,
            @Param("conta") Integer conta,
            @Param("nomeCorrentista") String nomeCorrentista,
            @Param("sobrenomeCorrentista") String sobrenomeCorrentista,
            @Param("dataInclusao") LocalDateTime dataInclusao,
            @Param("dataInclusaoFim") LocalDateTime dataInclusaoFim,
            @Param("dataInativacao") LocalDateTime dataInativacao,
            @Param("dataInativacaoFim") LocalDateTime dataInativacaoFim
    );

    Optional<ChavesEntity> findByIdAndAtivo(UUID id, boolean ativo);

    @Modifying
    @Query(value = "UPDATE tb_chave_pix SET ativo = false, data_inativacao = :dataInativacao WHERE id = :id", nativeQuery = true)
    void desativarChave(@Param("id") UUID id, @Param("dataInativacao") LocalDateTime dataInativacao);
}