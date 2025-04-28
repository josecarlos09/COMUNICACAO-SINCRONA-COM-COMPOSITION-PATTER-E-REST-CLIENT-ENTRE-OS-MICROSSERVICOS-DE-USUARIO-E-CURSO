package plataforma_ead.usuario.exceptions;

public class NotFoundException extends RuntimeException{
    // Construtor da classe NotFoundException que retorna uma mensagem personalizada
    public NotFoundException(String message) {
        super(message);
    }
}
