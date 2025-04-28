package plataforma_ead.usuario.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.specifications.SpecificationsTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService{

    Optional<UsuarioModel> findById(UUID usuarioId);

    UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel);

    UsuarioModel saveUsuario(UsuarioRecordDto recordDtoUsuario);

    boolean existByNome(String nome);

    UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto recordDtoUsuario);

    UsuarioModel updateSenha(UsuarioRecordDto usuarioRecordDto, UsuarioModel usuarioModel);

    Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable);

    UsuarioModel updateStatusUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto);

    UsuarioModel updateImagem(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto);

    UsuarioModel registrarInstrutor(UsuarioModel usuarioModel);

    boolean existByEmail(String email);
}

