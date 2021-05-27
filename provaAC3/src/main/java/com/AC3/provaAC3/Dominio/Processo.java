package com.AC3.provaAC3.Dominio;

public class Processo {
    private String protocolo;
    private String verbo;
    private Skate corpo;
    private Integer status = 000;

    @Override
    public String toString() {
        return "Processo{" +
                "protocolo='" + protocolo + '\'' +
                ", verbo='" + verbo + '\'' +
                ", corpo=" + corpo +
                ", status=" + status +
                '}';
    }

    public String getProtocolo() {
        return protocolo;
    }
    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }
    public String getVerbo() {
        return verbo;
    }
    public void setVerbo(String verbo) {
        this.verbo = verbo;
    }
    public Skate getCorpo() {
        return corpo;
    }
    public void setCorpo(Skate corpo) {
        this.corpo = corpo;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
