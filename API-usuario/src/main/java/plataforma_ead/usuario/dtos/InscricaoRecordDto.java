package plataforma_ead.usuario.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InscricaoRecordDto(UUID usuarioId,
                                 @NotNull(message = "O ID do curso é obrigatorio") UUID cursoId) {
}
