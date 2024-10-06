package excepciones;

public class OpcionNoValidaException extends RuntimeException {
    private String mensaje;
    public OpcionNoValidaException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage(){
        return this.mensaje;
    }
}
