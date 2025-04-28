package plataforma_ead.usuario.services.impl;

import org.springframework.stereotype.Service;
import plataforma_ead.usuario.models.UsuarioCursoModel;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.repositorys.UsuarioCursoRepository;
import plataforma_ead.usuario.services.UsuarioCursoService;

import java.util.UUID;

@Service
public class UsuarioCursoServiceImpl implements UsuarioCursoService {
    final UsuarioCursoRepository usuarioCursoRepository;

    public UsuarioCursoServiceImpl(UsuarioCursoRepository usuarioCursoRepository) {
        this.usuarioCursoRepository = usuarioCursoRepository;
    }

    @Override
    public boolean existsByUsuarioAndCursoId(UsuarioModel usuarioModel, UUID usuarioId) {
        return usuarioCursoRepository.existsByUsuarioAndCursoId(usuarioModel, usuarioId);
    }

    @Override
    public UsuarioCursoModel saveInscricaoUsuarioCurso(UsuarioCursoModel usuarioCursoModel) {
        return usuarioCursoRepository.save(usuarioCursoModel);
    }
}
