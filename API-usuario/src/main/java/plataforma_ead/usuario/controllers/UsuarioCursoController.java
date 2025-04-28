package plataforma_ead.usuario.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma_ead.usuario.clients.CursoClient;
import plataforma_ead.usuario.dtos.CursoRecordDto;
import plataforma_ead.usuario.dtos.InscricaoRecordDto;
import plataforma_ead.usuario.models.UsuarioCursoModel;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.services.UsuarioCursoService;
import plataforma_ead.usuario.services.UsuarioService;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UsuarioCursoController {

    final CursoClient cursoCliente;
    final UsuarioService usuarioService;
    final UsuarioCursoService usuarioCursoService;

    public UsuarioCursoController(CursoClient cursoCliente, UsuarioService usuarioService, UsuarioCursoService usuarioCursoService) {
        this.cursoCliente = cursoCliente;
        this.usuarioService = usuarioService;

        this.usuarioCursoService = usuarioCursoService;
    }

    @GetMapping("/usuario/{usuarioId}/cursos")
    public ResponseEntity<Page<CursoRecordDto>> getAllCursoByUsuario(@PageableDefault(sort = "dataInicio", direction = Sort.Direction.ASC) Pageable pageable,
                                                                     @PathVariable(value = "usuarioId") UUID usuarioId){
        return ResponseEntity.status(HttpStatus.OK).body(cursoCliente.getAllCursosByUsuario(usuarioId, pageable));
    }

    @PostMapping("/usuario/{usuarioId}/curso/inscricao")
    public ResponseEntity<Object> saveInscricaoUsuarioCurso(@PathVariable(value = "usuarioId")UUID usuarioId,
                                                            @RequestBody @Valid InscricaoRecordDto inscricaoRecordDto){
        Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(usuarioId);
        if (usuarioCursoService.existsByUsuarioAndCursoId(usuarioModelOptional.get(), inscricaoRecordDto.cursoId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO: Inscrição já existente!");
        }
        // Verificação de usuario

        //Salvamento entre UsuarioId e cursoId
        UsuarioCursoModel usuarioCursoModel = usuarioCursoService.saveInscricaoUsuarioCurso(usuarioModelOptional.get().converterUsuarioParaUsuarioCursoModel(inscricaoRecordDto.cursoId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCursoModel);
    }
}
