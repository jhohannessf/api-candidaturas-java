package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.domain.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import br.com.jhohannesfreitas.candidaturas.repository.ICandidaturaRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IUsuarioRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidaturaService {

    private final IUsuarioRepository usuarioRepository;
    private final ICandidaturaRepository candidaturaRepository;
    private final IVagaRepository vagaRepository;

    public List<CandidaturaResponseDTO> obterCandidaturasPorEmail(String email) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        return candidaturaRepository.findByUsuario(usuario)
                .stream()
                .map(this::toDTO) // Converte para o método toDTO
                .toList();

    }

    private CandidaturaResponseDTO toDTO(CandidaturaEntity candidatura) {

        VagaEntity vaga = candidatura.getVaga();

        return new CandidaturaResponseDTO(
                candidatura.getId(),
                candidatura.getStatus(),
                candidatura.getDataAplicacao()
        );
    }

    public List<VagaResponseDTO> obterCandidaturasPorStatus(String status, String email) {
        // Pegar o status fornecido pelo usuário
        String stats = candidaturaRepository.findByStatus(status);
        if (stats == null) {
            throw new RuntimeException("Status não encontrado");
        }

        // Pegar o e-mail
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        return obterCandidaturasPorStatus(stats, email);
    }

    @Transactional
    public CandidaturaResponseDTO alterarStatusCandidatura(CandidaturaRequestDTO candidaturaRequestDTO, Authentication authentication) {
        // Pegar usuário autenticado
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();

        // Busca a candidatura
        CandidaturaEntity candidatura = candidaturaRepository.findById(candidaturaRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("Candidatura inexistente"));

        // Buscar a vaga
        VagaEntity vaga = candidatura.getVaga();

        // Altera o Status apenas das vagas que o usario_id seja o mesmo do usuário logado
        if (candidatura.getUsuario().getId().equals(usuarioLogado.getId())) {
            candidatura.setStatus(candidaturaRequestDTO.getStatus());
        }else {
            throw new AccessDeniedException("Usuário não autorizado para alterar o candidatura de outro usuário");
        }

        // Salva a alteração
        candidaturaRepository.save(candidatura);

        return toDTO(candidatura);

    }
}

