package com.AC3.provaAC3.Dominio;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Skate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private Double tamanho;

    @NotNull
    @Positive
    private Double conservacaoAtual = 0.0;

    @NotNull
    private Boolean quebrado = false;

    @ManyToOne
    @NotNull
    private CategoriaSkate categoria;

    @Override
    public String toString() {
        return "Skate{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tamanho=" + tamanho +
                ", conservacaoAtual=" + conservacaoAtual +
                ", quebrado=" + quebrado +
                '}';
    }

    public Skate(){}

    public Skate(Integer id, @NotNull @NotBlank String nome, @NotNull @Positive Double tamanho, @NotNull @Positive Double conservacaoAtual, @NotNull Boolean quebrado, @NotNull CategoriaSkate categoria) {
        this.id = id;
        this.nome = nome;
        this.tamanho = tamanho;
        this.conservacaoAtual = conservacaoAtual;
        this.quebrado = quebrado;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getTamanho() {
        return tamanho;
    }
    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }
    public Double getConservacaoAtual() {
        return conservacaoAtual;
    }
    public void setConservacaoAtual(Double conservacaoAtual) {
        this.conservacaoAtual = conservacaoAtual;
    }
    public Boolean getQuebrado() {
        return quebrado;
    }
    public void setQuebrado(Boolean quebrado) {
        this.quebrado = quebrado;
    }
    public CategoriaSkate getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaSkate categoria) {
        this.categoria = categoria;
    }
}
