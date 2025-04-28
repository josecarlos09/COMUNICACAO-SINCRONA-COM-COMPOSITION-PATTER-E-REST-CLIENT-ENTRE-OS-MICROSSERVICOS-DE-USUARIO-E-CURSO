package plataforma_ead.usuario.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClienteConfig {
    @Bean
    public RestClient.Builder restClientBilder(){
        return RestClient.builder();
    }
}