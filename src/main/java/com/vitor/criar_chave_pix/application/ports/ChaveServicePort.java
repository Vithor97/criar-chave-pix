package com.vitor.criar_chave_pix.application.ports;

import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.util.UUID;

public interface ChaveServicePort {
    UUID criaChavePix(ClienteChavePix chavePix);

}
