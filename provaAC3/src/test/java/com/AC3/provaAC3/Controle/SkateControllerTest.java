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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkateControllerTest {

    @Autowired
    SkateController controller;

    @MockBean
    SkateRepository repository;

    @MockBean
    CategoriaSkateRepository repositoryCateg;

    @Test
    void postSkateCategErrada() {
        Skate skate = new Skate
                (null, "skt", 20.0, 50.0, true,
                        new CategoriaSkate(10, "categoria"));

        ResponseEntity resposta = controller.postSkate(skate);

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Categoria inválida!", resposta.getBody());
    }

    @Test
    void postSkateCorreto() {
        Skate skate = new Skate
                (1, "skt1", 20.0, 100.0, false,
                        new CategoriaSkate(1, "categoria"));

        Mockito.when(repositoryCateg.findById(1))
                .thenReturn(java.util.Optional.of(new CategoriaSkate(1, "categoria")));

        ResponseEntity resposta = controller.postSkate(skate);
        assertEquals(201, resposta.getStatusCodeValue());
        assertEquals("Novo skate criado!", resposta.getBody());
    }

    @Test
    void getSkateVazio() {
        ResponseEntity<List<Skate>> resposta = controller.getSkate();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(0, resposta.getBody().size());
    }

    @Test
    void getSkatePreenchido() {
        List<Skate> skates = Arrays.asList(
                new Skate(1, "skt1", 20.0, 100.0, false, new CategoriaSkate(1, "categoria")),
                new Skate(2, "skt2", 20.0, 100.0, false, new CategoriaSkate(1, "categoria")),
                new Skate(3, "skt3", 20.0, 100.0, false, new CategoriaSkate(1, "categoria")));
        Mockito.when(repository.findAll()).thenReturn(skates);

        ResponseEntity<List<RespostaSkate>> resposta = controller.getSkate();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(3, resposta.getBody().size());
    }

    @Test
    void putSkateCorreto() {
        Skate skateRetorno = new Skate(1, "skt1", 20.0,
             100.0, false, new CategoriaSkate(1, "categoria"));

        Mockito.when(repository.findById(1)).thenReturn(java.util.Optional.of(skateRetorno));

        Skate nSkate = new Skate(1, "skate novo", 10.0, 100.0,
                false, new CategoriaSkate(1, "categoria"));


        ResponseEntity<List<RespostaSkate>> resposta = controller.putSkate(nSkate);
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(nSkate, resposta.getBody());
    }

    @Test
    void putSkateIdErrado() {
        Skate nSkate = new Skate(null,"skate novo", 10.0, 100.0,
                false, new CategoriaSkate(1, "categoria"));


        ResponseEntity<List<RespostaSkate>> resposta = controller.putSkate(nSkate);
        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("ID inválido!", resposta.getBody());
    }
}
