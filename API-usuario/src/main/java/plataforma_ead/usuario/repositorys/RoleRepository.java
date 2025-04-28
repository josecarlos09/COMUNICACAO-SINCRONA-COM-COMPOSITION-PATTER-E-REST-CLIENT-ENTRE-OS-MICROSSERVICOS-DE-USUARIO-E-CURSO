package plataforma_ead.usuario.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma_ead.usuario.enums.RoleType;
import plataforma_ead.usuario.models.RoleModel;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    // Método para buscar uma RoleModel a partir do nome do tipo de role (RoleType)
    Optional<RoleModel> findByRoleNome(RoleType nome);
}
