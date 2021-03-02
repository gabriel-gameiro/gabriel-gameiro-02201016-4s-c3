package com.example.continuarda1;

public class Long extends Skate implements Manobra{
    public Long(String nome, Double tamanho) {
        super(nome, tamanho);
    }

    @Override
    public String fazerManobra() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(2.0);
            return "A manobra do long é elegante e desgasta pouco!";
        }
    }

    @Override
    public Double getTempoRestante() {
        if(isQuebrado()){
            return 0.0;
        }
        else{
            //O long é o segundo mais durável dos skates!
            return (100 - getConservacaoAtual()) * 0.5;
        }
    }

    @Override
    public String remar() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(0.03);
            return "A remada no long é a que mais desgasta!";
        }
    }

    @Override
    public String parar() {
        if(isQuebrado()) {
            return "Skate quebrado!";
        }
        else {
            desgastar(0.04);
            return "Parar um long às vezes envolve um slide," +
                    " que desgasta muito!";
        }
    }
}
