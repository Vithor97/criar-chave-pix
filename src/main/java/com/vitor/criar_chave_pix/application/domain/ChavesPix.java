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


    public String getValorChave() {
        return valorChave;
    }


    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }


    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }


    public boolean isChaveAtivo() {
        return chaveAtivo;
    }


    public static ChavesPixBuilder builder() {
        return new ChavesPixBuilder();
    }

    public static class ChavesPixBuilder {
        private UUID uuidChave;
        private String tipoChave;
        private String valorChave;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataInativacao;
        private boolean chaveAtivo;

        public ChavesPixBuilder uuidChave(UUID uuidChave) {
            this.uuidChave = uuidChave;
            return this;
        }

        public ChavesPixBuilder tipoChave(String tipoChave) {
            this.tipoChave = tipoChave;
            return this;
        }

        public ChavesPixBuilder valorChave(String valorChave) {
            this.valorChave = valorChave;
            return this;
        }

        public ChavesPixBuilder dataCriacao(LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public ChavesPixBuilder dataInativacao(LocalDateTime dataInativacao) {
            this.dataInativacao = dataInativacao;
            return this;
        }

        public ChavesPixBuilder chaveAtivo(boolean chaveAtivo) {
            this.chaveAtivo = chaveAtivo;
            return this;
        }

        public ChavesPix build() {
            return new ChavesPix(uuidChave, tipoChave, valorChave, dataCriacao, dataInativacao, chaveAtivo);
        }
    }
}
