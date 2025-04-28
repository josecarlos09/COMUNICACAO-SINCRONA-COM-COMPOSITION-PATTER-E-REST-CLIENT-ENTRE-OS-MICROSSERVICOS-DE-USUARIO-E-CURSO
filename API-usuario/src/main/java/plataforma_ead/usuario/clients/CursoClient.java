package plataforma_ead.usuario.clients;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import plataforma_ead.usuario.dtos.CursoRecordDto;
import plataforma_ead.usuario.dtos.ResponsePageDto;

import java.util.UUID;

/**
 * Componente responsável por consumir o serviço externo de Cursos
 * utilizando a API REST via RestClient do Spring Framework.
 *
 * A comunicação é feita de forma assíncrona, e os dados retornam
 * paginados com base nas informações passadas pelo Pageable.
 */
@Component
public class CursoClient {

    // Logger para registrar informações de debug e erro.
    Logger logger = LogManager.getLogger(CursoClient.class);

    // URL base da API de cursos, injetada via application.yml
    @Value("${plataforma_ead.api.url.curso}")
    String baseUrlCurso;

    // Instância do RestClient para comunicação com serviços externos
    final RestClient restClient;

    /**
     * Construtor do CursoClient.
     *
     * @param restClientBuilder Builder fornecido pelo Spring para criar a instância do RestClient.
     */
    public CursoClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    /**
     * Realiza uma chamada GET para buscar todos os cursos associados a um determinado usuário.
     * A resposta é paginada e pode ser ordenada conforme os parâmetros fornecidos pelo Pageable.
     *
     * @param usuarioId ID do usuário para filtrar os cursos.
     * @param pageable Objeto contendo informações de paginação e ordenação.
     * @return Uma página de objetos CursoRecordDto contendo os dados dos cursos.
     */
    public Page<CursoRecordDto> getAllCursosByUsuario(UUID usuarioId, Pageable pageable){
        // Monta a URL de requisição com os parâmetros de paginação e ordenação
        // A baseUrlCurso + "/cursos?usuarioId" + usuarioId (cursos é o caminho certinho do controller de Curso)
        String url = baseUrlCurso + "/cursos?usuarioId=" + usuarioId
                + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");

        logger.debug("Request {} ", url); // Log da URL da requisição

        try {
            // Executa a requisição GET e converte a resposta para um ResponsePageDto tipado com CursoRecordDto
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<CursoRecordDto>>() {});
        } catch (RestClientException e) {
            // Em caso de erro, registra no log e relança a exceção como RuntimeException
            logger.error("ERRO Request RestCliente: {}", e.getMessage());
            throw new RuntimeException("ERRO Request RestCliente: ", e);
        }
    }
}