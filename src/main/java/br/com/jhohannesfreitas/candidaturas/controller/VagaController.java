package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.dto.VagaRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.service.VagaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vagas")
@RequiredArgsConstructor
@Tag(name = "Vagas", description = "Endpoints relacionados às vagas.")
public class VagaController {

    private final VagaService vagaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<VagaResponseDTO>> listar() {
        return ResponseEntity.ok(vagaService.listarVagas());
    }

    @GetMapping("/{empresa}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VagaResponseDTO> buscar(@PathVariable String empresa) {
        return ResponseEntity.ok(vagaService.buscaPorNomeEmpresa(empresa));
    }

    //Coloquei mapping porque configurei este endpoint para apenas ADMIN cadastrarem vagas
    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VagaResponseDTO> criar(@RequestBody VagaRequestDTO vaga) {
        return ResponseEntity.ok(vagaService.criarVaga(vaga));
    }

}