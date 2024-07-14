package com.vitor.criar_chave_pix.application.ports;

import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChaveServicePort {
    UUID criaChavePix(ClienteChavePix chavePix);

    Optional<ClienteChavePix> consultaChave(UUID uuidChave);


    List<ClienteChavePix> buscarChavesPixComFiltros(String tipoChave,
                                                    Integer agencia,
                                                    Integer conta,
                                                    String nomeCorrentista,
                                                    String sobrenomeCorrentista,
                                                    LocalDate dataInclusao,
                                                    LocalDate dataInativacao);
}
