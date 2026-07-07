package br.com.jhohannesfreitas.candidaturas.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "perfil_profissional")
@Getter
@Setter
@NoArgsConstructor
public class PerfilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String resumoCurriculo;

    @Column(columnDefinition = "TEXT")
    private String habilidades;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true)
    private UsuarioEntity usuario;

    public PerfilEntity(String resumoCurriculo, String habilidades, UsuarioEntity usuario) {
        this.resumoCurriculo = resumoCurriculo;
        this.habilidades = habilidades;
        this.usuario = usuario;
    }
}
