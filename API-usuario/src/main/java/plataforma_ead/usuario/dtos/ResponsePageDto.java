package plataforma_ead.usuario.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Classe genérica que representa uma página de resposta vinda de outra API REST.
 * Esta classe estende PageImpl do Spring Data e permite a desserialização de objetos
 * paginados personalizados que seguem o formato da API do serviço de Cursos.
 *
 * @param <T> Tipo de objeto da lista de conteúdo.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePageDto<T> extends PageImpl<T> {

    // Metadados da página (tamanho, total de elementos, página atual, etc.)
    private final PageMetadata page;

    /**
     * Construtor da classe ResponsePageDto.
     * Utiliza o @JsonCreator para mapear corretamente os dados vindos do JSON.
     *
     * @param content Lista de elementos da página.
     * @param page Objeto com os metadados da página.
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePageDto(@JsonProperty("content") List<T> content,
                           @JsonProperty("page") PageMetadata page) {
        // Constrói a página usando os dados recebidos
        super(content, PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
        this.page = page;
    }

    /**
     * Retorna os metadados da página.
     *
     * @return PageMetadata com as informações de paginação.
     */
    public PageMetadata getPage() {
        return page;
    }

    /**
     * Classe interna que representa os metadados de uma página,
     * como definidos na resposta da API de cursos.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageMetadata {
        private final int size;
        private final long totalElements;
        private final int totalPages;
        private final int number;

        /**
         * Construtor de PageMetadata.
         *
         * @param size Tamanho da página.
         * @param totalElements Total de elementos disponíveis.
         * @param totalPages Total de páginas.
         * @param number Número da página atual.
         */
        @JsonCreator
        public PageMetadata(@JsonProperty("size") int size,
                            @JsonProperty("totalElements") long totalElements,
                            @JsonProperty("totalPages") int totalPages,
                            @JsonProperty("number") int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getNumber() {
            return number;
        }
    }
}