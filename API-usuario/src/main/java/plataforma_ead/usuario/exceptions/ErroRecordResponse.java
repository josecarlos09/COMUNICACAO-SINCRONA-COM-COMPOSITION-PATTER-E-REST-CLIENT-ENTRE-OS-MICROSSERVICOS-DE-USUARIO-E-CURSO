package plataforma_ead.usuario.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;


/**
 * Classe de resposta padrão para erros na API.
 *
 * Esta classe é utilizada para padronizar o retorno de mensagens de erro
 * em requisições à API, tornando mais fácil a identificação e tratamento
 * dos erros pelos clientes da aplicação.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroRecordResponse(int codigoErro,
                                 String mensagemErro,
                                 Map<String, String> detalhesErro
){}
