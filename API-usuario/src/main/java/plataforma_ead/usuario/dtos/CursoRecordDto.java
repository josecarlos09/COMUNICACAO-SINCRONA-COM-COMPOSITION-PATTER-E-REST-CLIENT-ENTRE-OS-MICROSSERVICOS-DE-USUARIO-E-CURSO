package plataforma_ead.usuario.dtos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import plataforma_ead.usuario.enums.CursoNivel;
import plataforma_ead.usuario.enums.CursoStatus;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true) // Não inclua valores desconhecidos
@JsonInclude(JsonInclude.Include.NON_NULL)// Não inclua valores nulos
public record CursoRecordDto(
        UUID cursoId,
        String nome,
        String descricao,
        String duracaoCurso,
        String imagem,
        CursoStatus cursoStatus,
        UUID usuarioInstrutor,
        CursoNivel cursoNivel){
}

