package com.AC3.provaAC3.Controle;

import com.AC3.provaAC3.Dominio.CategoriaSkate;
import com.AC3.provaAC3.Dominio.RespostaSkate;
import com.AC3.provaAC3.Dominio.Skate;
import com.AC3.provaAC3.Modelos.CategoriaSkateRepository;
import com.AC3.provaAC3.Modelos.SkateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoriaSkateControllerTest {

    @Autowired
    CategoriaSkateController controller;

    @MockBean
    CategoriaSkateRepository repository;

    @Test
    void getCategoriasVazio() {
        ResponseEntity<List<RespostaSkate>> resposta = controller.getCategorias();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(0, resposta.getBody().size());
    }

    @Test
    void getCategoriasPreenchido() {
        List<CategoriaSkate> categorias = Arrays.asList(
                new CategoriaSkate(1, "categ 1"),
                new CategoriaSkate(2, "categ 2"),
                new CategoriaSkate(3, "categ 3"));
        Mockito.when(repository.findAll()).thenReturn(categorias);

        ResponseEntity<List<RespostaSkate>> resposta = controller.getCategorias();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(3, resposta.getBody().size());
    }

    @Test
    void postNomeErrado() {
        CategoriaSkate nCateg = new CategoriaSkate(1, null);

        ResponseEntity resposta = controller.postCategorias(nCateg);

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Nome inválido!", resposta.getBody());
    }

    @Test
    void postCorreto() {
        CategoriaSkate nCateg = new CategoriaSkate(1, "Nome categoria");

        ResponseEntity resposta = controller.postCategorias(nCateg);

        assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    void putCorreto() {
        CategoriaSkate categ = new CategoriaSkate(1,"categ antiga");

        Mockito.when(repository.findById(1)).thenReturn(java.util.Optional.of(categ));

        CategoriaSkate nCateg = new CategoriaSkate(1,"categ nova");

        ResponseEntity<List<RespostaSkate>> resposta = controller.putCategorias(nCateg);
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(nCateg, resposta.getBody());
    }

    @Test
    void putIdErrado() {
        CategoriaSkate nCateg = new CategoriaSkate(null,"categ nova");

        ResponseEntity<List<RespostaSkate>> resposta = controller.putCategorias(nCateg);
        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("ID inválido!", resposta.getBody());
    }
}
