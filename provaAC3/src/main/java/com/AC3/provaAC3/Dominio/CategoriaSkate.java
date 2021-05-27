package com.AC3.provaAC3.Dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class CategoriaSkate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String nome;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Skate> skates;

    public CategoriaSkate(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaSkate() {
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
    public List<Skate> getSkates() {
        return skates;
    }
    public void setSkates(List<Skate> skates) {
        this.skates = skates;
    }
}
