package com.example.continuarda1;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/skates")
public class Controller {
    private List<Skate> listaSkates;

    @GetMapping("/{opcaoLista}")
    public List<Skate> iniciarLista(@PathVariable int opcaoLista) {
        if(listaSkates.isEmpty()) {
            if (opcaoLista == 1) {
                //Criação da lista de Skates do tipo Street
                listaSkates.add(new Street("Street Amarelo", 10.0));
                listaSkates.add(new Street("Street Cinza", 9.0));
                listaSkates.add(new Street("Street Azul", 11.0));
                return listaSkates;
            } else {
                if(opcaoLista == 2){
                    //Criação da lista de Skates do tipo Cruiser
                    listaSkates.add(new Cruiser("Cruiser Verde", 15.0));
                    listaSkates.add(new Cruiser("Cruiser Marrom", 19.0));
                    listaSkates.add(new Cruiser("Cruiser Vermelho", 16.0));
                    return listaSkates;
                } else {
                    if(opcaoLista == 3) {
                        //Criação da lista de Skates do tipo Cruiser
                        listaSkates.add(new Long("Long Branco", 20.0));
                        listaSkates.add(new Long("Long Laranja", 23.0));
                        listaSkates.add(new Long("Long Roxo", 21.0));
                        return listaSkates;
                    } else {
                        return null;
                    }
                }
            }
        }
        else{
            return listaSkates;
        }
    }

    @DeleteMapping("/{posicaoItem}")
    public List<Skate> getLista() {
        return listaSkates;
    }
}
