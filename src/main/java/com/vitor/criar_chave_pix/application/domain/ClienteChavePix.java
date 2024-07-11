package com.vitor.criar_chave_pix.application.domain;

import java.util.ArrayList;
import java.util.List;

public class ClienteChavePix {

    private Long idConta;
    private String tipoConta;

    private Integer agencia;

    private Integer conta;

    private String tipoPessoa;

    private String nomeCorrentista;

    private String sobrenomeCorrentista;

    private ChavesPix chavesPix;

    private List<ChavesPix> chavesPixList;

    public ClienteChavePix(Long idConta,
                           String tipoConta,
                           Integer agencia,
                           Integer conta,
                           String tipoPessoa,
                           String nomeCorrentista,
                           String sobrenomeCorrentista,
                           ChavesPix chavesPix,
                           List<ChavesPix> chavesPixList) {
        this.idConta = idConta;
        this.tipoConta = tipoConta;
        this.agencia = agencia;
        this.conta = conta;
        this.tipoPessoa = tipoPessoa;
        this.nomeCorrentista = nomeCorrentista;
        this.sobrenomeCorrentista = sobrenomeCorrentista;
        this.chavesPix = chavesPix;
        this.chavesPixList = (chavesPixList != null) ? new ArrayList<>(chavesPixList) : new ArrayList<>();;
    }

    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Integer getConta() {
        return conta;
    }

    public void setConta(Integer conta) {
        this.conta = conta;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNomeCorrentista() {
        return nomeCorrentista;
    }

    public void setNomeCorrentista(String nomeCorrentista) {
        this.nomeCorrentista = nomeCorrentista;
    }

    public String getSobrenomeCorrentista() {
        return sobrenomeCorrentista;
    }

    public void setSobrenomeCorrentista(String sobrenomeCorrentista) {
        this.sobrenomeCorrentista = sobrenomeCorrentista;
    }

    public ChavesPix getChavesPix() {
        return chavesPix;
    }

    public void setChavesPix(ChavesPix chavesPix) {
        this.chavesPix = chavesPix;
    }

    public List<ChavesPix> getChavesPixList() {
        return chavesPixList;
    }

    public void setChavesPixList(List<ChavesPix> chavesPixList) {
        this.chavesPixList = chavesPixList;
    }
}
