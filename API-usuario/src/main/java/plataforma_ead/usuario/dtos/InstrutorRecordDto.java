package plataforma_ead.usuario.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record InstrutorRecordDto(@NotNull(message = "Informe o id do usuário") UUID usuarioId) {
}
