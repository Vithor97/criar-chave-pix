package com.vitor.criar_chave_pix.persistence.repository;

import com.vitor.criar_chave_pix.persistence.entity.ChavesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChavesRepository extends JpaRepository<ChavesEntity, Long> {
    boolean existsByValorChave(String valorChave);
    long countByContaId(Long contaId);
}