package plataforma_ead.usuario.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento de erro NOT FOUND
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroRecordResponse> trataNotFoundException(NotFoundException ex) {
        var erroRecordResponse = new ErroRecordResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroRecordResponse);
    }

    // Tratamento de erro BAD REQUEST por validações (campos inválidos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRecordResponse> manipulaExceptionDeValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagemErro = error.getDefaultMessage();
            erros.put(nomeCampo, mensagemErro);
        });

        var erroResponse = new ErroRecordResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos campos enviados",
                erros
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    // Tratamento de erro para enums inválidos
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroRecordResponse> handleInvalidFormatException(HttpMessageNotReadableException exception) {
        Map<String, String> erros = new HashMap<>();

        if (exception.getCause() instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType() != null && invalidFormatException.getTargetType().isEnum()) {
                String fieldName = invalidFormatException.getPath().get(invalidFormatException.getPath().size() - 1).getFieldName();
                String erroMensagem = String.format(
                        "O valor fornecido para '%s' não é válido. Valores permitidos: %s",
                        fieldName,
                        Arrays.toString(invalidFormatException.getTargetType().getEnumConstants())
                );
                erros.put(fieldName, erroMensagem);
            }
        }

        var erroResponse = new ErroRecordResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de leitura. Informção incorreta",
                erros
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    // Tratamento para argumentos ilegais (ex: validações de regra de negócio)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroRecordResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        var erroResponse = new ErroRecordResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Argumento ilegal: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    // Tratamento para erros de estado ilegal (ex: chamadas fora de ordem ou contexto inválido)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroRecordResponse> handleIllegalStateException(IllegalStateException ex) {
        var erroResponse = new ErroRecordResponse(
                HttpStatus.CONFLICT.value(),
                "Estado ilegal: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResponse);
    }

    // Tratamento para violação de integridade no banco de dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroRecordResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var erroResponse = new ErroRecordResponse(
                HttpStatus.CONFLICT.value(),
                "Violação de integridade de dados: " + ex.getMostSpecificCause().getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResponse);
    }
}