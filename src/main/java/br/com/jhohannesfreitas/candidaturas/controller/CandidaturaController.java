package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.dto.AlterarStatusCandidaturaRequest;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaRequest;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaResponse;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponse;
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
    public ResponseEntity<List<CandidaturaResponse>> listarPorUsuario(Authentication authentication) {
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();
        return ResponseEntity.ok(
                candidaturaService.obterCandidaturasPorEmail(usuarioLogado.getEmail())
        );
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<CandidaturaResponse>> listarPorStatus(@PathVariable StatusCandidaturaEnum status) {
        return ResponseEntity.ok(candidaturaService.obterCandidaturasPorStatus(status));
    }

    @PatchMapping
    public ResponseEntity<CandidaturaResponse> alterarStatus(@RequestBody AlterarStatusCandidaturaRequest alterarStatusCandidaturaRequest, Authentication authentication) {
        return ResponseEntity.ok(candidaturaService.alterarStatusCandidatura(alterarStatusCandidaturaRequest, authentication));
    }
}

