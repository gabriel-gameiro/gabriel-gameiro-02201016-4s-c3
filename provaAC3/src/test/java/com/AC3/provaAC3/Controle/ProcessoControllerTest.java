package com.AC3.provaAC3.Controle;

import com.AC3.provaAC3.Dominio.*;
import com.AC3.provaAC3.Modelos.CategoriaSkateRepository;
import com.AC3.provaAC3.Modelos.SkateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProcessoControllerTest {

    @Autowired
    ProcessoController controller;

    @MockBean
    SkateRepository repositorySkt;

    @MockBean
    CategoriaSkateRepository repositoryCateg;

    @Test
    void postProcessoDuplicado() {
        Skate skateExistente = new Skate
                (1, "skt1", 20.0, 100.0, false,
                        new CategoriaSkate(1, "categoria"));

        Mockito.when(repositorySkt.findById(1))
                .thenReturn(java.util.Optional.of(skateExistente));

        Skate skateNovo = new Skate
                (1, "skt2", 24.0, 100.0, false,
                        new CategoriaSkate(1, "categoria"));

        ResponseEntity resposta = controller.postProcesso(skateNovo);

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Skate já existente!", resposta.getBody());
    }

    @Test
    void postProcessoCorreto() {
        Skate skateNovo = new Skate
                (1, "skt1", 24.0, 100.0, false,
                        new CategoriaSkate(1, "categoria"));

        ResponseEntity resposta = controller.postProcesso(skateNovo);

        assertEquals(201, resposta.getStatusCodeValue());
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
        Mockito.when(repositorySkt.findAll()).thenReturn(skates);

        ResponseEntity<List<RespostaSkate>> resposta = controller.getSkate();
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(3, resposta.getBody().size());
    }

    @Test
    void execProcessos() {
    }

    @Test
    void getProcessosVazios() {
        ResponseEntity resposta = controller.getProcessos();
        FilaObj<Processo> filaP = new FilaObj<>(100);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(filaP.toString(), resposta.getBody());
    }

    @Test
    void getProcessosComElementos() {
        Skate skateNovo = new Skate
                (1, "skt1", 24.0, 100.0, false,
                        new CategoriaSkate(1, "categoria"));
        controller.postProcesso(skateNovo);

        ResponseEntity resposta = controller.getProcessos();
        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    void consultarProcessoNegativo() {
        ResponseEntity resposta = controller.consultarProcesso("111");

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("Processo não executado!", resposta.getBody());
    }

    @Test
    void consultarProcessoPositivo() {
        Processo processo = new Processo();
        processo.setProtocolo("111");
        controller.protocolosExecutados.add(processo);

        ResponseEntity resposta = controller.consultarProcesso("111");

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(("Processo já executado! " + processo.toString()), resposta.getBody());
    }

    @Test
    void desfazerNegado() {
        ResponseEntity resposta = controller.desfazer();

        assertEquals(404, resposta.getStatusCodeValue());
        assertEquals("Elemento para ser desfeito não existente!", resposta.getBody());
    }

    @Test
    void desfazerAceito() {
        Processo processo = new Processo();
        processo.setProtocolo("111");
        processo.setCorpo(new Skate());

        controller.processosExecutados.push(processo);

        ResponseEntity resposta = controller.desfazer();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(("Inserção desfeita: " + processo.getCorpo().toString()), resposta.getBody());
    }

    @Test
    void baixarArquivoNegado() {
        ResponseEntity resposta = controller.baixarArquivo();

        assertEquals(404, resposta.getStatusCodeValue());
        assertEquals("Nenhum skate encontrado!", resposta.getBody());
    }

    @Test
    void baixarArquivoAceito() {
        List<Skate> skates = Arrays.asList(
                new Skate(1, "skt1", 20.0, 100.0, false, new CategoriaSkate(1, "categoria")),
                new Skate(2, "skt2", 20.0, 100.0, false, new CategoriaSkate(1, "categoria")),
                new Skate(3, "skt3", 20.0, 100.0, false, new CategoriaSkate(1, "categoria")));
        Mockito.when(repositorySkt.findAll()).thenReturn(skates);

        ResponseEntity resposta = controller.baixarArquivo();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("Arquivo criado no diretorio de execução.", resposta.getBody());
    }

    @Test
    void subirArquivoInvalido() throws IOException {
        ResponseEntity resposta = controller.subirArquivo(null);

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Arquivo inválido!", resposta.getBody());
    }

    @Test
    void subirArquivoInvalido2() throws IOException {
        BufferedReader entrada = null;

        // Abre o arquivo
        try {
            entrada = new BufferedReader(new FileReader("Skates.txt"));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        MultipartFile arquivo = new MockMultipartFile
                ("Skates.txt","Skates.txt","text/plain",
                        entrada.toString().getBytes(StandardCharsets.UTF_8));

        ResponseEntity resposta = controller.subirArquivo(arquivo);

        assertEquals(400, resposta.getStatusCodeValue());
    }
}
