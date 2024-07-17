package com.vitor.criar_chave_pix.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitor.criar_chave_pix.adapter.exceptions.NaoEncontradoException;
import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.adapter.input.web.CriaChavePixController;
import com.vitor.criar_chave_pix.adapter.input.web.request.AlteraClienteRequest;
import com.vitor.criar_chave_pix.adapter.input.web.request.CriaChaveRequest;
import com.vitor.criar_chave_pix.application.domain.ChavesPix;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.commons.consultacontantes.ConsultaContantes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static com.vitor.criar_chave_pix.commons.consultacontantes.ConsultaContantes.CRIACLIENTE_CHAVEPIX_INATIVADA_DEFAULT;
import static com.vitor.criar_chave_pix.commons.consultacontantes.ConsultaContantes.DEFAULT_UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CriaChavePixController.class)
class CriaChavePixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ChaveServicePort chaveServicePort;

    @MockBean
    private ContaServicePort contaServicePort;


    @Test
    public void criaChavePix_WithValidData_ReturnsCreatedWithUUID() throws Exception {
        CriaChaveRequest chaveRequest = new CriaChaveRequest("cpf", "12345678912", "corrente", 1234, 12345678, "João", "Silva");
        UUID chavePixId = UUID.randomUUID();

        when(chaveServicePort.criaChavePix(any(ClienteChavePix.class))).thenReturn(chavePixId);

        mockMvc.perform(
                        post("/api/inserir")
                                .content(objectMapper.writeValueAsString(chaveRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idChave").value(chavePixId.toString()));
    }

    @Test
    public void criar_chavePix_WithExisting_ChavePix() throws Exception {

        CriaChaveRequest chaveRequest = new CriaChaveRequest("cpf", "12345678912", "corrente", 1234, 12345678, "João", "Silva");

        when(chaveServicePort.criaChavePix(any(ClienteChavePix.class))).thenThrow(new ValidationException("Chave Pix já cadastrada."));


        mockMvc.perform(
                        post("/api/inserir")
                                .content(objectMapper.writeValueAsString(chaveRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity());
    }


    @ParameterizedTest
    @MethodSource("provideDadosCriacaoInvalido")
    public void criar_chavesPix_com_dadosRequest_invalidas_retorna_excecao(CriaChaveRequest request) throws Exception {

        mockMvc.perform(
                        post("/api/inserir")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity());
    }



    @Test
    public void alteraDadosConta_WithValidData_ReturnsOkAndValidResponse() throws Exception {
        Long id = 1L;
        AlteraClienteRequest request = new AlteraClienteRequest("Vitor", "Santos", "corrente", 1234, 6789);
        Cliente clienteMock = new Cliente(id, "Vitor", "Santos", "PF", 1234, 6789, "corrente");

        when(contaServicePort.alteraDadosConta(eq(id), any(Cliente.class))).thenReturn(clienteMock);

        mockMvc.perform(
                        put("/api/alterarDados/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Vitor"))
                .andExpect(jsonPath("$.sobrenome").value("Santos"))
                .andExpect(jsonPath("$.tipoConta").value("corrente"))
                .andExpect(jsonPath("$.agencia").value(1234))
                .andExpect(jsonPath("$.conta").value(6789));
    }

    @Test
    public void alteraDadosConta_ClienteNotFound_ThrowsValidationException() throws Exception {
        Long id = 1L;
        AlteraClienteRequest request = new AlteraClienteRequest("Vitor", "Santos", "corrente", 1234, 6789);

        when(contaServicePort.alteraDadosConta(eq(id), any(Cliente.class)))
                .thenThrow(new NaoEncontradoException("Cliente não encontrado"));

        mockMvc.perform(
                        put("/api/alterarDados/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void consultaChavePixPorId_ChaveEncontrada_ReturnsOk() throws Exception {
        UUID uuid = UUID.fromString("7bed4ffa-7ab1-4a92-a3ff-43564426414b");
        ChavesPix chavesPix = new ChavesPix(uuid,
                "chave_aleatoria",
                "chavealeatoriaDavi3",
                LocalDateTime.parse("14/07/2024 19:28:37", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                LocalDateTime.parse("14/07/2024 19:28:54", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), true);
        ClienteChavePix clienteChavePix = new ClienteChavePix(1L, "corrente", 1234, 11234441, "PF", "Davi", "Santos", chavesPix, Arrays.asList(chavesPix));

        when(chaveServicePort.consultaChave(uuid)).thenReturn(Optional.of(clienteChavePix));

        mockMvc.perform(get("/api/consultar/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("7bed4ffa-7ab1-4a92-a3ff-43564426414b"))
                .andExpect(jsonPath("$.tipoChave").value("chave_aleatoria"))
                .andExpect(jsonPath("$.valorChave").value("chavealeatoriaDavi3"))
                .andExpect(jsonPath("$.tipoConta").value("corrente"))
                .andExpect(jsonPath("$.agencia").value(1234))
                .andExpect(jsonPath("$.conta").value(11234441))
                .andExpect(jsonPath("$.nomeCorrentista").value("Davi"))
                .andExpect(jsonPath("$.sobrenomeCorrentista").value("Santos"))
                .andExpect(jsonPath("$.dataInclusao").value("14/07/2024 19:28:37"))
                .andExpect(jsonPath("$.dataInativacao").value("14/07/2024 19:28:54"));
    }

    @Test
    public void consultaChavePixPorId_ChaveNaoEncontrada_ReturnsNotFound() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(chaveServicePort.consultaChave(uuid)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/consultar/{id}", uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void consultaChavePixPorId_ComTipoChave_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("tipoChave", "EMAIL"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void consultaChavePixPorId_ComAgencia_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("agencia", "1234"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void consultaChavePixPorId_ComConta_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("conta", "567890"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void consultaChavePixPorId_ComNomeCorrentista_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("nomeCorrentista", "João"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void consultaChavePixPorId_ComSobrenomeCorrentista_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("sobrenomeCorrentista", "Silva"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void consultaChavePixPorId_ComDataInclusao_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("dataInclusao", "01/01/2020"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void consultaChavePixPorId_ComDataInativacao_ReturnsUnprocessableEntity() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(get("/api/consultar/{id}", uuid)
                        .param("dataInativacao", "01/01/2021"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void listaChavePixPorId_NoResults_ReturnsNotFound() throws Exception {
        when(chaveServicePort.buscarChavesPixComFiltros(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/consultar")
                        .param("tipoChave", "cpf"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void listaChavePixPorId_ResultsFound_ReturnsOkAndData() throws Exception {
        List<ClienteChavePix> listChavesPix = List.of(
                ConsultaContantes.CRIACLIENTE_CHAVEPIX_INATIVADA_DEFAULT,
                ConsultaContantes.CLIENTE_CHAVEPIX_1,
                ConsultaContantes.CLIENTE_CHAVEPIX_2,
                ConsultaContantes.CLIENTE_CHAVEPIX_3);

        when(chaveServicePort.buscarChavesPixComFiltros(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(listChavesPix);

        mockMvc.perform(get("/api/consultar")
                        .param("tipoChave", "CPF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(listChavesPix.size()));
    }



    @Test
    public void desativaChavePix_ReturnsOkAndDesativadaResponse() throws Exception {


        when(chaveServicePort.desativaChave(DEFAULT_UUID)).thenReturn(CRIACLIENTE_CHAVEPIX_INATIVADA_DEFAULT);

        mockMvc.perform(delete("/api/desativar/{id}", DEFAULT_UUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DEFAULT_UUID.toString()))
                .andExpect(jsonPath("$.tipoChave").value("chave_aleatoria"))
                .andExpect(jsonPath("$.valorChave").value("chavealeatoriaDavi3"))
                .andExpect(jsonPath("$.tipoConta").value("corrente"))
                .andExpect(jsonPath("$.agencia").value(1234))
                .andExpect(jsonPath("$.conta").value(11234441))
                .andExpect(jsonPath("$.nomeCorrentista").value("Davi"))
                .andExpect(jsonPath("$.sobrenomeCorrentista").value("Santos"))
                .andExpect(jsonPath("$.dataInclusao").value("14/07/2024 19:28:37"))
                .andExpect(jsonPath("$.dataInativacao").value("14/07/2024 19:28:54"));
    }


    @Test
    public void accessingInvalidUrl_ReturnsServerError() throws Exception {
        mockMvc.perform(get("/invalid-url"))
                .andExpect(status().isInternalServerError());
    }


    private static Stream<Arguments> provideDadosCriacaoInvalido() {
        return Stream.of(
                Arguments.of(new CriaChaveRequest("", "validValue", "corrente", 1234, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "", "corrente", 1234, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "invalidType", 1234, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", null, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 10000, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, null, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 100000000, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, null, "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, "", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, "NomeMuitoMuitoMuitoMuitoMuitoMuitoLongo", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, "NomeValido", "SobrenomeMuitoMuitoMuitoMuitoMuitoMuitoMuitoLongo")),
                Arguments.of(new CriaChaveRequest("", "validValue", "corrente", 1234, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("email", "", "corrente", 1234, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "invalidType", 1234, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "poupanca", null, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "poupanca", 10000, 567890, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 10000, null, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 100000000, "NomeValido", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, null, "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, "", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, "NomeMuitoMuitoMuitoMuitoMuitoMuitoLongo", "SobrenomeValido")),
                Arguments.of(new CriaChaveRequest("CPF", "validValue", "corrente", 1234, 567890, "NomeValido", "SobrenomeMuitoMuitoMuitoMuitoMuitoMuitoMuitoLongo"))
        );
    }

}