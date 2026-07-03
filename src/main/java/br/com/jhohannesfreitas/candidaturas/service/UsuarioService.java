package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.VagaRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioCreateDTO;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioResponseDTO;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    private final ICandidaturaRepository candidaturaRepository;

    private final IVagaRepository vagaRepository;

    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO) {

        validarEmailExistente(usuarioCreateDTO.getEmail());

        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome(usuarioCreateDTO.getNome())
                .email(usuarioCreateDTO.getEmail())
                .senha(usuarioCreateDTO.getSenha())
                .build();

        UsuarioEntity usuarioSalvo =
                usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail()
        );
    }

    @Transactional
    public UsuarioResponseDTO inscreverUsuarioEmVaga(String emailUsuario, VagaRequestDTO vagaRequestDTO) {

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

        return new UsuarioResponseDTO(
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public List<CandidaturaEntity> listarCandidaturasUsuario(Long usuarioId) {

        UsuarioEntity usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        List<CandidaturaEntity> candidaturas = candidaturaRepository.findByUsuario(usuario);

        return candidaturas;
    }

    private UsuarioEntity buscarUsuarioPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
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
