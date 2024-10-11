package excepciones;

public class ConversionNoRealizadaException extends RuntimeException{
    private String mensaje;
    public ConversionNoRealizadaException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage(){
        return this.mensaje;
    }
}
