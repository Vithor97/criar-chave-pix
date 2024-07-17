package com.vitor.criar_chave_pix.adapter.output.persistence.repository.impl;

import com.vitor.criar_chave_pix.adapter.converter.ClienteConverter;
import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.adapter.output.persistence.entity.ChavesEntity;
import com.vitor.criar_chave_pix.adapter.output.persistence.entity.ContaEntity;
import com.vitor.criar_chave_pix.adapter.output.persistence.repository.ChavesRepository;
import com.vitor.criar_chave_pix.adapter.output.persistence.repository.ContaRepository;
import com.vitor.criar_chave_pix.adapter.output.persistence.repository.dto.ChavePixContaDTO;
import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.vitor.criar_chave_pix.commons.lista.ListarConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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

    @Test
    public void insereCliente_ReturnaCliente(){
        Cliente cliente = new Cliente(
                1L,
                "Vitor",
                "Santos",
                "PF",
                1234,
                12345678,
                "corrente"
        );

        ContaEntity contaEntity = ClienteConverter.toEntity(cliente);

        when(contaRepository.save(any(ContaEntity.class))).thenReturn(contaEntity);
        Cliente result = pixKeyServiceRepositoryImpl.insereCliente(cliente);

        assertThat(result).isNotNull();
        assertThat(result.getAgencia()).isEqualTo(1234);
        assertThat(result.getConta()).isEqualTo(12345678);
        assertThat(result.getTipoConta()).isEqualTo("corrente");
        assertThat(result.getNome()).isEqualTo("Vitor");
        assertThat(result.getSobrenome()).isEqualTo("Santos");

        verify(contaRepository).save(any(ContaEntity.class));
    }


    @Test
    public void chavepixExistente_ReturnoBoolean() {
        String existentKey = "chaveExistente";
        String nonExistentKey = "chaveNaoExistente";

        when(chavesRepository.existsByValorChave(existentKey)).thenReturn(true);
        when(chavesRepository.existsByValorChave(nonExistentKey)).thenReturn(false);

        boolean resultForExistent = pixKeyServiceRepositoryImpl.chavepixExistente(existentKey);
        assertTrue(resultForExistent);

        boolean resultForNonExistent = pixKeyServiceRepositoryImpl.chavepixExistente(nonExistentKey);
        assertFalse(resultForNonExistent);
    }

    @Test
    public void salvaChavePix_ReturnaChavePix() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        ChavesEntity chavesEntity = new ChavesEntity(
                uuid,
                null,
                "cpf",
                "11111111111",
                now,
                null,
                true
        );

        ChavesPix chavesPixDomain = ChavesPix.builder().tipoChave("cpf")
                .valorChave("11111111111")
                .chaveAtivo(true)
                .dataCriacao(now)
                .uuidChave(uuid)
                .build();


        when(chavesRepository.save(any(ChavesEntity.class))).thenReturn(chavesEntity);
        ChavesPix result = pixKeyServiceRepositoryImpl.salvaChavePix(chavesPixDomain, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getValorChave()).isEqualTo("11111111111");
        assertThat(result.getUuidChave()).isEqualTo(uuid);
        assertThat(result.getDataCriacao()).isEqualTo(now);

        verify(chavesRepository).save(any(ChavesEntity.class));
    }

    @Test
    public void buscarPorIdChave_WhenFound_ReturnsClienteChavePix() {
        UUID uuid = UUID.randomUUID();
        ChavePixContaDTO chavePixContaDTO = new ChavePixContaDTO(
                uuid,
                "cpf",
                "11111111111",
                "corrente",
                1234,
                12345678,
                "Vitor",
                "Santos",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Optional<ChavePixContaDTO> optionalDto = Optional.of(chavePixContaDTO);
        when(chavesRepository.findByIdWithConta(uuid)).thenReturn(optionalDto);
        Optional<ClienteChavePix> result = pixKeyServiceRepositoryImpl.buscarPorIdChave(uuid);
        assertTrue(result.isPresent());
    }

    @Test
    public void buscarPorIdChave_WhenNotFound_ReturnsEmpty() {
        UUID uuid = UUID.randomUUID();
        when(chavesRepository.findByIdWithConta(uuid)).thenReturn(Optional.empty());
        Optional<ClienteChavePix> result = pixKeyServiceRepositoryImpl.buscarPorIdChave(uuid);
        assertFalse(result.isPresent());
    }

    @Test
    public void findClienteById_WhenFound_ReturnsCliente() {
        Long id = 1L;
        ContaEntity contaEntity = new ContaEntity(
                1L, "corrente", 1234, 12345678, "Vitor", "Santos", "PF", null
        );

        when(contaRepository.findById(id)).thenReturn(Optional.of(contaEntity));

        Optional<Cliente> result = pixKeyServiceRepositoryImpl.findClienteById(id);

        assertThat(result).isPresent();
        result.ifPresent(cliente -> {
            assertThat(cliente.getNome()).isEqualTo("Vitor");
            assertThat(cliente.getSobrenome()).isEqualTo("Santos");
            assertThat(cliente.getTipoPessoa()).isEqualTo("PF");
            assertThat(cliente.getAgencia()).isEqualTo(1234);
            assertThat(cliente.getConta()).isEqualTo(12345678);
        });
    }

    @Test
    public void findClienteById_WhenNotFound_ReturnsEmpty() {
        Long id = 2L;
        when(contaRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Cliente> result = pixKeyServiceRepositoryImpl.findClienteById(id);
        assertFalse(result.isPresent());
    }


    @Test
    public void alteraDadosCliente_WithAgenciaContaIgualTrue() {
        Cliente clienteAlteracao = new Cliente(1L, "Vitorr", "Santos De Miranda", "PF", 4321, 123455, "corrente");


        doNothing().when(contaRepository).atualizaDadosCliente(
                clienteAlteracao.getId(),
                clienteAlteracao.getNome(),
                clienteAlteracao.getSobrenome(),
                clienteAlteracao.getTipoConta());

        pixKeyServiceRepositoryImpl.alteraDadosCliente(clienteAlteracao, true);

        verify(contaRepository).atualizaDadosCliente(clienteAlteracao.getId(), clienteAlteracao.getNome(), clienteAlteracao.getSobrenome(), clienteAlteracao.getTipoConta());

    }

    @Test
    public void alteraDadosCliente_WithAgenciaContaIgualFalse() {
        Cliente clienteAlteracao = new Cliente(1L, "Vitorr", "Santos De Miranda", "PF", 4321, 123455, "corrente");

        doNothing().when(contaRepository).atualizaDadosCliente(
                clienteAlteracao.getId(),
                clienteAlteracao.getNome(),
                clienteAlteracao.getSobrenome(),
                clienteAlteracao.getTipoConta(),
                clienteAlteracao.getAgencia(),
                clienteAlteracao.getConta());

        pixKeyServiceRepositoryImpl.alteraDadosCliente(clienteAlteracao, false);

        verify(contaRepository).atualizaDadosCliente(clienteAlteracao.getId(), clienteAlteracao.getNome(), clienteAlteracao.getSobrenome(), clienteAlteracao.getTipoConta(), clienteAlteracao.getAgencia(), clienteAlteracao.getConta());

    }

    @Test
    public void alteraDadosCliente_ThrowsDataIntegrityViolationException() {
        Cliente clienteAlteracao = new Cliente(1L, "Vitorr", "Santos De Miranda", "PF", 4321, 123455, "corrente");

        doThrow(new DataIntegrityViolationException("Agencia e Conta já existem para outro cliente."))
                .when(contaRepository).atualizaDadosCliente(anyLong(), anyString(), anyString(), anyString(), anyInt(), anyInt());

        assertThatThrownBy(() -> pixKeyServiceRepositoryImpl.alteraDadosCliente(clienteAlteracao, false))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Erro ao alterar dados do cliente. Agencia e Conta já existem para outro cliente.");
    }

    @Test
    public void testBuscarChavesPixComTodosOsFiltros() {
        when(chavesRepository.findByAllFilters(
                TIPO_CHAVE, AGENCIA, CONTA, NOME_CORRENTISTA, SOBRENOME_CORRENTISTA,
                DATA_REGISTRO, DATA_REGISTRO.toLocalDate().atTime(LocalTime.MAX),
                DATA_INATIVACAO, DATA_INATIVACAO.toLocalDate().atTime(LocalTime.MAX)
        )).thenReturn(Arrays.asList(CHAVE_PIX_CONTA_DTO));

        List<ClienteChavePix> results = pixKeyServiceRepositoryImpl.buscarChavesPixComFiltros(
                TIPO_CHAVE, AGENCIA, CONTA, NOME_CORRENTISTA, SOBRENOME_CORRENTISTA,
                DATA_REGISTRO.toLocalDate(), DATA_INATIVACAO.toLocalDate()
        );

        assertThat(results).hasSize(1);
        assertThat(results.get(0)).usingRecursiveComparison().isEqualTo(toDomain(CHAVE_PIX_CONTA_DTO));
    }

    @Test
    public void testBuscarChavesPixPorAgenciaEConta() {
        when(chavesRepository.findByAllFilters(
                null, AGENCIA, CONTA, null, null, null, null, null, null
        )).thenReturn(Arrays.asList(CHAVE_PIX_CONTA_DTO));

        List<ClienteChavePix> results = pixKeyServiceRepositoryImpl.buscarChavesPixComFiltros(
                null, AGENCIA, CONTA, null, null, null, null
        );

        assertThat(results).hasSize(1);
    }

    @Test
    public void testBuscarChavesPixPorTipoChave() {
        when(chavesRepository.findByAllFilters(
                TIPO_CHAVE, null, null, null, null, null, null, null, null
        )).thenReturn(Arrays.asList(CHAVE_PIX_CONTA_DTO));

        List<ClienteChavePix> results = pixKeyServiceRepositoryImpl.buscarChavesPixComFiltros(
                TIPO_CHAVE, null, null, null, null, null, null
        );

        assertThat(results).hasSize(1);
    }

    @Test
    public void testBuscarChavesPixPorDataInclusao() {
        when(chavesRepository.findByAllFilters(
                null, null, null, null, null, DATA_REGISTRO, DATA_REGISTRO.toLocalDate().atTime(LocalTime.MAX), null, null
        )).thenReturn(Arrays.asList(CHAVE_PIX_CONTA_DTO));

        List<ClienteChavePix> results = pixKeyServiceRepositoryImpl.buscarChavesPixComFiltros(
                null, null, null, null, null, DATA_REGISTRO.toLocalDate(), null
        );

        assertThat(results).hasSize(1);
    }

    @Test
    public void testBuscarChavesPixSemResultados() {
        when(chavesRepository.findByAllFilters(
                "email", null, null, null, null, null, null, null, null
        )).thenReturn(Collections.emptyList());

        List<ClienteChavePix> results = pixKeyServiceRepositoryImpl.buscarChavesPixComFiltros(
                "email", null, null, null, null, null, null
        );

        assertThat(results).isEmpty();
    }

    @Test
    public void desativaChavePixComSucessoRetornaClienteChavePix(){

        UUID uuid = UUID.randomUUID();

        var dataRegistro = LocalDateTime.parse("16/07/2024 21:03",
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        var now = LocalDateTime.now();

        var clienteChavePixEsperado =
                ClienteChavePix.builder()
                        .idConta(1L)
                        .tipoConta("corrente")
                        .agencia(1234)
                        .conta(123456)
                        .tipoPessoa("pf")
                        .nomeCorrentista("Vitor")
                        .sobrenomeCorrentista("Santos de Miranda")
                        .chavesPix(new ChavesPix(
                                uuid,
                                "email",
                                "vitor@email.com",
                                dataRegistro,
                                now,
                                false))
                        .build();

        ContaEntity contaEntity = new ContaEntity(1L, "corrente", 1234, 123456, "Vitor", "Santos de Miranda", "pf");

        ChavesEntity chavesEntity = new ChavesEntity(
                uuid,
                contaEntity,
                "email",
                "vitor@email.com",
                dataRegistro,
                now,
                true
        );


        when(chavesRepository.findById(uuid)).thenReturn(Optional.of(chavesEntity));
        doNothing().when(chavesRepository).desativarChave(eq(uuid), any(LocalDateTime.class));

        var result = pixKeyServiceRepositoryImpl.desativaChave(uuid);

        assertThat(result.getIdConta()).isEqualTo(clienteChavePixEsperado.getIdConta());
        assertThat(result.getTipoConta()).isEqualTo(clienteChavePixEsperado.getTipoConta());
        assertThat(result.getAgencia()).isEqualTo(clienteChavePixEsperado.getAgencia());
        assertThat(result.getConta()).isEqualTo(clienteChavePixEsperado.getConta());
        assertThat(result.getTipoPessoa()).isEqualTo(clienteChavePixEsperado.getTipoPessoa());
        assertThat(result.getNomeCorrentista()).isEqualTo(clienteChavePixEsperado.getNomeCorrentista());
        assertThat(result.getSobrenomeCorrentista()).isEqualTo(clienteChavePixEsperado.getSobrenomeCorrentista());
        assertThat(result.getChavesPix().isChaveAtivo()).isFalse();
        assertThat(result.getChavesPix().getDataInativacao()).isNotNull();

    }

    @Test
    public void deveLancarExcecaoQuandoChaveNaoEncontrada() {
        UUID uuid = UUID.randomUUID();
        when(chavesRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pixKeyServiceRepositoryImpl.desativaChave(uuid))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void deveLancarExcecaoQuandoChaveJaDesativada() {
        UUID uuid = UUID.randomUUID();
        ChavesEntity chavesEntity = new ChavesEntity(uuid, null, "email", "vitor@email.com", null, null, false);
        when(chavesRepository.findById(uuid)).thenReturn(Optional.of(chavesEntity));

        assertThatThrownBy(() -> pixKeyServiceRepositoryImpl.desativaChave(uuid))
                .isInstanceOf(ValidationException.class);
    }

}