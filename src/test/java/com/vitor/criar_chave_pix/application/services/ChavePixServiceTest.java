package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.commons.ConsultaChavePixConstants;
import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.application.ports.persistence.ChavePixServicePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.vitor.criar_chave_pix.commons.ChavePixServiceContants.*;
import static com.vitor.criar_chave_pix.commons.ConsultaChavePixConstants.CLIENTE_CHAVE_PIX_CPF_VALIDO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChavePixServiceTest {

    @Mock
    private ContaServicePort contaServicePort;

    @Mock
    private ChavePixServicePersistencePort chavePixServiceRepository;

    @InjectMocks
    private ChavePixService chavePixService;



    @Test
    public void criariaChavePixSuccesso_Quando_Nao_Existe_Cliente() {
        // given
        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.empty());
        when(chavePixServiceRepository.insereCliente(any(Cliente.class)))
                .thenReturn(new Cliente(1L, "João", "Silva", "PF", 1234, 5678, "corrente"));
        when(chavePixServiceRepository.salvaChavePix(any(ChavesPix.class), any(Long.class)))
                .thenAnswer(invocation -> {
                    ChavesPix pix = invocation.getArgument(0);
                    pix.setUuidChave(UUID.randomUUID());
                    return pix;
                });
        when(contaServicePort.verificaTipoPessoa(any(Integer.class), any(Integer.class)))
                .thenReturn("PF");

        // Act
        UUID uuid = chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO);

        // Assert
        assertNotNull(uuid);
    }

    @Test
    public void criariaChavePixSuccesso_Quando_Nao_Existe_ClientePJ() {
        // given
        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.empty());
        when(chavePixServiceRepository.insereCliente(any(Cliente.class)))
                .thenReturn(new Cliente(1L, "João", "Silva", "PJ", 1234, 5678, "corrente"));
        when(chavePixServiceRepository.salvaChavePix(any(ChavesPix.class), any(Long.class)))
                .thenAnswer(invocation -> {
                    ChavesPix pix = invocation.getArgument(0);
                    pix.setUuidChave(UUID.randomUUID());
                    return pix;
                });
        when(contaServicePort.verificaTipoPessoa(any(Integer.class), any(Integer.class)))
                .thenReturn("PJ");

        // Act
        UUID uuid = chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO);

        // Assert
        assertNotNull(uuid);
    }

    @Test
    public void criarChavePix_comChaveSucesso_ComClientePFJaExistente() {

        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.of(CLIENTE_EXISTENTE_CHAVE_PIXV_VALIDO));
        when(chavePixServiceRepository.salvaChavePix(any(ChavesPix.class), any(Long.class)))
                .thenAnswer(invocation -> {
                    ChavesPix pix = invocation.getArgument(0);
                    pix.setUuidChave(UUID.randomUUID());
                    return pix;
                });

        UUID uuid = chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO);

        assertNotNull(uuid);

    }

    @Test
    public void criarChavePix_comChaveEmailSucesso_ComClientePJJaExistente() {

        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.of(CLIENTE_EXISTENTE_CHAVE_PIX_EMAIL_VALIDO));
        when(chavePixServiceRepository.salvaChavePix(any(ChavesPix.class), any(Long.class)))
                .thenAnswer(invocation -> {
                    ChavesPix pix = invocation.getArgument(0);
                    pix.setUuidChave(UUID.randomUUID());
                    return pix;
                });

        UUID uuid = chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO);

        assertNotNull(uuid);

    }

    @Test
    public void criarChavePix_comCPFDiferenteDeJaExistenteDoCliente() {

        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.of(CLIENTE_EXISTENTE_CHAVEPIX_CPF_EXISTENTE_INVALIDO_));

        assertThatThrownBy(() -> chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void criarChavePix_comChaveJaExistente_LancaExecao() {

        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(true);

        assertThatThrownBy(() -> chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Chave Pix já cadastrada.");
    }

    @Test
    public void criarChavePix_clientePFJaExistenteCom5ChavesPixCadastradas() {

        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.of(CLIENTE_PF_EXISTENTE_MAX5CHAVES_CADASTRADAS_RETONAEXCECAO));

        assertThatThrownBy(() -> chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Limite de 5 chaves Pix para Pessoa Física atingido.");
    }

    @Test
    public void criarChavePix_clientePJJaExistenteCom20ChavesPixCadastradas() {

        when(chavePixServiceRepository.chavepixExistente(anyString())).thenReturn(false);
        when(chavePixServiceRepository.buscaAgenciaConta(any(Integer.class), any(Integer.class)))
                .thenReturn(Optional.of(CLIENTE_PJ_EXISTENTE_MAX20CHAVES_CADASTRADAS_RETONAEXCECAO));

        assertThatThrownBy(() -> chavePixService.criaChavePix(CLIENTE_CHAVE_PIXV_VALIDO))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Limite de 20 chaves Pix para Pessoa Jurídica atingido.");
    }

    @ParameterizedTest
    @MethodSource("provideChavesInvalidas")
    public void criar_chavesPix_com_chaves_invalidas_retorna_excecao(ClienteChavePix clienteChavePix) {
        assertThatThrownBy(() -> chavePixService.criaChavePix(clienteChavePix)).isInstanceOf(ValidationException.class);
    }


    private static Stream<Arguments> provideChavesInvalidas() {
        return Stream.of(
                Arguments.of(new ClienteChavePix(null, "corrente", 1234, 12345678, "PF", "Vitor", "Silva",
                        new ChavesPix(null, "cpf", "9999999999", null, null, true), null
                )),
                Arguments.of(new ClienteChavePix(null, "corrente", 1234, 12345678, "PF", "Vitor", "Silva",
                        new ChavesPix(null, "cnpj", "9999999999", null, null, true), null
                )),
                Arguments.of(new ClienteChavePix(null, "corrente", 1234, 12345678, "PF", "Vitor", "Silva",
                        new ChavesPix(null, "email", "emailinvalido", null, null, true), null
                )),
                Arguments.of(new ClienteChavePix(null, "corrente", 1234, 12345678, "PF", "Vitor", "Silva",
                        new ChavesPix(null, "celular", "1111111", null, null, true), null
                )),
                Arguments.of(new ClienteChavePix(null, "corrente", 1234, 12345678, "PF", "Vitor", "Silva",
                        new ChavesPix(null, "chave_aleatoria", "blablablab1232nfdjsnfjksfnjkehfewhfjkebfkjewbfjkewbfjkwebfjkbwefjbewjkfbewk@fjerfjerfjbrfrejfberjfbj", null, null, true), null
                )),
                Arguments.of(new ClienteChavePix(null, "corrente", 1234, 12345678, "PF", "Vitor", "Silva",
                        new ChavesPix(null, "tipoChaveInexistente", "122345", null, null, true), null
                ))
        );
    }

    //consultas
    @Test
    public void consultaChavePixPorId_RetornaSucesso() {
        when(chavePixServiceRepository.buscarPorIdChave(any(UUID.class))).thenReturn(Optional.of(CLIENTE_CHAVE_PIX_CPF_VALIDO));
        Optional<ClienteChavePix> actual = chavePixService.consultaChave(UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b"));
        assertNotNull(actual);
        verify(chavePixServiceRepository, times(1)).buscarPorIdChave(any(UUID.class));
    }

    @Test
    public void consultaChavePixPorId_chaveNaoCadastrada_retornaVazio() {
        when(chavePixServiceRepository.buscarPorIdChave(any(UUID.class))).thenReturn(Optional.empty());
        assertThat(chavePixService.consultaChave(UUID.randomUUID())).isEmpty();
        verify(chavePixServiceRepository, times(1)).buscarPorIdChave(any(UUID.class));
    }

    @Test
    public void buscarChavesPixComFiltros_sucesso() {
        String tipoChave = "cpf";
        Integer agencia = 1234;
        Integer conta = 12345678;
        String nomeCorrentista = "Vitor";
        String sobrenomeCorrentista = "Santos";
        LocalDate dataInclusao = LocalDate.of(2024, 7, 14);
        LocalDate dataInativacao = null;


        List<ClienteChavePix> clientesEsperado = List.of(ConsultaChavePixConstants.CLIENTE_CHAVE_PIX_CPF_VALIDO);

        when(chavePixServiceRepository.buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao))
                .thenReturn(clientesEsperado);

        List<ClienteChavePix> result = chavePixService.buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao);


        assertThat(result).isEqualTo(clientesEsperado);

        verify(chavePixServiceRepository, times(1)).buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao);
    }

    @Test
    public void buscarChavesPixComFiltros_DataInativacao_sucesso() {
        String tipoChave = "cpf";
        Integer agencia = 1234;
        Integer conta = 12345678;
        String nomeCorrentista = "Vitor";
        String sobrenomeCorrentista = "Santos";
        LocalDate dataInclusao = null;
        LocalDate dataInativacao = LocalDate.of(2024, 7, 14);


        List<ClienteChavePix> clientesEsperado = List.of(ConsultaChavePixConstants.CLIENTE_CHAVE_PIX_CPF_VALIDO);

        when(chavePixServiceRepository.buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao))
                .thenReturn(clientesEsperado);

        List<ClienteChavePix> result = chavePixService.buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao);


        assertThat(result).isEqualTo(clientesEsperado);

        verify(chavePixServiceRepository, times(1)).buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista, dataInclusao, dataInativacao);
    }

    @Test
    public void buscarChavesPixComFiltros_comDataInclusaoEInativacao() {
        String tipoChave = "cpf";
        Integer agencia = 1234;
        Integer conta = 567890;
        String nomeCorrentista = "Vitor";
        String sobrenomeCorrentista = null;
        LocalDate dataInclusao = LocalDate.of(2024, 7, 14);
        LocalDate dataInativacao = LocalDate.of(2024, 7, 14);

        assertThatThrownBy(() -> chavePixService.buscarChavesPixComFiltros(
                tipoChave,
                agencia,
                conta,
                nomeCorrentista,
                sobrenomeCorrentista, dataInclusao, dataInativacao)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Somente data de inclusão ou de inativação para a consulta");

        verify(chavePixServiceRepository, times(0)).buscarChavesPixComFiltros(
                any(), any(), any(), any(), any(), any(), any());

    }

    //desativação

    @Test
    public void desativaChave_sucesso() {
        UUID chaveId = UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b");

        when(chavePixServiceRepository.desativaChave(chaveId)).thenReturn(ConsultaChavePixConstants.CLIENTE_CHAVE_PIX_CPF_DESATIVADO);


        ClienteChavePix result = chavePixService.desativaChave(chaveId);


        assertThat(result).isEqualTo(ConsultaChavePixConstants.CLIENTE_CHAVE_PIX_CPF_DESATIVADO);
        assertThat(result.getChavesPix().isChaveAtivo()).isFalse();
        assertThat(result.getChavesPix().getDataInativacao()).isNotNull();


        verify(chavePixServiceRepository, times(1)).desativaChave(chaveId);
    }

    @Test
    public void desativaChave_falha() {
        UUID chaveId = UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b");


        when(chavePixServiceRepository.desativaChave(chaveId)).thenThrow(new RuntimeException("Erro ao desativar chave"));


        assertThatThrownBy(() -> chavePixService.desativaChave(chaveId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Erro ao desativar chave");


        verify(chavePixServiceRepository, times(1)).desativaChave(chaveId);
    }

}