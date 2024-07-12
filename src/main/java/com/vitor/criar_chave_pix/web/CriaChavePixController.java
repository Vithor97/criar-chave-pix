package com.vitor.criar_chave_pix.web;


import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.application.ports.ContaServicePort;
import com.vitor.criar_chave_pix.web.request.AlteraClienteRequest;
import com.vitor.criar_chave_pix.web.request.CriaChaveRequest;
import com.vitor.criar_chave_pix.web.response.AlteraClienteResponse;
import com.vitor.criar_chave_pix.web.response.CriaChaveResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CriaChavePixController {

    private final ChaveServicePort chaveServicePort;
    private final ContaServicePort contaServicePort;

    public CriaChavePixController(ChaveServicePort chaveServicePort, ContaServicePort contaServicePort) {
        this.chaveServicePort = chaveServicePort;
        this.contaServicePort = contaServicePort;
    }

    @PostMapping("/inserir")
    public ResponseEntity<CriaChaveResponse> criaChavePix(@RequestBody @Valid CriaChaveRequest chaveRequest) {
        var chavePixClienteCreated = chaveServicePort.criaChavePix(chaveRequest.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CriaChaveResponse(chavePixClienteCreated));
    }

    @PutMapping("/alterarDados/{id}")
    public ResponseEntity<AlteraClienteResponse> alteraDadosConta(@PathVariable Long id, @RequestBody @Valid AlteraClienteRequest alteraClienteRequest) {
        var clienteAlterado = contaServicePort.alteraDadosConta(id, alteraClienteRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(AlteraClienteResponse.fromDomain(clienteAlterado));
    }


}
