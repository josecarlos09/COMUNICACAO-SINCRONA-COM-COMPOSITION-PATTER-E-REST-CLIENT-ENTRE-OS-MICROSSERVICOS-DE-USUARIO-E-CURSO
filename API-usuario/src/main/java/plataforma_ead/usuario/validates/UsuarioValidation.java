package plataforma_ead.usuario.validates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.services.UsuarioService;

/**
 * Classe responsável por realizar validações customizadas sobre os dados de um Usuário.
 *
 * Esta classe implementa a interface Validator do Spring, permitindo:
 * - Aplicar validações específicas para o contexto de criação ou atualização de usuários.
 * - Verificar conflitos de dados no banco (por exemplo, nome ou e-mail já existente).
 * - Registrar erros de validação de forma clara para resposta posterior ao cliente.
 *
 * Obs.: Esta abordagem separa a lógica de validação da camada de Controller, seguindo o princípio
 * de responsabilidade única (SRP), melhorando a organização e a manutenibilidade do código.
 */
@Component
public class UsuarioValidation implements Validator {

    // Logger para registrar mensagens de erro e auxiliar no monitoramento e debug
    Logger logger = LogManager.getLogger(UsuarioValidation.class);

    // Instância do validador padrão do Spring (geralmente responsável pelas validações de anotações como @NotBlank, @Email, etc.)
    private final Validator validador;

    // Serviço de Usuário para acessar operações de verificação no banco de dados
    final UsuarioService usuarioService;

    /**
     * Construtor para injeção de dependências necessárias.
     *
     * @param validador - o validador padrão do Spring, anotado com @Qualifier para especificar qual validador usar.
     * @param usuarioService - serviço para realizar verificações específicas de negócio sobre usuários.
     */
    public UsuarioValidation(@Qualifier("defaultValidator") Validator validador, UsuarioService usuarioService) {
        this.validador = validador;
        this.usuarioService = usuarioService;
    }

    /**
     * Método obrigatório da interface Validator.
     * Define para quais tipos de classe este validador pode ser aplicado.
     *
     * Como este método ainda não está implementado corretamente, ele retorna 'false' (não suporta nenhuma classe diretamente).
     *
     * Em implementações futuras, este método poderia ser ajustado para validar apenas instâncias de UsuarioRecordDto.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    /**
     * Método principal de validação.
     * Executa primeiro as validações padrões (por anotações) e, caso não haja erros,
     * aplica validações específicas de negócio (nome e e-mail exclusivos).
     *
     * @param object - objeto que será validado (espera-se um UsuarioRecordDto).
     * @param errors - estrutura para registrar eventuais erros de validação.
     */
    @Override
    public void validate(Object object, Errors errors) {
        // Faz o cast do objeto recebido para UsuarioRecordDto
        UsuarioRecordDto usuarioRecordDto = (UsuarioRecordDto) object;

        // Primeiro executa as validações básicas (por exemplo: campos obrigatórios)
        validador.validate(usuarioRecordDto, errors);

        // Se nenhuma validação básica falhou, parte para as validações de negócio
        if (!errors.hasErrors()) {
            validadorNomeUsuario(usuarioRecordDto, errors);
            validadorEmailUsuario(usuarioRecordDto, errors);
        }
    }

    /**
     * Valida se o nome do usuário informado já está em uso.
     *
     * @param usuarioRecordDto - DTO contendo os dados do usuário.
     * @param errors - estrutura para registrar eventuais erros encontrados.
     */
    private void validadorNomeUsuario(UsuarioRecordDto usuarioRecordDto, Errors errors) {
        if (usuarioService.existByNome(usuarioRecordDto.nome())) {
            // Registra o erro para o campo "nome"
            errors.rejectValue("nome", "UsuarioNomeConflito", "O nome informado já está em uso");

            // Registra o erro no log para facilitar a rastreabilidade
            logger.error("ERRO: Validation, o nome desse usuário já existe: {}", usuarioRecordDto.nome());
        }
    }

    /**
     * Valida se o e-mail do usuário informado já está em uso.
     *
     * @param usuarioRecordDto - DTO contendo os dados do usuário.
     * @param errors - estrutura para registrar eventuais erros encontrados.
     */
    private void validadorEmailUsuario(UsuarioRecordDto usuarioRecordDto, Errors errors) {
        if (usuarioService.existByEmail(usuarioRecordDto.email())) {
            // Obs.: Aqui está registrando o erro no campo "nome", deveria ser no campo "email".
            errors.rejectValue("email", "UsuarioEmailConflito", "O E-mail informado já está em uso");

            logger.error("ERRO: Validation, o E-mail desse usuário já está em uso: {}", usuarioRecordDto.email());
        }
    }
}