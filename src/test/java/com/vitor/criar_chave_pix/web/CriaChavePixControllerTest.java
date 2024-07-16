package com.vitor.criar_chave_pix.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.web.request.CriaChaveRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}