package plataforma_ead.usuario.validates;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Implementação do validador de senha.
 *
 * A classe `SenhaConstraintImpl` é responsável por validar se uma senha está de acordo com os requisitos
 * especificados pela anotação `@SenhaConstraint`. Essa validação garante que a senha tenha entre 5 e 20 caracteres,
 * inclua pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.
 */
public class SenhaConstraintImpl implements ConstraintValidator<SenhaConstraint, String> {
    // Padrão de expressão regular para verificar a conformidade da senha com os requisitos
    private static final String SENHA_PATTER = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{5,20}$";

    /**
     * Método de inicialização do validador. Neste caso, não há nenhuma inicialização específica necessária,
     * então o método apenas invoca o método `super.initialize` da interface `ConstraintValidator`.
     *
     * @param constraintAnnotation A anotação `@SenhaConstraint` que está sendo aplicada.
     */
    @Override
    public void initialize(SenhaConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Método que realiza a validação da senha.
     *
     * O método verifica se a senha é nula, vazia ou contém espaços em branco. Caso passe por essa verificação,
     * ele valida a senha com o padrão de expressão regular especificado. Se a senha não atender ao padrão, o método
     * retorna `false`, indicando que a senha não é válida.
     */
    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(SENHA_PATTER); // Uso do padrão PATTERN
        // Verificação se a senha é: nula, ou vazia ou com espassos
        if (senha == null || senha.trim().isEmpty() || senha.contains(" ")){
             return false;
        // Validação da senha com o padrão especificado
        } else if (!pattern.matcher(senha).matches()){
            return false;
        }
        // Se a senha passar pelas verificações, ela é considerada válida
        return true;
    }
}