package com.vitor.criar_chave_pix.adapter.input.web;


import com.vitor.criar_chave_pix.adapter.exceptions.ValidationException;
import com.vitor.criar_chave_pix.adapter.input.web.request.AlteraClienteRequest;
import com.vitor.criar_chave_pix.adapter.input.web.request.CriaChaveRequest;
import com.vitor.criar_chave_pix.adapter.input.web.response.AlteraClienteResponse;
import com.vitor.criar_chave_pix.adapter.input.web.response.ChavePixDesativadaResponse;
import com.vitor.criar_chave_pix.adapter.input.web.response.ConsultaChavePixResponse;
import com.vitor.criar_chave_pix.adapter.input.web.response.CriaChaveResponse;
import com.vitor.criar_chave_pix.adapter.input.web.swagger.ICriaChave;
import com.vitor.criar_chave_pix.application.domain.ClienteChavePix;
import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CriaChavePixController implements ICriaChave {

    private final ChaveServicePort chaveServicePort;
    private final ContaServicePort contaServicePort;

    public CriaChavePixController(ChaveServicePort chaveServicePort, ContaServicePort contaServicePort) {
        this.chaveServicePort = chaveServicePort;
        this.contaServicePort = contaServicePort;
    }

    @Override
    public ResponseEntity<CriaChaveResponse> criaChavePix(@RequestBody @Valid CriaChaveRequest chaveRequest) {
        var chavePixClienteCreated = chaveServicePort.criaChavePix(chaveRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new CriaChaveResponse(chavePixClienteCreated));
    }

    @Override
    public ResponseEntity<AlteraClienteResponse> alteraDadosConta(@PathVariable Long id, @RequestBody @Valid AlteraClienteRequest alteraClienteRequest) {
        var clienteAlterado = contaServicePort.alteraDadosConta(id, alteraClienteRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(AlteraClienteResponse.fromDomain(clienteAlterado));
    }

    @Override
    public ResponseEntity<ConsultaChavePixResponse> consultaChavePixPorId(@PathVariable UUID id,
                                                                          @RequestParam(required = false) String tipoChave,
                                                                          @RequestParam(required = false) Integer agencia,
                                      @RequestParam(required = false) Integer conta,
                                      @RequestParam(required = false) String nomeCorrentista,
                                      @RequestParam(required = false) String sobrenomeCorrentista,
                                      @RequestParam(required = false) String dataInclusao,
                                      @RequestParam(required = false) String dataInativacao
    ) {

        if (tipoChave != null || agencia != null || conta != null || nomeCorrentista != null || sobrenomeCorrentista != null || dataInclusao != null || dataInativacao != null) {
            throw new ValidationException("Parâmetros de busca não podem ser informados quando possui uma chave PIX informada.");
        }

        Optional<ClienteChavePix> chavePix = chaveServicePort.consultaChave(id);

        return chavePix.map(chave -> ResponseEntity.ok(ConsultaChavePixResponse.fromDomain(chave)))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }


    @Override
    public ResponseEntity<List<ConsultaChavePixResponse>> listaChavePixPorId(@RequestParam(required = false) String tipoChave,
                                                                             @RequestParam(required = false) Integer agencia,
                                                                             @RequestParam(required = false) Integer conta,
                                                                             @RequestParam(required = false) String nomeCorrentista,
                                                                             @RequestParam(required = false) String sobrenomeCorrentista,
                                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInclusao,
                                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInativacao) {
        List<ClienteChavePix> chavesPix = chaveServicePort.buscarChavesPixComFiltros(
                tipoChave, agencia, conta, nomeCorrentista, sobrenomeCorrentista,dataInclusao, dataInativacao);

        if (chavesPix.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        List<ConsultaChavePixResponse> response = chavesPix.stream()
                .map(ConsultaChavePixResponse::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ChavePixDesativadaResponse> desativaChavePix(@PathVariable UUID id) {
        var chaveDesativada = chaveServicePort.desativaChave(id);
        return ResponseEntity.status(HttpStatus.OK).body(ChavePixDesativadaResponse.fromDomain(chaveDesativada));
    }

}
