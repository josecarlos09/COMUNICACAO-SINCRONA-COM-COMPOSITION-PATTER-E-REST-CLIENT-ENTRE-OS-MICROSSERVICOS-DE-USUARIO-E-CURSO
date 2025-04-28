package plataforma_ead.usuario.configs.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe de configuração de segurança da aplicação.
 *
 * Define filtros, regras de autorização e autenticação para proteger as requisições.
 * Implementa segurança baseada em tokens JWT e define políticas de sessão sem estado.
 */
@Configuration
@EnableMethodSecurity // Habilita segurança baseada em métodos (roles)
@EnableWebSecurity // Habilita configurações de segurança do Spring Security
public class WebSecurityConfigurations {

    // Lista de endpoints que não precisam de autenticação
    private static final String [] LISTA_AUTENTICADOS ={
            // PARCE SEMPRE O CAMINHO COMPLETO, PARA EVITAR ERROS
            "/autenticacao/login",
            "/autenticacao/registro",
            "usuarios/**",
            "/usuario/**",
            "/instrutor/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
    };

    // Injeção de dependência via construtor
    private final UsuarioDetailsServiceImpl usuarioDetailsService;
    private final AutenticacaoEntryPointImpl autenticacaoEntryPoint;
    private final TokenJwt tokenJwt;
    private final AccessDeniedHandlerImpl accessDeniedHandler;

    //Construtor para injeção das dependências necessárias para a segurança.
    public WebSecurityConfigurations(UsuarioDetailsServiceImpl usuarioDetailsService, AutenticacaoEntryPointImpl authenticationEntryPoint, TokenJwt tokenJwt, AccessDeniedHandlerImpl accessDeniedHandler) {
        this.usuarioDetailsService = usuarioDetailsService;
        this.autenticacaoEntryPoint = authenticationEntryPoint;
        this.tokenJwt = tokenJwt;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    /**
     * Bean que retorna a instância do filtro de autenticação JWT.
     */
    @Bean
    public AutenticacaoJwtFilter autenticacaoJwtFilter() {
        return new AutenticacaoJwtFilter(tokenJwt, usuarioDetailsService);
    }

    /**
     * Configuração da cadeia de filtros de segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Configuração de tratamento de exceções
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(autenticacaoEntryPoint) // Define o EntryPoint para erros de autenticação
                        .accessDeniedHandler(accessDeniedHandler) // Define o handler para acessos negados
                )
                // Definição das regras de autorização
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll() // Permite acesso a páginas de erro
                        .requestMatchers(LISTA_AUTENTICADOS).permitAll() // Endpoints permitidos sem autenticação
                        .anyRequest().authenticated() // Todas as demais requisições precisam de autenticação
                )
                .formLogin(Customizer.withDefaults()) // Configura login padrão do Spring Security
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para permitir chamadas via API REST
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Define a política de sessão sem estado (JWT)
                );

        // Adiciona o filtro JWT antes do UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(autenticacaoJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * Configuração do gerenciador de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configuração do encoder de senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}