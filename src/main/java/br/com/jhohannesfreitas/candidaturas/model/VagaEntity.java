package br.com.jhohannesfreitas.candidaturas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "vagas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VagaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String empresa;

    @Column(nullable = false)
    private String cargo;

    @Column(columnDefinition = "TEXT", nullable = false) // descrição da vaga podem ficar grandes
    private String descricaoVaga;

    @Enumerated(EnumType.STRING)
    private StatusCandidaturaEnum status;

    private LocalDate dataAplicacao;

    @Column(columnDefinition = "TEXT") // análises de IA podem ficar grandes
    private String analiseIa;

    @ManyToOne(fetch = FetchType.LAZY) // o padrão para @ManyToOne é EAGER, por isso é necessário informar aqui
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL)
    private List<CandidaturaEntity> candidaturas;

}
