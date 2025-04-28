package plataforma_ead.usuario.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRecordDto(@NotBlank String nome,
                             @NotBlank String senha) {
}

