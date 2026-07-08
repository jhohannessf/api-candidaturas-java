package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.VagaRequest;
import br.com.jhohannesfreitas.candidaturas.dto.VagaResponse;
import br.com.jhohannesfreitas.candidaturas.exception.RegraNegocioException;
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
    public VagaResponse criarVaga(VagaRequest vaga) {

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

            return new VagaResponse(vagaSalva.getEmpresa(),vagaSalva.getCargo(),vagaSalva.getDescricaoVaga(),vagaSalva.getStatus());
        }

    }

    // Listar todas as vagas
    public List<VagaResponse> listarVagas() {
        return vagaRepository.findAll()
                .stream()
                .map(vaga -> new VagaResponse(vaga.getEmpresa(),vaga.getCargo(),vaga.getDescricaoVaga(),vaga.getStatus())) // Transformando minha VagaEntity em DTO
                .toList();
    }

    // Buscar vaga por ID
    public VagaResponse buscaPorNomeEmpresa(String empresa) {
        return vagaRepository.findByEmpresaContainingIgnoreCase(empresa)
                .map(vaga -> new VagaResponse(vaga.getEmpresa(),vaga.getCargo(),vaga.getDescricaoVaga(),vaga.getStatus()))
                .orElseThrow(() -> new RegraNegocioException("Vaga não encontrada"));

    }

    // Buscar por empresa e cargo
    public VagaEntity buscarPorEmpresaECargo(String empresa, String cargo) {
        return vagaRepository.findByEmpresaAndCargo(empresa, cargo)
                .orElseThrow(() ->
                        new RegraNegocioException("Vaga não encontrada"));
    }
}

