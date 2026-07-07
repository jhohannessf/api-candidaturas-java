package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.VagaRequest;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponseDTO;
import br.com.jhohannesfreitas.candidaturas.exception.NotFoundException;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import br.com.jhohannesfreitas.candidaturas.repository.IVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VagaService {

    private final IVagaRepository vagaRepository;

    // Criar vaga
    public VagaResponseDTO criarVaga(VagaRequest vaga) {

        // Validar se a vaga já não existe
        Optional<VagaEntity> vagaexiste = vagaRepository.findByEmpresaAndCargo(vaga.empresa(), vaga.cargo());
        if (vagaexiste.isPresent()) {
            throw new RuntimeException("Vaga já cadastrada");
        } else {
            VagaEntity vagaEntity = VagaEntity.builder()
                    .empresa(vaga.empresa())
                    .cargo(vaga.cargo())
                    .build();

            VagaEntity vagaSalva = vagaRepository.save(vagaEntity);

            return new VagaResponseDTO(vagaSalva.getEmpresa(),vagaSalva.getCargo(),vagaSalva.getDescricaoVaga(),vagaSalva.getStatus());
        }

    }

    // Listar todas as vagas
    public List<VagaResponseDTO> listarVagas() {
        return vagaRepository.findAll()
                .stream()
                .map(vaga -> new VagaResponseDTO(vaga.getEmpresa(),vaga.getCargo(),vaga.getDescricaoVaga(),vaga.getStatus())) // Transformando minha VagaEntity em DTO
                .toList();
    }

    // Buscar vaga por ID
    public VagaResponseDTO buscaPorNomeEmpresa(String empresa) {
        return vagaRepository.findByEmpresaContainingIgnoreCase(empresa)
                .map(vaga -> new VagaResponseDTO(vaga.getEmpresa(),vaga.getCargo(),vaga.getDescricaoVaga(),vaga.getStatus()))
                .orElseThrow(() -> new NotFoundException("Vaga não encontrada"));

    }

    // Buscar por empresa e cargo
    public VagaEntity buscarPorEmpresaECargo(String empresa, String cargo) {
        return vagaRepository.findByEmpresaAndCargo(empresa, cargo)
                .orElseThrow(() ->
                        new RuntimeException("Vaga não encontrada"));
    }
}

