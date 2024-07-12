package com.vitor.criar_chave_pix.application.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    ContaService contaService;

    @Test
    void verificaTipoPessoa() {
        String tipoPessoa = contaService.verificaTipoPessoa(1234, 5678456);
        assertNotNull(tipoPessoa);
    }

}