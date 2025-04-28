package plataforma_ead.usuario.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plataforma_ead.usuario.configs.security.TokenJwt;
import plataforma_ead.usuario.dtos.JwtRecordDto;
import plataforma_ead.usuario.dtos.LoginRecordDto;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.services.UsuarioService;
import plataforma_ead.usuario.validates.UsuarioValidation;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoUsuarioController {
    Logger logger = LogManager.getLogger(AutenticacaoUsuarioController.class); // uso de logs

    final UsuarioService usuarioService;
    final AuthenticationManager authenticationManager;
    final TokenJwt tokenJwt;
    final UsuarioValidation usuarioValidation;

    public AutenticacaoUsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, TokenJwt tokenJwt, UsuarioValidation usuarioValidation) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenJwt = tokenJwt;
        this.usuarioValidation = usuarioValidation;
    }

    /**
     * Endpoint para autenticação de usuários.
     * Recebe um DTO com nome e senha e retorna um token JWT caso as credenciais sejam válidas.
     *
     * @param loginRecordDto DTO contendo as credenciais do usuário.
     * @return ResponseEntity com o token JWT em caso de sucesso.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtRecordDto> autenticacao(@RequestBody @Valid LoginRecordDto loginRecordDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRecordDto.nome(), loginRecordDto.senha()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtRecordDto(tokenJwt.gerarJwt(authentication)));
    }

    /**
     * Endpoint para registro de novo usuário.
     * <p>
     * Valida se o nome, e-mail, senha e CPF já existem antes de registrar um novo usuário.
     * Caso qualquer um dos campos já esteja cadastrado, retorna um status de CONFLICT (409).
     * Caso contrário, registra o usuário e retorna um status de CREATED (201).
     * </p>
     *
     * @param usuarioRecordDto DTO contendo os dados do usuário a ser registrado.
     * @return ResponseEntity<Object> com a resposta da requisição.
     */
    @PostMapping("/registro")
    public ResponseEntity<Object> registroUsuario(@RequestBody
                                                  @Validated(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class)
                                                  @JsonView(UsuarioRecordDto.UsuarioView.RegistroUsuarioPost.class)
                                                  UsuarioRecordDto usuarioRecordDto,
                                                  Errors errors){
        // Validações customizadas
        usuarioValidation.validate(usuarioRecordDto, errors);
        // Verificação se a validação customizada com tem erros
        if (errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        // Log para depuração, exibindo os dados do usuário que está sendo registrado.
        logger.debug("POST: Registro de usuário {}", usuarioRecordDto);

        // Salva o usuário na base de dados e retorna a resposta com status 201 (Created).
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(usuarioRecordDto));
    }
}
