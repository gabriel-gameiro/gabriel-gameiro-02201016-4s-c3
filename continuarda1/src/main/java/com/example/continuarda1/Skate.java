package com.example.continuarda1;

public abstract class Skate {
    private String nome;
    private Double tamanho;
    private Double conservacaoAtual = 0.0;
    private Boolean quebrado = false;

    public Skate(String nome, Double tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    protected void desgastar(Double qtdDesgaste) {
        this.conservacaoAtual += qtdDesgaste;
        if(conservacaoAtual >= 100) {
            quebrado = true;
        }
    }

    public abstract Double getTempoRestante();

    public abstract String remar();

    public abstract String parar();

    public String getNome() {
        return nome;
    }
    public Double getTamanho() {
        return tamanho;
    }
    public Double getConservacaoAtual() {
        return conservacaoAtual;
    }
    public Boolean isQuebrado() {
        return quebrado;
    }
}
