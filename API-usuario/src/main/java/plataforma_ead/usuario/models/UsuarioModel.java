package plataforma_ead.usuario.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.hateoas.RepresentationModel;
import plataforma_ead.usuario.enums.StatusUsuario;
import plataforma_ead.usuario.enums.TipoUsuario;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Modelo que representa a entidade "Usuario" no banco de dados.
 * A classe é mapeada para a tabela TB_USUARIO e inclui informações
 * detalhadas sobre o usuário, como nome, CPF, e-mail, status, tipo
 * de usuário, entre outros. A classe também implementa o modelo
 * HATEOAS para integração com APIs RESTful.
 */

@JsonInclude(JsonInclude.Include.NON_NULL) // Iclua apenas valores que não sejam nulos.
@Entity
@Table(name = "TB_USUARIO")
public class UsuarioModel extends RepresentationModel<UsuarioModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private UUID usuarioId;

    @Column(nullable = false, unique = true, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @JsonIgnore // No processo de serialização ingnore a senha (a senha será ingnorada em procesos de serialização)
    @Column(nullable = false, unique = true, length = 255)
    private String senha;

    @Column(nullable = false, length = 255)
    private String nomeCompleto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusUsuario statusUsuario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Column(length = 20)
    private String telefoneCelular;

    @Column(nullable = true, length = 255)
    private String imagemUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Acesso apenas escrita
    @ManyToMany(fetch = FetchType.LAZY) // Associação de muitos para muitos
    // Será gerado uma tabela auxiliar TB_USUARIO_ROLE que conterar o id de usuario e o id de role
    @JoinTable(name = "TB_USUARIO_ROLE",
            joinColumns = @JoinColumn(name = "usuarioId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<RoleModel> roles = new HashSet<>(); // Estrutura de Set

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<UsuarioCursoModel> usuarioCurso;

    // Método para converter um Curso em CursoUsuarioModel
    public UsuarioCursoModel converterUsuarioParaUsuarioCursoModel(UUID cursoId) {
        return new UsuarioCursoModel(null, cursoId, this);
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public StatusUsuario getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(StatusUsuario statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

    public Set<UsuarioCursoModel> getUsuarioCurso() {
        return usuarioCurso;
    }

    public void setUsuarioCurso(Set<UsuarioCursoModel> usuarioCurso) {
        this.usuarioCurso = usuarioCurso;
    }
}