package plataforma_ead.usuario.validates;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented // anotação do java doc
@Constraint(validatedBy = SenhaConstraintImpl.class) // Indica qual é a classe que implementa as regras
@Target({ElementType.METHOD, ElementType.FIELD}) // A anotação vai servir a nivel de método e de campo(arquivo)
@Retention(RetentionPolicy.RUNTIME) // A validação vai ocorre em tempo de execução.
public @interface SenhaConstraint {
 /**
 * Mensagem padrão de erro a ser exibida caso a senha não atenda aos requisitos.
 * Esta mensagem pode ser substituída por uma personalizada no momento da aplicação.
 */
 String message() default """
            A senha deve conter entre 5 e 20 caracteres,
            a senha deve incluir pelo menos uma letra maiúscula (A-Z),
            a senha deve incluir pelo menos uma letra minúscula (a-z),
            a senha deve conter pelo menos um número (0-9),
            a senha deve incluir pelo menos um caractere especial dentre os seguintes: @, $, !, %, *, ?, &.
            """;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}