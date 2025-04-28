package plataforma_ead.usuario.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import plataforma_ead.usuario.enums.StatusUsuario;
import plataforma_ead.usuario.validates.SenhaConstraint;

/**
 * DTO (Data Transfer Object) para representar os dados de um usuário.
 * Utilizado para receber e validar dados fornecidos pelo cliente nas operações de:
 * - Registro de novos usuários
 * - Atualização de dados (nome completo, imagem, status)
 * - Alteração de senha
 */
public record UsuarioRecordDto(

        /**
         * Nome de exibição do usuário.
         * Obrigatório no cadastro e deve conter entre 5 e 50 caracteres.
         */
        @NotBlank(groups = UsuarioView.RegistroUsuarioPost.class, message = "O campo nome é obrigatório!")
        @Size(groups = UsuarioView.RegistroUsuarioPost.class, min = 5, max = 50,
                message = "O número máximo de caracteres é 50, e o mínimo é 5.")
        @JsonView(UsuarioView.RegistroUsuarioPost.class)
        String nome,

        /**
         * E-mail do usuário.
         * Obrigatório no cadastro e deve seguir o padrão de e-mail válido.
         */
        @NotBlank(groups = UsuarioView.RegistroUsuarioPost.class, message = "Campo E-mail é obrigatorio")
        @Email(groups = UsuarioView.RegistroUsuarioPost.class, message = "Formato de E-mail invalido. Verifique o campo!")
        @JsonView(UsuarioView.RegistroUsuarioPost.class)
        String email,

        /**
         * Senha do usuário.
         * Obrigatória no cadastro e atualização, validada com regra personalizada (@SenhaConstraint).
         */
        @NotBlank(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class},
                message = "O campo senha é obrigatório")
        @Size(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class}, min = 5, max = 20,
                message = "Informe a senha com no mínimo 5 caracteres e no máximo 20")
        @SenhaConstraint(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class})
        @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.SenhaPut.class})
        String senha,

        /**
         * Senha antiga do usuário.
         * Obrigatória para validação no processo de troca de senha.
         */
        @NotBlank(groups = UsuarioView.SenhaPut.class, message = "Informe a senha antiga")
        @Size(groups = UsuarioView.SenhaPut.class, min = 5, max = 20,
                message = "Informe a senha com no mínimo 5 caracteres e no máximo 20")
        @SenhaConstraint(groups = UsuarioView.SenhaPut.class)
        @JsonView(UsuarioView.SenhaPut.class)
        String senhaAntiga,

        /**
         * Nome completo do usuário.
         * Obrigatório no cadastro e na edição de dados.
         */
        @NotBlank(groups = {UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class},
                message = "Informe o nome completo")
        @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class})
        String nomeCompleto,

        /**
         * Número de telefone celular do usuário.
         * Opcional, porém visível nas operações de cadastro e atualização.
         */
        @JsonView({UsuarioView.RegistroUsuarioPost.class, UsuarioView.UsuarioPut.class})
        String telefoneCelular,

        /**
         * URL da imagem de perfil do usuário.
         * Obrigatória apenas na operação de alteração de imagem.
         */
        @NotBlank(groups = UsuarioView.ImagemPut.class, message = "Informe a URL da imagem")
        @JsonView(UsuarioView.ImagemPut.class)
        String imagemUrl,

        /**
         * Status atual do usuário (ATIVO, INATIVO, BLOQUEADO etc.).
         * Obrigatório nas alterações de status do usuário.
         */
        @NotNull(groups = UsuarioView.StatusUsuarioPut.class, message = "Informe o status do usuário")
        @JsonView(UsuarioView.StatusUsuarioPut.class)
        StatusUsuario statusUsuario) {

    /**
     * Interface interna com subclasses para controle de validações e visualizações (JsonView).
     * Utilizada para agrupar contextos de uso:
     * - RegistroUsuarioPost → para POST /registro
     * - UsuarioPut → para PUT /usuario
     * - SenhaPut → para PUT /senha
     * - ImagemPut → para PUT /imagem
     * - StatusUsuarioPut → para PUT /status
     */
    public interface UsuarioView {
        interface RegistroUsuarioPost {}
        interface UsuarioPut {}
        interface StatusUsuarioPut {}
        interface SenhaPut {}
        interface ImagemPut {}
    }
}
