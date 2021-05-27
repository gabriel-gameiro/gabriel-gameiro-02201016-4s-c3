package com.AC3.provaAC3.Dominio;

public class RespostaSkate {
    private Integer id;
    private String nome;
    private Double tamanho;
    private Double conservacaoAtual = 0.0;
    private Boolean quebrado = false;
    private String nomeCategoria;

    public RespostaSkate(Skate skate) {
        this.id = skate.getId();
        this.nome = skate.getNome();
        this.tamanho = skate.getTamanho();
        this.conservacaoAtual = skate.getConservacaoAtual();
        this.quebrado = skate.getQuebrado();
        this.nomeCategoria = skate.getCategoria().getNome();
    }

    public Integer getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public Double getTamanho() {
        return tamanho;
    }
    public Double getConservacaoAtual() {
        return conservacaoAtual;
    }
    public Boolean getQuebrado() {
        return quebrado;
    }
    public String getNomeCategoria() {
        return nomeCategoria;
    }
}
