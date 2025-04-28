package plataforma_ead.usuario.specifications;

import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import plataforma_ead.usuario.models.UsuarioCursoModel;
import plataforma_ead.usuario.models.UsuarioModel;

import java.util.UUID;

/**
 * Classe que contém a definição de especificações para realizar consultas dinâmicas na entidade `UsuarioModel`.
 * Utiliza a biblioteca `spring-data-specifications` para permitir a criação de filtros flexíveis e reutilizáveis
 * ao realizar consultas no banco de dados.
 */
public class SpecificationsTemplate {
    // Mapeamento dos filtros dinamicos em uma interface que herda o Specification na entidade UsuarioModel
    @And({
            @Spec(path = "tipoUsuario", spec = Equal.class),
            @Spec(path = "statusUsuario", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "nome", spec = Like.class),
            @Spec(path = "nomeCompleto", spec = LikeIgnoreCase.class)
    })
    public interface UsuarioSpec extends Specification<UsuarioModel>{}

    // Specification para consulta getAll no controller de Usuario
    public static Specification<UsuarioModel> usuarioCurso(final UUID cursoId){
        return (root, query, criteriaBuilder) ->{
            query.distinct(true);
            Join<UsuarioModel, UsuarioCursoModel> usuarioJoinCurso = root.join("usuarioCurso");
            return criteriaBuilder.equal(usuarioJoinCurso.get("cursoId"), cursoId);
        };
    }
}