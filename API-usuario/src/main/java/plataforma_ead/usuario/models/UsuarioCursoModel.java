package plataforma_ead.usuario.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USUARIO_CURSO")
public class UsuarioCursoModel implements Serializable {

    public UsuarioCursoModel(UUID inscricaoId, UUID cursoId, UsuarioModel usuario) {
        this.inscricaoId = inscricaoId;
        this.cursoId = cursoId;
        this.usuario = usuario;
    }

    public UsuarioCursoModel(){}

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID inscricaoId;

    @Column(nullable = false)
    private UUID cursoId;

    // Relacionamento de UsuarioCursoModel com UsuarioModel
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UsuarioModel usuario;

    public UUID getInscricaoId() {
        return inscricaoId;
    }

    public void setInscricaoId(UUID inscricaoId) {
        this.inscricaoId = inscricaoId;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
}
