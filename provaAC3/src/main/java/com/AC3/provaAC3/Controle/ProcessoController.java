package com.AC3.provaAC3.Controle;

import com.AC3.provaAC3.Dominio.*;
import com.AC3.provaAC3.Modelos.SkateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    SkateRepository repository;

    FilaObj<Processo> filaP = new FilaObj<>(100);
    PilhaObj<Processo> processosExecutados = new PilhaObj<>(100);
    List<Processo> protocolosExecutados = new ArrayList<Processo>();

    @PostMapping("/")
    public ResponseEntity postProcesso(@RequestBody @Valid Skate skate) {
        Optional<Skate> skt = repository.findById(skate.getId());
        if (skt.isPresent()) {
            return ResponseEntity.status(400).body("Skate já existente!");
        } else {
            Processo processo = new Processo();
            Random rand = new Random();
            String protocolo = Integer.toString(rand.nextInt(10000000));
            String verbo = "post";

            processo.setProtocolo(protocolo);
            processo.setVerbo(verbo);
            processo.setCorpo(skate);

            filaP.insert(processo);
            return ResponseEntity.status(201).body(protocolo);
        }

    }

    @GetMapping("/")
    public ResponseEntity getSkate() {
        return ResponseEntity.status(200).body(
                repository.findAll().stream().map(RespostaSkate::new).collect(Collectors.toList())
        );
    }

    @GetMapping("/executar")
    public ResponseEntity execProcessos() {
        while (!filaP.isEmpty()) {
            //Tiro da fila
            Processo pExec = filaP.poll();

            //Executo
            repository.save(pExec.getCorpo());
            pExec.setStatus(201);

            //Coloco na Pilha
            processosExecutados.push(pExec);
            protocolosExecutados.add(pExec);
            break;
        }
        return ResponseEntity.status(200).body("Processos em fila executados!");
    }


    @GetMapping("/fila")
    public ResponseEntity getProcessos() {
        return ResponseEntity.status(200).body(filaP.toString());
    }

    @GetMapping("/consultar/{protocolo}")
    public ResponseEntity consultarProcesso(@PathVariable String protocolo) {
        if (!protocolosExecutados.isEmpty()) {
            for (int i = 0; i < protocolosExecutados.size(); i++) {

                Processo processo = protocolosExecutados.get(i);
                if (processo.getProtocolo().equals(protocolo)) {
                    protocolosExecutados.remove(i);
                    return ResponseEntity.status(200).body
                            ("Processo já executado! " + processo.toString());
                }
            }
        }

        return ResponseEntity.status(200).body("Processo não executado!");
    }

    @GetMapping("/desfazer")
    public ResponseEntity desfazer() {
        if (processosExecutados.isEmpty()){
            return ResponseEntity.status(404).body("Elemento para ser desfeito não existente!");
        }

        Processo processo = processosExecutados.pop();
        repository.delete(processo.getCorpo());
        return ResponseEntity.status(200).body("Inserção desfeita: " + processo.getCorpo().toString());
    }

    @GetMapping("/arquivo")
    public ResponseEntity baixarArquivo() {
        List<Skate> lista = repository.findAll();

        if(lista.isEmpty()){
            return ResponseEntity.status(404).body("Nenhum skate encontrado!");
        }

        String header = "";
        String corpo = "";
        String trailer = "";

        Date dataDeHoje = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        header += "00SKATE";
        header += formatter.format(dataDeHoje);
        header += "01";

        Integer contador;
        for (contador = 0; contador < lista.size(); contador++) {
            Skate skate = lista.get(contador);
            corpo = "02";
            corpo += String.format("%-100s", skate.getNome());
            corpo += String.format("%3.2f", skate.getTamanho());
            corpo += String.format("%3.2f", skate.getConservacaoAtual());
            corpo += String.format("%-1d", (skate.getQuebrado() ? 1 : 0));
            corpo += String.format("%-1d", skate.getCategoria().getId());
        }

        trailer += "01";
        trailer += String.format("%010d", contador);

        BufferedWriter saida = null;
        try {
            saida = new BufferedWriter(new FileWriter("Skates.txt", true));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        try {
            saida.append(header + "\n" + corpo + "\n" + trailer);
            saida.close();

        } catch (IOException e) {
            System.err.printf("Erro ao gravar arquivo: %s.\n", e.getMessage());
        }

        return ResponseEntity.status(200).body("Arquivo criado no diretorio de execução.");
    }

    @PostMapping("/arquivo")
    public ResponseEntity subirArquivo(@RequestParam MultipartFile arq) throws IOException {

        if(arq == null){
            return ResponseEntity.status(400).body("Arquivo inválido!");
        }

        BufferedReader entrada = null;
        String registro;
        String tipoRegistro;
        int contRegistro=0;

        // Abre o arquivo
        try {
            entrada = new BufferedReader(new FileReader(arq.getBytes().toString()));
        } catch (IOException e) {
            return ResponseEntity.status(400).body("Erro na abertura do arquivo: %s.\n"+e.getMessage());
        }

        // Lê os registros do arquivo
        try {
            // Lê um registro
            registro = entrada.readLine();

            while (registro != null) {
                // Obtém o tipo do registro
                tipoRegistro = registro.substring(0, 2); // obtém os 2 primeiros caracteres do registro

                if (tipoRegistro.equals("00")) {
                    System.out.println("Header");
                    System.out.println("Tipo de arquivo: " + registro.substring(2, 7));
                    System.out.println("Data/hora de geração do arquivo: " + registro.substring(7,26));
                    System.out.println("Versão do layout: " + registro.substring(26,28));
                }
                else if (tipoRegistro.equals("01")) {
                    System.out.println("\nTrailer");
                    int qtdRegistro = Integer.parseInt(registro.substring(2,12));
                    if (qtdRegistro == contRegistro) {
                        System.out.println("Quantidade de registros gravados compatível com quantidade lida");
                    }
                    else {
                        System.out.println("Quantidade de registros gravados não confere com quantidade lida");
                    }
                }
                else if (tipoRegistro.equals("02")) {
                    String nome = registro.substring(2,102);
                    Double tamanho = Double.valueOf(registro.substring(102, 105));
                    Double conservacao = Double.valueOf(registro.substring(105, 108));
                    Boolean quebrado = Boolean.valueOf(registro.substring(108));
                    Integer categoria = Integer.valueOf(registro.substring(109));
                    Skate skt = new Skate
                            (null, nome, tamanho, conservacao, quebrado, new CategoriaSkate(categoria, ""));
                    repository.save(skt);
                    contRegistro++;
                }
                else {
                    return ResponseEntity.status(400).body("Tipo de registro inválido");
                }

                // lê o próximo registro
                registro = entrada.readLine();
            }

            // Fecha o arquivo
            entrada.close();
        } catch (IOException e) {
            return ResponseEntity.status(400).body("Erro ao ler arquivo: %s.\n"+ e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }

}
