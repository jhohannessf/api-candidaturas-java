package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.VagaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.model.VagaEntity;
import br.com.jhohannesfreitas.candidaturas.repository.ICandidaturaRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidaturaService {

    private final IUsuarioRepository usuarioRepository;
    private final ICandidaturaRepository candidaturaRepository;

    public List<VagaResponseDTO> obterCandidaturasPorEmail(String email) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        return candidaturaRepository.findByUsuario(usuario)
                .stream()
                .map(this::toDTO) // Converte para o método toDTO
                .toList(); // Transforma o resultado em uma lista
    }

    private VagaResponseDTO toDTO(CandidaturaEntity candidatura) {

        VagaEntity vaga = candidatura.getVaga();

        return new VagaResponseDTO(
                vaga.getEmpresa(),
                vaga.getCargo(),
                vaga.getDescricaoVaga()
        );
    }
}

