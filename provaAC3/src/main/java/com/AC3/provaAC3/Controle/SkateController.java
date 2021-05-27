package com.AC3.provaAC3.Controle;

import com.AC3.provaAC3.Dominio.CategoriaSkate;
import com.AC3.provaAC3.Dominio.RespostaSkate;
import com.AC3.provaAC3.Dominio.Skate;
import com.AC3.provaAC3.Modelos.CategoriaSkateRepository;
import com.AC3.provaAC3.Modelos.SkateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skate/")
public class SkateController {
    @Autowired
    SkateRepository repository;

    @Autowired
    CategoriaSkateRepository repositoryCateg;

    @PostMapping("/")
    public ResponseEntity postSkate(@RequestBody @Valid Skate novoSkate) {
        Optional<CategoriaSkate> categ = repositoryCateg.findById(novoSkate.getCategoria().getId());
        if (!categ.isPresent()) {
            return ResponseEntity.status(400).body("Categoria inválida!");
        } else {
            repository.save(novoSkate);
            return ResponseEntity.status(201).body("Novo skate criado!");
        }
    }

    @GetMapping("/")
    public ResponseEntity getSkate() {
        return ResponseEntity.status(200).body(
                repository.findAll().stream().map(RespostaSkate::new).collect(Collectors.toList())
        );
        // return ResponseEntity.status(200).body(repository.findAll());
    }

    @PutMapping("/")
    public ResponseEntity putSkate(@RequestBody @Valid Skate skate) {
        if (skate.getId() == null) {
            return ResponseEntity.status(400).body("ID inválido!");
        }
        if (repository.findById(skate.getId()).isPresent()) {
            repository.save(skate);
            return ResponseEntity.status(200).body(skate);
        } else {
            return ResponseEntity.status(404).body("Skate não encontrado!");
        }
    }
}
