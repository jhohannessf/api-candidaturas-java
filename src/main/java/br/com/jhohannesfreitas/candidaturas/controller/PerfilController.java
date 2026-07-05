package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.dto.PerfilRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.PerfilResponseDTO;
import br.com.jhohannesfreitas.candidaturas.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @PostMapping
    public ResponseEntity<PerfilResponseDTO> cadastrarPerfil(@RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.cadastrarPerfil(dto));
    }

}
