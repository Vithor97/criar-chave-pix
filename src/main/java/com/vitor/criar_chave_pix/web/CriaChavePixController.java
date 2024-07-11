package com.vitor.criar_chave_pix.web;


import com.vitor.criar_chave_pix.application.ports.ChaveServicePort;
import com.vitor.criar_chave_pix.web.request.CriaChaveRequest;
import com.vitor.criar_chave_pix.web.request.CriaChaveResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CriaChavePixController {

    private final ChaveServicePort chaveServicePort;

    public CriaChavePixController(ChaveServicePort chaveServicePort) {
        this.chaveServicePort = chaveServicePort;
    }

    @PostMapping("/inserir")
    public ResponseEntity<CriaChaveResponse> criaChavePix(@RequestBody @Valid CriaChaveRequest chaveRequest) {
        var chavePixClienteCreated = chaveServicePort.criaChavePix(chaveRequest.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CriaChaveResponse(chavePixClienteCreated));
    }


}
