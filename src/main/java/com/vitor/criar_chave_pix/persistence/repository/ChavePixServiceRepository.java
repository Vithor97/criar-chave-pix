package com.vitor.criar_chave_pix.persistence.repository;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChavePixServiceRepository {
    Optional<ClienteChavePix> buscaAgenciaConta(Integer agencia, Integer conta);
    Cliente insereCliente(Cliente cliente);
    boolean chavepixExistente(String chavePix);

    ChavesPix salvaChavePix(ChavesPix chavePix, Long idConta);

    Optional<Cliente> findClienteById(Long id);

    Cliente alteraDadosCliente(Cliente clienteAlteracao, boolean agenciaContaIgual);

    Optional<ClienteChavePix> buscarPorIdChave(UUID uuidChave);

    List<ClienteChavePix> buscarChavesPixComFiltros(
            String tipoChave,
            Integer agencia,
            Integer conta,
            String nomeCorrentista,
            String sobrenomeCorrentista,
            LocalDate dataInclusao,
            LocalDate dataInativacao);

    ClienteChavePix desativaChave(UUID id);
}
