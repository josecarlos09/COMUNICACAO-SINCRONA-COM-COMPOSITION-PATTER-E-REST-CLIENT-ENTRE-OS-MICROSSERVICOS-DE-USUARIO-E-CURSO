package plataforma_ead.usuario.services;

import jakarta.validation.constraints.NotNull;
import plataforma_ead.usuario.models.UsuarioCursoModel;
import plataforma_ead.usuario.models.UsuarioModel;

import java.util.UUID;

public interface UsuarioCursoService {
    boolean existsByUsuarioAndCursoId(UsuarioModel usuarioModel, UUID cursoId);

    UsuarioCursoModel saveInscricaoUsuarioCurso(UsuarioCursoModel usuarioCursoModel);
}

