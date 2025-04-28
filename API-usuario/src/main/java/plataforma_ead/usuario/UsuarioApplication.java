package plataforma_ead.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
public class UsuarioApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsuarioApplication.class, args);
	}
}