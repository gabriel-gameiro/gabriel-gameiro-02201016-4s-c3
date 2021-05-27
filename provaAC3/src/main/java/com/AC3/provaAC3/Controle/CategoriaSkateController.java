package com.AC3.provaAC3.Controle;

import com.AC3.provaAC3.Dominio.CategoriaSkate;
import com.AC3.provaAC3.Modelos.CategoriaSkateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaSkateController {
    @Autowired
    CategoriaSkateRepository repository;

    @GetMapping("/")
    public ResponseEntity getCategorias() {
        List<CategoriaSkate> categorias = repository.findAll();
        return ResponseEntity.status(200).body(categorias);
    }

    @PostMapping("/")
    public ResponseEntity postCategorias(@RequestBody @Valid CategoriaSkate novaCateg) {
        if (novaCateg.getNome() == null) {
            return ResponseEntity.status(400).body("Nome inválido!");
        } else {
            repository.save(novaCateg);
            return ResponseEntity.status(201).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity putCategorias(@RequestBody CategoriaSkate categoriaSkate) {
        if (categoriaSkate.getId() == null) {
            return ResponseEntity.status(400).body("ID inválido!");
        }

        if (repository.findById(categoriaSkate.getId()).isPresent()) {
            repository.save(categoriaSkate);
            return ResponseEntity.status(200).body(categoriaSkate);
        } else {
            return ResponseEntity.status(404).body("Skate enviado não cadastrado!");
        }
    }
}
