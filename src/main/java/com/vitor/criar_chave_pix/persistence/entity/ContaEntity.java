package com.vitor.criar_chave_pix.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TB_CONTA", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"agencia", "conta"})
})
public class ContaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_conta")
    private String tipoConta;

    @Column(name = "agencia", length = 4, nullable = false)
    private Integer agencia;

    @Column(name = "conta", length = 8, nullable = false)
    private Integer conta;

    @Column(name = "nome_correntista", length = 30, nullable = false)
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista", length = 45)
    private String sobrenomeCorrentista;

    @Column(name = "tipo_pessoa")
    private String tipoPessoa;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChavesEntity> chavesPixList;
    public ContaEntity() {
    }

    public ContaEntity(Long id, String tipoConta, Integer agencia, Integer conta,
                       String nomeCorrentista, String sobrenomeCorrentista, String tipoPessoa,
                       List<ChavesEntity> chavesPixList) {
        this.id = id;
        this.tipoConta = tipoConta;
        this.agencia = agencia;
        this.conta = conta;
        this.nomeCorrentista = nomeCorrentista;
        this.sobrenomeCorrentista = sobrenomeCorrentista;
        this.tipoPessoa = tipoPessoa;
        this.chavesPixList = chavesPixList;
    }

    public ContaEntity(Long id, String tipoConta, Integer agencia, Integer conta,
                       String nomeCorrentista, String sobrenomeCorrentista, String tipoPessoa
                       ) {
        this.id = id;
        this.tipoConta = tipoConta;
        this.agencia = agencia;
        this.conta = conta;
        this.nomeCorrentista = nomeCorrentista;
        this.sobrenomeCorrentista = sobrenomeCorrentista;
        this.tipoPessoa = tipoPessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public List<ChavesEntity> getChavesPixList() {
        return chavesPixList;
    }

    public void setChavesPixList(List<ChavesEntity> chavesPixList) {
        this.chavesPixList = chavesPixList;
    }
}