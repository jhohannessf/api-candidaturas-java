package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.service.CandidaturaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidaturas")
@RequiredArgsConstructor
@Tag(name = "Candidaturas", description = "Endpoints relacionados às candidaturas de usuárias às vagas.")
public class CandidaturaController {

    private final CandidaturaService candidaturaService;

    // Listar candidaturas do usuário logado
    @GetMapping
    public ResponseEntity<List<CandidaturaResponseDTO>> listarPorUsuario(Authentication authentication) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();
        return ResponseEntity.ok(
                candidaturaService.obterCandidaturasPorEmail(usuarioLogado.getEmail())
        );
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<VagaResponseDTO>> listarPorStatus(@PathVariable String status, Authentication authentication) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();
        return ResponseEntity.ok(candidaturaService.obterCandidaturasPorStatus(status, usuarioLogado.getEmail()));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CandidaturaResponseDTO> alterarStatus(@RequestBody CandidaturaRequestDTO candidaturaRequestDTO, Authentication authentication) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();
        return ResponseEntity.ok(candidaturaService.alterarStatusCandidatura(candidaturaRequestDTO, authentication));
    }
}

