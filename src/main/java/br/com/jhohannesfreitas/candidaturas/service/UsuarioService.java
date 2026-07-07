package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.VagaRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioResponse;
import br.com.jhohannesfreitas.candidaturas.exception.NotFoundException;
import br.com.jhohannesfreitas.candidaturas.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.model.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.model.VagaEntity;
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

    private final IUsuarioRepository usuarioRepository;

    private final ICandidaturaRepository candidaturaRepository;

    private final IVagaRepository vagaRepository;

    @Transactional
    public UsuarioResponse inscreverUsuarioEmVaga(String emailUsuario, VagaRequestDTO vagaRequestDTO) {

        UsuarioEntity usuario = buscarUsuarioPorEmail(emailUsuario);

        VagaEntity vaga = vagaRepository
                .findByEmpresaAndCargo(
                        vagaRequestDTO.getEmpresa(),
                        vagaRequestDTO.getCargo())
                .orElseThrow(() ->
                        new RuntimeException("Vaga não encontrada"));

        validarCandidaturaExistente(usuario, vaga);

        CandidaturaEntity candidatura = CandidaturaEntity.builder()
                .usuario(usuario)
                .vaga(vaga)
                .status(StatusCandidaturaEnum.APLICADO)
                .dataAplicacao(LocalDate.now())
                .build();

        candidaturaRepository.save(candidatura);

        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    private UsuarioEntity buscarUsuarioPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("Usuário de e-mail: " + email + " não encontrado"));
    }

    private void validarEmailExistente(String email) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException(
                    "E-mail já cadastrado");
        }
    }

    private void validarCandidaturaExistente(
            UsuarioEntity usuario,
            VagaEntity vaga) {

        boolean jaCandidatado =
                candidaturaRepository.existsByUsuarioAndVaga(
                        usuario,
                        vaga);

        if (jaCandidatado) {
            throw new RuntimeException(
                    "Usuário já se candidatou para esta vaga");
        }
    }
}
