package com.example.continuarda1;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/skates")
public class Controller {
    private List<Skate> listaSkates = new ArrayList<>();
    private Boolean iniciado = false;

    @GetMapping("/getLista")
    public List<Skate> getLista() {
        if(!iniciado) {
            //Criação da lista de Skates do tipo Street
            listaSkates.add(new Street("Street Amarelo", 10.0));
            listaSkates.add(new Street("Street Cinza", 9.0));
            listaSkates.add(new Street("Street Azul", 11.0));
            //Criação da lista de Skates do tipo Cruiser
            listaSkates.add(new Cruiser("Cruiser Verde", 15.0));
            listaSkates.add(new Cruiser("Cruiser Marrom", 19.0));
            listaSkates.add(new Cruiser("Cruiser Vermelho", 16.0));
            //Criação da lista de Skates do tipo Cruiser
            listaSkates.add(new Long("Long Branco", 20.0));
            listaSkates.add(new Long("Long Laranja", 23.0));
            listaSkates.add(new Long("Long Roxo", 21.0));

            iniciado = true;
        }
            return listaSkates;
    }

    @GetMapping("/getDuracaoStreet")
    public List<Double> getDuracaoStreet(){
        List<Double> retorno = new ArrayList<>();
        for (Skate skt : listaSkates){
            if(skt.getClass() == Street.class){
                retorno.add(skt.getTempoRestante());
            }
        }
        return retorno;
    }

    @GetMapping("/getDuracaoLong")
    public List<Double> getDuracaoLong(){
        List<Double> retorno = new ArrayList<>();
        for (Skate skt : listaSkates){
            if(skt.getClass() == Long.class){
                retorno.add(skt.getTempoRestante());
            }
        }
        return retorno;
    }

    @GetMapping("/getDuracaoCruiser")
    public List<Double> getDuracaoCruiser(){
        List<Double> retorno = new ArrayList<>();
        for (Skate skt : listaSkates){
            if(skt.getClass() == Cruiser.class){
                retorno.add(skt.getTempoRestante());
            }
        }
        return retorno;
    }

    @DeleteMapping("removerItem/{posicaoItem}")
    public String deletarItem(@PathVariable int posicaoItem) {
        if(posicaoItem < listaSkates.size()){
            listaSkates.remove(posicaoItem);
            return "Deletado com sucesso!";
        }
        else{
            return "Posição não encontrada!";
        }
    }
}
