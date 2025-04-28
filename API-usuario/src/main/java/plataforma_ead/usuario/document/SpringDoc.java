package plataforma_ead.usuario.document;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Classe de configuração para documentação da API utilizando SpringDoc OpenAPI.
 * Esta configuração define as informações gerais da API, incluindo título, descrição,
 * informações de contato e licença de uso.
 */
@Configuration
public class SpringDoc {

    /**
     * Configuração personalizada do OpenAPI.
     *
     * @return Uma instância de OpenAPI com informações da API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plataforma de Educação Online, área do usuário.") // Título da API
                        .description("""
                            Microserviço: API RESTful para Plataforma EAD de Usuários
                            Esta aplicação é dedicada ao gerenciamento de controle de acessos e autenticação 
                            para a API da plataforma de ensino a distância (EAD). Seu objetivo é centralizar e organizar 
                            as operações relacionadas à autenticação, autorização e gerenciamento de usuários, garantindo 
                            segurança e eficiência no acesso aos recursos da plataforma.
                        """.stripIndent())
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@plataforma-ead.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://plataforma-ead.com/api/licenca")));
    }
}

