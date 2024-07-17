package com.vitor.criar_chave_pix.application.domain;

public class Cliente {

    private Long id;

    private String nome;

    private String sobrenome;

    private String tipoPessoa;

    private Integer agencia;

    private Integer conta;

    private String tipoConta;


    public Cliente(String nome, String sobrenome, String tipoPessoa, Integer agencia, Integer conta, String tipoConta) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipoPessoa = tipoPessoa;
        this.agencia = agencia;
        this.conta = conta;
        this.tipoConta = tipoConta;
    }

    public Cliente(Long id, String nome, String sobrenome, String tipoPessoa, Integer agencia, Integer conta, String tipoConta) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipoPessoa = tipoPessoa;
        this.agencia = agencia;
        this.conta = conta;
        this.tipoConta = tipoConta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
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

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }


}
