package com.vitor.criar_chave_pix.adapter.input.web.swagger;


import com.vitor.criar_chave_pix.adapter.input.web.request.AlteraClienteRequest;
import com.vitor.criar_chave_pix.adapter.input.web.request.CriaChaveRequest;
import com.vitor.criar_chave_pix.adapter.input.web.response.AlteraClienteResponse;
import com.vitor.criar_chave_pix.adapter.input.web.response.ChavePixDesativadaResponse;
import com.vitor.criar_chave_pix.adapter.input.web.response.ConsultaChavePixResponse;
import com.vitor.criar_chave_pix.adapter.input.web.response.CriaChaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "Gerenciamento de chaves pix", description = "API de gerenciamento de chaves pix")
public interface ICriaChave {



    @PostMapping("/inserir")
    @Operation(summary = "Cria uma nova chave pix para um cliente", description = "Cria uma nova chave pix para um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chave criada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CriaChaveResponse.class))}),

            @ApiResponse(responseCode = "422", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    }
    )
    ResponseEntity<CriaChaveResponse> criaChavePix(@RequestBody @Valid CriaChaveRequest chaveRequest);


    @PutMapping("/alterarDados/{id}")
    @Operation(summary = "Altera dados da conta", description = "Altera dados de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente alterado com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AlteraClienteResponse.class))}),
            @ApiResponse(responseCode = "422", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),

            @ApiResponse(responseCode = "404", description = "Cliente nao encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    }
    )
    public ResponseEntity<AlteraClienteResponse> alteraDadosConta(@PathVariable Long id, @RequestBody @Valid AlteraClienteRequest alteraClienteRequest);


    @GetMapping("/consultar/{id}")
    @Operation(summary = "Consulta uma chave pix", description = "Consulta uma chave pix")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave encontrada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConsultaChavePixResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Chave pix não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "422", description = "Critérios de busca inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ConsultaChavePixResponse> consultaChavePixPorId(@PathVariable UUID id,
                                                                          @RequestParam(required = false) String tipoChave,
                                                                          @RequestParam(required = false) Integer agencia,
                                                                          @RequestParam(required = false) Integer conta,
                                                                          @RequestParam(required = false) String nomeCorrentista,
                                                                          @RequestParam(required = false) String sobrenomeCorrentista,
                                                                          @RequestParam(required = false) String dataInclusao,
                                                                          @RequestParam(required = false) String dataInativacao
    );

    @GetMapping("/consultar")
    @Operation(summary = "Consulta uma chave pix", description = "Consulta uma chave pix")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave encontrada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ConsultaChavePixResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Chave pix não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "422", description = "Critérios de busca inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<List<ConsultaChavePixResponse>> listaChavePixPorId(@RequestParam(required = false) String tipoChave,
                                                                             @RequestParam(required = false) Integer agencia,
                                                                             @RequestParam(required = false) Integer conta,
                                                                             @RequestParam(required = false) String nomeCorrentista,
                                                                             @RequestParam(required = false) String sobrenomeCorrentista,
                                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInclusao,
                                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInativacao);

    @DeleteMapping("/desativar/{id}")
    @Operation(summary = "Desativa uma chave pix", description = "Desativa uma chave pix")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave desativada com sucesso",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ChavePixDesativadaResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Chave pix não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "422", description = "Chave já desativada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<ChavePixDesativadaResponse> desativaChavePix(@PathVariable UUID id);
}
