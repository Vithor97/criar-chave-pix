package com.vitor.criar_chave_pix.persistence.repository;

import com.vitor.criar_chave_pix.persistence.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {
    Optional<ContaEntity> findByAgenciaAndConta(Integer agencia, Integer conta);
}
