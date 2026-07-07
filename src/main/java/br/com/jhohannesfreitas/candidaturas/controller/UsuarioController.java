package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.dto.InscricaoRequest;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioResponse;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints relacionados aos usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Definindo quem pode ver as candidaturas a nível de método
    //@PreAuthorize("#usuarioId == authentication.principal.id or hasRole('ADMIN')")
    @PostMapping("/inscrever")
    public ResponseEntity<UsuarioResponse> inscreverUsuarioEmVaga(@Valid @RequestBody InscricaoRequest dto, Authentication authentication) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();
        UsuarioResponse response = usuarioService.inscreverUsuarioEmVaga(
                usuarioLogado.getEmail(),
                dto.vaga());
        return ResponseEntity.ok(response);
    }

}
