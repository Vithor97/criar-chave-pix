package com.vitor.criar_chave_pix.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.adapter.input.web.CriaChavePixController;
import com.vitor.criar_chave_pix.adapter.input.web.request.AlteraClienteRequest;
import com.vitor.criar_chave_pix.adapter.input.web.request.CriaChaveRequest;
import com.vitor.criar_chave_pix.application.domain.Cliente;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        AlteraClienteRequest request = new AlteraClienteRequest("Vitor", "Sabtos", "corrente", 1234, 6789);

        when(contaServicePort.alteraDadosConta(eq(id), any(Cliente.class)))
                .thenThrow(new ValidationException("Cliente não encontrado"));

        mockMvc.perform(
                        put("/api/alterarDados/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnprocessableEntity());
                //.andExpect(jsonPath("$.erro").value("Cliente não encontrado"));
    }

}