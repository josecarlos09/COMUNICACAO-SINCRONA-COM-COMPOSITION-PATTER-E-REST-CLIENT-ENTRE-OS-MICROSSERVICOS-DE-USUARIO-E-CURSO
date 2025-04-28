package plataforma_ead.usuario.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plataforma_ead.usuario.dtos.UsuarioRecordDto;
import plataforma_ead.usuario.enums.RoleType;
import plataforma_ead.usuario.enums.StatusUsuario;
import plataforma_ead.usuario.enums.TipoUsuario;
import plataforma_ead.usuario.exceptions.NotFoundException;
import plataforma_ead.usuario.models.UsuarioModel;
import plataforma_ead.usuario.repositorys.UsuarioRepository;
import plataforma_ead.usuario.services.RoleService;
import plataforma_ead.usuario.services.UsuarioService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Repositório para manipulação de dados de usuários no banco de dados
    final UsuarioRepository usuarioRepository;
    final PasswordEncoder passwordEncoder;
    final RoleService roleService;

    // Construtor para injeção de dependência do repositório
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param usuarioId ID do usuário
     * @return Um Optional contendo o usuário encontrado, ou vazio se não encontrado.
     * @throws NotFoundException Caso o usuário não seja encontrado.
     */
    @Override
    public Optional<UsuarioModel> findById(UUID usuarioId) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(usuarioId);

        // Lança uma exceção personalizada caso o usuário não seja encontrado
        if (usuarioModelOptional.isEmpty()){
            throw new NotFoundException("ERRO, USUARIO NÃO ENCONTRADO!");
        }
        return usuarioModelOptional;
    }

    /**
     * Exclui um usuário do banco de dados.
     *
     * @param usuarioModel Objeto do usuário a ser excluído
     * @return O objeto do usuário excluído
     */
    @Override
    public UsuarioModel deleteUsuarioId(UsuarioModel usuarioModel) {
        usuarioRepository.delete(usuarioModel);
        return usuarioModel;
    }

    /**
     * Salva um novo usuário no banco de dados.
     *
     * @param recordDtoUsuario Dados do usuário a serem salvos, recebidos como DTO
     * @return O usuário salvo no banco de dados
     */
    @Override
    @Transactional
    public UsuarioModel saveUsuario(UsuarioRecordDto recordDtoUsuario) {
        var usuarioModel = new UsuarioModel(); // Instanciando o UsuarioModel
        // Copia os dados do DTO para o modelo
        BeanUtils.copyProperties(recordDtoUsuario, usuarioModel);
        // Preenche informações adicionais
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);
        usuarioModel.setTipoUsuario(TipoUsuario.USUARIO);
        usuarioModel.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Codificando a senha antes de salvar no banco de dados
        usuarioModel.setSenha(passwordEncoder.encode(usuarioModel.getSenha()));

        // Adiciona a role de usuário ao conjunto de roles do usuário
        usuarioModel.getRoles().add(roleService.findByRoleNome(RoleType.ROLE_USUARIO));

        // Salva o modelo no banco e retorna
        return usuarioRepository.save(usuarioModel);
    }

    /**
     * Verifica se já existe um usuário com o nome informado.
     *
     * @param nome Nome do usuário
     * @return True se o nome existir, false caso contrário
     */
    @Override
    public boolean existByNome(String nome) {
        return usuarioRepository.existsByNome(nome);
    }

    /**
     * Atualiza os dados do usuário.
     *
     * @param usuarioModel Modelo do usuário a ser atualizado
     * @param recordDtoUsuario Dados do usuário a serem atualizados, recebidos como DTO
     * @return O usuário atualizado
     */
    @Override
    public UsuarioModel updateUsuario(UsuarioModel usuarioModel, UsuarioRecordDto recordDtoUsuario) {
        // Atualiza o status, tipo, e data de atualização
        usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);
        usuarioModel.setTipoUsuario(TipoUsuario.USUARIO);
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Salva e retorna o usuário atualizado
        return usuarioRepository.save(usuarioModel);
    }

    /**
     * Atualiza a senha do usuário.
     *
     * @param usuarioRecordDto Dados da nova senha do usuário
     * @param usuarioModel Modelo do usuário a ser atualizado
     * @return O usuário com a senha atualizada
     */
    @Override
    public UsuarioModel updateSenha(UsuarioRecordDto usuarioRecordDto, UsuarioModel usuarioModel) {
        usuarioModel.setSenha(usuarioRecordDto.senha());
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));

        // Salva e retorna o usuário com a senha atualizada
        return usuarioRepository.save(usuarioModel);
    }

    /**
     * Recupera todos os usuários, com a possibilidade de filtragem e paginação.
     *
     * @param spec Filtros de busca aplicados
     * @param pageable Paginação dos resultados
     * @return Lista paginada de usuários
     */
    @Override
    public Page<UsuarioModel> findAll(Specification<UsuarioModel> spec, Pageable pageable) {
        return usuarioRepository.findAll(spec, pageable);
    }

    /**
     * Atualiza o status do usuário.
     *
     * @param usuarioModel Modelo do usuário a ser atualizado
     * @param usuarioRecordDto Dados do novo status do usuário
     * @return O usuário com o status atualizado
     */
    @Override
    public UsuarioModel updateStatusUsuario(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        usuarioModel.setStatusUsuario(usuarioRecordDto.statusUsuario());
        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public UsuarioModel updateImagem(UsuarioModel usuarioModel, UsuarioRecordDto usuarioRecordDto) {
        usuarioModel.setImagemUrl(usuarioRecordDto.imagemUrl());
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public UsuarioModel registrarInstrutor(UsuarioModel usuarioModel) {
        usuarioModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Recife")));
        usuarioModel.setTipoUsuario(TipoUsuario.INSTRUTOR);

        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public boolean existByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
