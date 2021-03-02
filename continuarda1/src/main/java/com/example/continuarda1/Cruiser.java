package com.example.continuarda1;

public class Cruiser extends Skate{
    public Cruiser(String nome, Double tamanho) {
        super(nome, tamanho);
    }

    @Override
    public Double getTempoRestante() {
        if(isQuebrado()){
            return 0.0;
        }
        else{
            //O cruiser é o mais durável dos skates!
            return (100 - getConservacaoAtual()) * 0.8;
        }
    }

    @Override
    public String remar() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(0.01);
            return "Remar com um cruiser é fácil e desgasta pouco!";
        }
    }

    @Override
    public String parar() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(0.02);
            return "Parar com um cruiser normalmente é fácil" +
                    " e desgasta médio!";
        }
    }
}
