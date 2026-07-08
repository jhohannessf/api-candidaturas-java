package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.domain.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioResponse;
import br.com.jhohannesfreitas.candidaturas.dto.VagaRequest;
import br.com.jhohannesfreitas.candidaturas.exception.RegraNegocioException;
import br.com.jhohannesfreitas.candidaturas.mapper.UsuarioMapper;
import br.com.jhohannesfreitas.candidaturas.repository.ICandidaturaRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IUsuarioRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IVagaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;

    private final IUsuarioRepository usuarioRepository;

    private final AuthenticationService authenticationService;

    private final ICandidaturaRepository candidaturaRepository;

    private final IVagaRepository vagaRepository;

    private final VagaService vagaService;

    private final CandidaturaService candidaturaService;

    @Transactional
    public UsuarioResponse inscreverUsuarioEmVaga(VagaRequest vagaRequest) {

        // Buscar por usuário autenticado logado
        UsuarioEntity usuario = authenticationService.getUsuarioAutenticado();

        // Busca a vaga pela Empresa e Cargo
        VagaEntity vaga = vagaService.buscarPorEmpresaECargo(vagaRequest.empresa(),vagaRequest.cargo());

        // Valida se o candidato já está na vaga
        candidaturaService.validarCandidaturaExistente(usuario, vaga);

        CandidaturaEntity candidatura = CandidaturaEntity.builder()
                .usuario(usuario)
                .vaga(vaga)
                .status(StatusCandidaturaEnum.APLICADO)
                .dataAplicacao(LocalDate.now())
                .build();

        candidaturaRepository.save(candidatura);

        return usuarioMapper.toResponse(usuario);
    }

    private UsuarioEntity buscarUsuarioPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RegraNegocioException("Usuário de e-mail: " + email + " não encontrado"));
    }

    private void validarEmailExistente(String email) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraNegocioException(
                    "E-mail já cadastrado");
        }
    }


}
