package com.example.continuarda1;

public class Bicicleta implements Manobra{
    private String nome;
    private Integer aro;
    private String marca;

    @Override
    public String fazerManobra() {
        return "A manobra da bicicleta é muito perigosa e " +
                "desgasta peças especificas!";
    }
}
