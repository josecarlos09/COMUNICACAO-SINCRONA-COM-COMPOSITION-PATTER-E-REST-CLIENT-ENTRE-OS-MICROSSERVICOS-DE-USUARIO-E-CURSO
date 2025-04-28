package plataforma_ead.usuario.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma_ead.usuario.models.UsuarioCursoModel;
import plataforma_ead.usuario.models.UsuarioModel;

import java.util.UUID;

public interface UsuarioCursoRepository extends JpaRepository<UsuarioCursoModel, UUID> {
    boolean existsByUsuarioAndCursoId(UsuarioModel usuarioModel, UUID usuarioId);
}
