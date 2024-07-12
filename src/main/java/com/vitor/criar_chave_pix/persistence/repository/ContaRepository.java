package com.vitor.criar_chave_pix.persistence.repository;

import com.vitor.criar_chave_pix.persistence.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {
    Optional<ContaEntity> findByAgenciaAndConta(Integer agencia, Integer conta);

    @Modifying
    @Query(value = "UPDATE tb_conta SET " +
            "nome_correntista = :nome, " +
            "sobrenome_correntista = :sobrenome, " +
            "tipo_conta = :tipoConta, " +
            "agencia = :agencia, " +
            "conta = :conta " +
            "WHERE id = :id", nativeQuery = true)
    void atualizaDadosCliente(@Param("id") Long id,
                       @Param("nome") String nome,
                       @Param("sobrenome") String sobrenome,
                       @Param("tipoConta") String tipoConta,
                       @Param("agencia") Integer agencia,
                       @Param("conta") Integer conta);

    @Modifying
    @Query(value = "UPDATE tb_conta SET " +
            "nome_correntista = :nome, " +
            "sobrenome_correntista = :sobrenome, " +
            "tipo_conta = :tipoConta " +
            "WHERE id = :id", nativeQuery = true)
    void atualizaDadosCliente(@Param("id") Long id,
                                      @Param("nome") String nome,
                                      @Param("sobrenome") String sobrenome,
                                      @Param("tipoConta") String tipoConta);
}
