package com.vitor.criar_chave_pix.adapter.output.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_CHAVE_PIX", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"conta_id", "valor_chave"})
})
public class ChavesEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private ContaEntity conta;

    @Column(name = "tipo_chave")
    private String tipoChave;

    @Column(name = "valor_chave")
    private String valorChave;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataRegistro = LocalDateTime.now();

    @Column(name = "data_inativacao")
    private LocalDateTime dataInativacao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;


    public ChavesEntity() {
        this.dataRegistro = LocalDateTime.now();
    }

    public ChavesEntity(UUID id,
                        ContaEntity conta,
                        String tipoChave,
                        String valorChave,
                        LocalDateTime dataRegistro,
                        LocalDateTime dataInativacao,
                        boolean ativo) {
        this.id = id;
        this.conta = conta;
        this.tipoChave = tipoChave;
        this.valorChave = valorChave;
        this.dataRegistro = dataRegistro != null ? dataRegistro : LocalDateTime.now();
        this.dataInativacao = dataInativacao;
        this.ativo = ativo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ContaEntity getConta() {
        return conta;
    }

    public void setConta(ContaEntity conta) {
        this.conta = conta;
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getValorChave() {
        return valorChave;
    }

    public void setValorChave(String valorChave) {
        this.valorChave = valorChave;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
