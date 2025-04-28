package plataforma_ead.usuario.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.services.UsuarioService;
import plataforma_ead.usuario.specifications.SpecificationsTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    Logger logger = LogManager.getLogger(UsuarioController.class); // uso de logs
    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para obter todos os usuários com paginação e filtragem via Specification.
     * Também adiciona links HATEOAS para cada usuário listado.
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioModel>> getAllUsuarios(SpecificationsTemplate.UsuarioSpec spec,
                                                             @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC)
                                                             Pageable pageable,
                                                             @RequestParam(required = false) UUID cursoId){

        // Se o id do curso for informado então devolva a lista de usuarios matriculados nesse curso especificado
        Page<UsuarioModel> usuarioModelPageSpec = (cursoId != null)
                ? usuarioService.findAll(SpecificationsTemplate.usuarioCurso(cursoId).and(spec), pageable)
                : usuarioService.findAll(spec, pageable);

        // Verifica se a lista não está vazia antes de adicionar os links HATEOAS
        if (!usuarioModelPageSpec.isEmpty()) {
            for (UsuarioModel usuario : usuarioModelPageSpec.toList()) {
                usuario.add(linkTo(methodOn(UsuarioController.class).getOnUsuario(usuario.getUsuarioId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioModelPageSpec);
    }

    /**
     * Endpoint para obter um usuário específico pelo ID.
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> getOnUsuario(@PathVariable(value = "usuarioId") UUID usuarioId) {
        logger.debug("GET: getOnUsuario, consulta: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(usuarioId));
    }

    /**
     * Endpoint para deletar um usuário pelo ID.
     */
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "usuarioId") UUID usuarioId) {
        usuarioService.deleteUsuarioId(usuarioService.findById(usuarioId).get());
        logger.debug("DELETE: deleteUsuario, usuarioId recebido: {}", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }

    /**
     * Endpoint para atualizar dados do usuário pelo ID.
     */
    @PutMapping("/{usuarioId}/usuario")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "usuarioId") UUID usuarioId,
                                                @RequestBody @Validated(UsuarioRecordDto.UsuarioView.UsuarioPut.class)
                                                @JsonView(UsuarioRecordDto.UsuarioView.UsuarioPut.class)
                                                UsuarioRecordDto usuarioRecordDto) {
        logger.debug("PUT: updateUsuario, usuarioId recebido: {} ", usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateUsuario(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }

    /**
     * Endpoint para atualização de senha de um usuário pelo ID.
     * Valida se a senha antiga informada corresponde à senha cadastrada.
     */
    @PatchMapping("/{usuarioId}/senha")
    public ResponseEntity<Object> updateSenha(@PathVariable(value = "usuarioId") UUID usuarioId,
                                              @RequestBody @Validated(UsuarioRecordDto.UsuarioView.SenhaPut.class)
                                              @JsonView(UsuarioRecordDto.UsuarioView.SenhaPut.class)
                                              UsuarioRecordDto dadosUsuarioRecordDto) {
        Optional<UsuarioModel> optionalModelUsuario = usuarioService.findById(usuarioId);

        // Verifica se a senha antiga informada corresponde à senha atual do usuário
        if (!optionalModelUsuario.get().getSenha().equals(dadosUsuarioRecordDto.senhaAntiga())) {
            logger.error("PUT: updateSenha, {} inexistente", dadosUsuarioRecordDto.senha());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Senha inexistente!");
        }

        logger.debug("PUT: updateSenha, senha atualizada com sucesso!");
        usuarioService.updateSenha(dadosUsuarioRecordDto, optionalModelUsuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Senha atualizada com sucesso!");
    }

    /**
     * Endpoint para atualizar o status do usuário pelo ID.
     */
    @PatchMapping("/{usuarioId}/status")
    public ResponseEntity<Object> updateStatus(@PathVariable(value = "usuarioId") UUID usuarioId,
                                               @RequestBody @Validated(UsuarioRecordDto.UsuarioView.StatusUsuarioPut.class) UsuarioRecordDto usuarioRecordDto) {
        logger.debug("PUT: status do usuário atualizado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateStatusUsuario(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }

    @PatchMapping("/{usuarioId}/imagem")
    public ResponseEntity<Object> updateImagem(@PathVariable(value = "usuarioId")UUID usuarioId,
                                               @RequestBody @Validated(UsuarioRecordDto.UsuarioView.ImagemPut.class)
                                               @JsonView(UsuarioRecordDto.UsuarioView.ImagemPut.class)
                                               UsuarioRecordDto usuarioRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateImagem(usuarioService.findById(usuarioId).get(), usuarioRecordDto));
    }
}