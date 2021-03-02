package com.example.continuarda1;

public class Street extends Skate implements Manobra{
    public Street(String nome, Double tamanho) {
        super(nome, tamanho);
    }

    @Override
    public String fazerManobra() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(4.0);
            return "Manobra de street desgasta muito o Skate!";
        }
    }

    @Override
    public Double getTempoRestante() {
        if(isQuebrado()){
            return 0.0;
        }
        else{
            //O street é o menos durável dos skates!
            return (100 - getConservacaoAtual()) * 0.3;
        }
    }

    @Override
    public String remar() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(0.02);
            return "Remada no street é fácil e desgasta médio.";
        }
    }

    @Override
    public String parar() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(0.02);
            return "Parar um street envolve colocar o pé aos poucos no chão," +
                    " dando um desgaste médio";
        }
    }
}
