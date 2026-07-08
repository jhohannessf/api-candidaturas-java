package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.dto.AlterarStatusCandidaturaRequest;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaRequest;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaResponse;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponse;
import br.com.jhohannesfreitas.candidaturas.domain.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import br.com.jhohannesfreitas.candidaturas.exception.RegraNegocioException;
import br.com.jhohannesfreitas.candidaturas.mapper.CandidaturaMapper;
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

    private final CandidaturaMapper candidaturaMapper;
    private final AuthenticationService authenticationService;

    public List<CandidaturaResponse> obterCandidaturasPorEmail(String email) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        return candidaturaRepository.findByUsuario(usuario)
                .stream()
                .map(candidaturaMapper::toResponse) // Converte para o método toResponse do Mapper
                .toList();

    }

    public List<CandidaturaResponse> obterCandidaturasPorStatus(StatusCandidaturaEnum status) {

        // Buscar por usuário autenticado logado
        UsuarioEntity usuarioLogado = authenticationService.getUsuarioAutenticado();

        return candidaturaRepository.findByUsuarioAndStatus(usuarioLogado, status)
                .stream()
                .map(candidaturaMapper::toResponse)
                .toList();
    }

    @Transactional
    public CandidaturaResponse alterarStatusCandidatura(AlterarStatusCandidaturaRequest alterarStatusCandidaturaRequest, Authentication authentication) {
        // Pegar usuário autenticado
        UsuarioEntity usuarioLogado = (UsuarioEntity) authentication.getPrincipal();

        Long id = alterarStatusCandidaturaRequest.candidaturaId();

        // Busca a candidatura
        CandidaturaEntity candidatura = candidaturaRepository.findById(alterarStatusCandidaturaRequest.candidaturaId())
                .orElseThrow(() -> new RegraNegocioException("Candidatura inexistente"));

        // Buscar a vaga
        VagaEntity vaga = candidatura.getVaga();

        // Altera o Status apenas das vagas que o usario_id seja o mesmo do usuário logado
        if (candidatura.getUsuario().getId().equals(usuarioLogado.getId())) {
            candidatura.setStatus(alterarStatusCandidaturaRequest.status());
        } else {
            throw new AccessDeniedException("Usuário não autorizado para alterar o candidatura de outro usuário");
        }

        // Salva a alteração
        candidaturaRepository.save(candidatura);

        return candidaturaMapper.toResponse(candidatura);

    }

    // Validar candidatura existente
    public void validarCandidaturaExistente(UsuarioEntity usuario, VagaEntity vaga) {

        boolean candidatado =
                candidaturaRepository.existsByUsuarioAndVaga(
                        usuario,
                        vaga);

        if (candidatado) {
            throw new RegraNegocioException(
                    "Usuário já se candidatou para esta vaga");
        }
    }


}

