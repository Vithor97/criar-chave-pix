package com.vitor.criar_chave_pix.application.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChavesPix {

    private UUID uuidChave;
    private String tipoChave;
    private String valorChave;
    private LocalDateTime dataCriacao;

    private LocalDateTime dataInativacao;

    private boolean chaveAtivo;

    public ChavesPix() {
    }

    public ChavesPix(UUID uuidChave,
                     String tipoChave,
                     String valorChave,
                     LocalDateTime dataCriacao,
                     LocalDateTime dataInativacao,
                     boolean chaveAtivo) {
        this.uuidChave = uuidChave;
        this.tipoChave = tipoChave;
        this.valorChave = valorChave;
        this.dataCriacao = dataCriacao;
        this.dataInativacao = dataInativacao;
        this.chaveAtivo = chaveAtivo;
    }

    public UUID getUuidChave() {
        return uuidChave;
    }

    public void setUuidChave(UUID uuidChave) {
        this.uuidChave = uuidChave;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public boolean isChaveAtivo() {
        return chaveAtivo;
    }

    public void setChaveAtivo(boolean chaveAtivo) {
        this.chaveAtivo = chaveAtivo;
    }

    @Override
    public String toString() {
        return "ChavesPix{" +
                "uuidChave=" + uuidChave +
                ", tipoChave='" + tipoChave + '\'' +
                ", valorChave='" + valorChave + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
