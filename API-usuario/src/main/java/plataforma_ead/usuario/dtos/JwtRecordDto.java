package plataforma_ead.usuario.dtos;

import jakarta.validation.constraints.NotBlank;

public record JwtRecordDto(@NotBlank String token,
                           String type) {

    // Construtor que inicia o type
    public JwtRecordDto(@NotBlank String token) {
        this(token, "Bearer");
    }
}