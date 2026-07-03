package br.com.jhohannesfreitas.candidaturas.model;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity implements UserDetails { //UserDetails: Um usuário autenticável do Sistema
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<VagaEntity> vagaEntities = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<CandidaturaEntity> candidaturas;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    PerfilProfissionalEntity perfilProfissionalEntity;

    @ManyToMany(fetch = FetchType.EAGER) // as roles serão carregadas automaticamente junto com o usuário
    @JoinTable(name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"), // define qual coluna representa o usuario
            inverseJoinColumns = @JoinColumn(name = "role_id")) // define qual coluna representa a role
    private Set<RolesEntity> roles = new HashSet<>(); // um usuário possui várias roles, mas não se repetem.

    // Se o usuário pode ou não acessar determinado recurso na API
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // Como que o Spring reconhece a senha de um usuário para autenticar no login
    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    // // Como que o Spring reconhece o username de um usuário para autenticar no login
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
