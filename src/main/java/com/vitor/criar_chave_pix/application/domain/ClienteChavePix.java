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


    public Integer getAgencia() {
        return agencia;
    }


    public Integer getConta() {
        return conta;
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


    public String getSobrenomeCorrentista() {
        return sobrenomeCorrentista;
    }

    public ChavesPix getChavesPix() {
        return chavesPix;
    }


    public List<ChavesPix> getChavesPixList() {
        return chavesPixList;
    }

    public void setChavesPixList(List<ChavesPix> chavesPixList) {
        this.chavesPixList = chavesPixList;
    }

    public static ClienteChavePixBuilder builder() {
        return new ClienteChavePixBuilder();
    }

    public static class ClienteChavePixBuilder {
        private Long idConta;
        private String tipoConta;
        private Integer agencia;
        private Integer conta;
        private String tipoPessoa;
        private String nomeCorrentista;
        private String sobrenomeCorrentista;
        private ChavesPix chavesPix;
        private List<ChavesPix> chavesPixList;

        public ClienteChavePixBuilder idConta(Long idConta) {
            this.idConta = idConta;
            return this;
        }

        public ClienteChavePixBuilder tipoConta(String tipoConta) {
            this.tipoConta = tipoConta;
            return this;
        }

        public ClienteChavePixBuilder agencia(Integer agencia) {
            this.agencia = agencia;
            return this;
        }

        public ClienteChavePixBuilder conta(Integer conta) {
            this.conta = conta;
            return this;
        }

        public ClienteChavePixBuilder tipoPessoa(String tipoPessoa) {
            this.tipoPessoa = tipoPessoa;
            return this;
        }

        public ClienteChavePixBuilder nomeCorrentista(String nomeCorrentista) {
            this.nomeCorrentista = nomeCorrentista;
            return this;
        }

        public ClienteChavePixBuilder sobrenomeCorrentista(String sobrenomeCorrentista) {
            this.sobrenomeCorrentista = sobrenomeCorrentista;
            return this;
        }

        public ClienteChavePixBuilder chavesPix(ChavesPix chavesPix) {
            this.chavesPix = chavesPix;
            return this;
        }

        public ClienteChavePixBuilder chavesPixList(List<ChavesPix> chavesPixList) {
            this.chavesPixList = chavesPixList;
            return this;
        }

        public ClienteChavePix build() {
            return new ClienteChavePix(idConta, tipoConta, agencia, conta, tipoPessoa, nomeCorrentista, sobrenomeCorrentista, chavesPix, chavesPixList);
        }
    }
}
