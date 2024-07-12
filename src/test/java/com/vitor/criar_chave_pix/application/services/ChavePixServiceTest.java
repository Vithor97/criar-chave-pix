package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.exceptions.ValidationException;
import com.vitor.criar_chave_pix.persistence.repository.ChavePixServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.vitor.criar_chave_pix.commons.ChavePixServiceContants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChavePixServiceTest {

    @Mock
    private ContaServicePort contaServicePort;

    @Mock
    private ChavePixServiceRepository chavePixServiceRepository;

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
}