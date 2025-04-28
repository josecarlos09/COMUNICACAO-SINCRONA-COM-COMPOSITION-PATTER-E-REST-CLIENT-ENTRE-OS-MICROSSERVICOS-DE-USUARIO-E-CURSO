package plataforma_ead.usuario.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plataforma_ead.usuario.dtos.InstrutorRecordDto;
import plataforma_ead.usuario.services.UsuarioService;

/**
 * Controlador responsável pelo gerenciamento das inscrições dos instrutores na plataforma EAD.
 * Expondo endpoints para operações relacionadas aos instrutores.
 */
@RestController
@RequestMapping("/instrutor")  // Definindo o prefixo da URL para as requisições relacionadas ao instrutor
public class InstrutorController {

    final UsuarioService usuarioService;  // Serviço utilizado para a lógica de negócio relacionada ao usuário

    /**
     * Construtor da classe InstrutorController.
     *
     * @param usuarioService Serviço de usuários utilizado para registrar o instrutor
     */
    public InstrutorController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint responsável por registrar um instrutor.
     *
     * @param instrutorRecordDto Dados do instrutor a ser registrado, recebidos no corpo da requisição.
     * @return ResponseEntity com o status da operação e o objeto de resposta.
     */
    @PostMapping("/inscricao")  // Mapeando a requisição POST para "/instrutor/inscricao"
    public ResponseEntity<Object> salvarInscricoesInstrutores(@RequestBody @Valid InstrutorRecordDto instrutorRecordDto) {
        // Buscando o usuário pelo ID fornecido no DTO e registrando como instrutor
        return ResponseEntity.status(HttpStatus.OK)  // Retornando status 200 (OK) se a operação for bem-sucedida
                .body(usuarioService.registrarInstrutor(usuarioService.findById(instrutorRecordDto.usuarioId()).get()));
    }
}