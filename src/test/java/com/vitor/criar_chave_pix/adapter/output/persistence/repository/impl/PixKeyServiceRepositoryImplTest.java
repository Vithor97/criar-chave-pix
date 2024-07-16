package com.vitor.criar_chave_pix.adapter.output.persistence.repository.impl;

import com.vitor.criar_chave_pix.adapter.output.persistence.entity.ChavesEntity;
import com.vitor.criar_chave_pix.adapter.output.persistence.entity.ContaEntity;
import com.vitor.criar_chave_pix.adapter.output.persistence.repository.ChavesRepository;
import com.vitor.criar_chave_pix.adapter.output.persistence.repository.ContaRepository;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PixKeyServiceRepositoryImplTest {

    @InjectMocks
    PixKeyServiceRepositoryImpl pixKeyServiceRepositoryImpl;

    @Mock
    private ChavesRepository chavesRepository;

    @Mock
    private ContaRepository contaRepository;


    @Test
    public void buscaAgenciaEConta_RetornaClienteChavePix(){


        ChavesEntity chavesEntity1 = new ChavesEntity(
                UUID.randomUUID(),
                null,
                "cpf",
                "11111111111",
                LocalDateTime.now(),
                null,
                true
        );

        List<ChavesEntity> listChaves = Arrays.asList(chavesEntity1);

        ContaEntity contaEntity = new ContaEntity(1L,
                "corrente",
                1234, 12345678,
                "Vitor", "Santos",
                "pessoa",
                listChaves
        );



        when(contaRepository.findByAgenciaAndConta(anyInt(), anyInt())).thenReturn(Optional.of(contaEntity));
        Optional<ClienteChavePix> result = pixKeyServiceRepositoryImpl.buscaAgenciaConta(9999, 8888);

        assertThat(result).isNotEmpty();
        assertThat(result.get().getAgencia()).isEqualTo(1234);
        assertThat(result.get().getConta()).isEqualTo(12345678);

    }

    @Test
    public void buscaAgenciaConta_ContaInexistente_ReturnsEmpty() {
        when(contaRepository.findByAgenciaAndConta(anyInt(), anyInt())).thenReturn(Optional.empty());
        Optional<ClienteChavePix> result = pixKeyServiceRepositoryImpl.buscaAgenciaConta(9999, 8888);

        assertThat(result).isEmpty();
    }

}