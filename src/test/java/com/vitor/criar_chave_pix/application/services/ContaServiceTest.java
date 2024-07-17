package com.vitor.criar_chave_pix.application.services;

import com.vitor.criar_chave_pix.adapter.exceptions.NaoEncontradoException;
import com.vitor.criar_chave_pix.application.domain.Cliente;
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

import java.util.Optional;
import java.util.stream.Stream;

import static com.vitor.criar_chave_pix.commons.ContaServiceConstants.ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO;
import static com.vitor.criar_chave_pix.commons.ContaServiceConstants.ALTERACAO_NOME_DADOS_CLIENTE_VALIDO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    ContaService contaService;

    @Mock
    private ChavePixServicePersistencePort chavePixServiceRepository;

    @Test
    void verificaTipoPessoa() {
        String tipoPessoa = contaService.verificaTipoPessoa(1234, 5678456);
        assertNotNull(tipoPessoa);
    }



    @Test
    public void alteraDadosCliente_e_conta_igual_retorna_sucesso(){
        long id = 1L;
        when(chavePixServiceRepository.findClienteById(id)).thenReturn(Optional.of(ALTERACAO_NOME_DADOS_CLIENTE_VALIDO));
        doNothing().when(chavePixServiceRepository).alteraDadosCliente(ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO, true);

        var result = contaService.alteraDadosConta(id, ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO);
        assertNotNull(result);
        assertEquals(result.getNome(), ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO.getNome());
        assertEquals(result.getSobrenome(), ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO.getSobrenome());
        assertEquals(result.getConta(), ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO.getConta());
        assertEquals(result.getAgencia(), ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO.getAgencia());
    }


    @Test
    public void alteraDadosCliente_todos_campos_validos_retorna_sucesso(){
        long id = 2L;

        // Configuração dos clientes para o teste
        Cliente clienteExistente = new Cliente(id, "Giovana1", "Santos",
                null , 1234, 12345678, "corrente");

        Cliente clienteAlterado = new Cliente(id, "Giovanna", "Santos Costa",
                null, 4321, 87654321, "poupanca");

        when(chavePixServiceRepository.findClienteById(id)).thenReturn(Optional.of(clienteExistente));
        doNothing().when(chavePixServiceRepository).alteraDadosCliente(clienteAlterado, false);

        var result = contaService.alteraDadosConta(id, clienteAlterado);

        assertNotNull(result);
        assertEquals(clienteAlterado.getNome(), result.getNome());
        assertEquals(clienteAlterado.getSobrenome(), result.getSobrenome());
        assertEquals(clienteAlterado.getTipoConta(), result.getTipoConta());
        assertEquals(clienteAlterado.getAgencia(), result.getAgencia());
        assertEquals(clienteAlterado.getConta(), result.getConta());

    }

    @Test
    public void alteraDadosCliente_sobrenomeNuloNaEntrada_mantemSobrenomeExistente() {
        long id = 2L;

        Cliente clienteExistente = new Cliente(id, "Giovana", "Santos",
                null, 1234, 12345678, "corrente");

        Cliente clienteAlterado = new Cliente(id, "Giovana", null,
                null, 4321, 87654321, "poupanca");

        when(chavePixServiceRepository.findClienteById(id)).thenReturn(Optional.of(clienteExistente));
        doNothing().when(chavePixServiceRepository).alteraDadosCliente(clienteAlterado, false);

        var result = contaService.alteraDadosConta(id, clienteAlterado);

        assertNotNull(result);
        assertEquals(clienteAlterado.getNome(), result.getNome());
        assertEquals(clienteExistente.getSobrenome(), result.getSobrenome()); // Mantém o sobrenome existente
        assertEquals(clienteAlterado.getTipoConta(), result.getTipoConta());
        assertEquals(clienteAlterado.getAgencia(), result.getAgencia());
        assertEquals(clienteAlterado.getConta(), result.getConta());
    }

    @Test
    public void alteraDadosCliente_ContaDiferente_mantemSobrenomeExistente() {
        long id = 2L;

        Cliente clienteExistente = new Cliente(id, "Giovana", "Santos",
                null, 1234, 12345678, "corrente");

        Cliente clienteAlterado = new Cliente(id, "Giovana", "Santos",
                null, 1234, 12345698, "corrente");

        when(chavePixServiceRepository.findClienteById(id)).thenReturn(Optional.of(clienteExistente));
        doNothing().when(chavePixServiceRepository).alteraDadosCliente(clienteAlterado, false);

        var result = contaService.alteraDadosConta(id, clienteAlterado);

        assertNotNull(result);
        assertEquals(clienteAlterado.getNome(), result.getNome());
        assertEquals(clienteExistente.getSobrenome(), result.getSobrenome()); // Mantém o sobrenome existente
        assertEquals(clienteAlterado.getTipoConta(), result.getTipoConta());
        assertEquals(clienteAlterado.getAgencia(), result.getAgencia());
        assertEquals(clienteAlterado.getConta(), result.getConta());
    }

    @Test
    public void alteraDadosCliente_id_Inexistente_lancaExecao(){
        long id = 1L;
        when(chavePixServiceRepository.findClienteById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> contaService.alteraDadosConta(id, ALTERACAO_NOME_DADOS_CLIENTE_ALTERADO))
                .isInstanceOf(NaoEncontradoException.class)
                .hasMessage("Cliente não encontrado");
    }



    @ParameterizedTest
    @MethodSource("provideContasInvalidas")
    public void criar_chavesPix_com_chaves_invalidas_retorna_excecao(Cliente cliente) {
        when(chavePixServiceRepository.findClienteById(1L)).thenReturn(Optional.of(ALTERACAO_NOME_DADOS_CLIENTE_VALIDO));
        assertThatThrownBy(() -> contaService.alteraDadosConta(1L, cliente)).isInstanceOf(ValidationException.class);
    }

    private static Stream<Arguments> provideContasInvalidas() {
        return Stream.of(
                Arguments.of(ALTERACAO_NOME_DADOS_CLIENTE_VALIDO)
        );
    }


    private static Stream<Arguments> fornecerClientesParaTeste() {
        long id = 2L;

        Cliente clienteExistente1 = new Cliente(id, "Giovana", "Santos", null, 1234, 12345678, "corrente");
        Cliente clienteAlterado1 = new Cliente(id, "Giovanna", "Santos", null, 1234, 12345678, "corrente");

        Cliente clienteExistente2 = new Cliente(id, "Giovana", "Santos", null, 1234, 12345678, "corrente");
        Cliente clienteAlterado2 = new Cliente(id, "Giovana", "Santos", null, 1234, 12345678, "poupanca");

        Cliente clienteExistente3 = new Cliente(id, "Giovana", "Santos", null, 1234, 12345678, "corrente");
        Cliente clienteAlterado3 = new Cliente(id, "Giovana", "Santos", null, 4321, 12345678, "corrente");

        Cliente clienteExistente4 = new Cliente(id, "Giovana", "Santos", null, 1234, 12345678, "corrente");
        Cliente clienteAlterado4 = new Cliente(id, "Giovana", "Santos", null, 1234, 87654321,"corrente" );

        return Stream.of(
                Arguments.of(clienteExistente1, clienteAlterado1, true),
                Arguments.of(clienteExistente2, clienteAlterado2, true),
                Arguments.of(clienteExistente3, clienteAlterado3, false),
                Arguments.of(clienteExistente4, clienteAlterado4, false)
        );


    }

    @ParameterizedTest
    @MethodSource("fornecerClientesParaTeste")
    public void alteraDadosCliente_validaAlteracao(Cliente clienteExistente, Cliente clienteAlterado, boolean agenciaContaIgual) {
        long id = clienteExistente.getId();

        when(chavePixServiceRepository.findClienteById(id)).thenReturn(Optional.of(clienteExistente));
        doNothing().when(chavePixServiceRepository).alteraDadosCliente(clienteAlterado, agenciaContaIgual);

        var result = contaService.alteraDadosConta(id, clienteAlterado);

        assertNotNull(result);
        assertEquals(clienteAlterado.getNome(), result.getNome());
        assertEquals(clienteExistente.getSobrenome(), result.getSobrenome());
        assertEquals(clienteAlterado.getTipoConta(), result.getTipoConta());
        assertEquals(clienteAlterado.getAgencia(), result.getAgencia());
        assertEquals(clienteAlterado.getConta(), result.getConta());
    }

}