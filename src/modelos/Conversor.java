package modelos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Conversor {
    private String primerCodigoDeMoneda;
    private String segundoCodigoDeMoneda;
    private double valorAConvertir;
    private FileWriter historial;
    private List<List<String>> listaDeCodigos;
    private JsonObject jsonObject;

    public Conversor(String primeraMoneda, String segundaMoneda, double valor) throws IOException {
        this.primerCodigoDeMoneda = primeraMoneda;
        this.segundoCodigoDeMoneda = segundaMoneda;
        this.valorAConvertir = valor;
        this.historial = new FileWriter("Historial.txt", true);
    }
    public Conversor() throws IOException {
        this.historial = new FileWriter("Historial.txt", true);
    }

    public void setPrimerCodigoDeMoneda(String codigoDeMoneda){
        this.primerCodigoDeMoneda = codigoDeMoneda;
    }
    public void setSegundoCodigoDeMoneda(String codigoDeMoneda){
        this.segundoCodigoDeMoneda = codigoDeMoneda;
    }
    public void setValorAConvertir(double valor){ this.valorAConvertir = valor; }
    public String getPrimerCodigoDeMoneda(){
        return this.primerCodigoDeMoneda;
    }
    public String getSegundoCodigoDeMoneda(){
        return this.segundoCodigoDeMoneda;
    }
    public double getValorAConvertir(){
        return this.valorAConvertir;
    }

    private void conectarAPI(String direccion) throws IOException {
        // Making Request
        URL url = new URL(direccion);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        jsonObject = root.getAsJsonObject();
    }
    /*public double convertirMoneda() throws IOException {
        double conversion = -1.0;
        String direccion = "https://v6.exchangerate-api.com/v6/84cf4f0ca20cdd2ed2c8338d/pair/"+
                getPrimerCodigoDeMoneda()+"/"+getSegundoCodigoDeMoneda()+"/"+getValorAConvertir();

        conectarAPI(direccion);
        conversion = Double.valueOf(jsonObject.get("conversion_result").getAsString());

        historial.write(
                getFechaHora()+" | "+
                getValorAConvertir()+" ["+
                getPrimerCodigoDeMoneda()+"] ==>> "+
                conversion+" ["+
                getSegundoCodigoDeMoneda()+"]\n");
        historial.close();

        return conversion;
    }*/
    public double convertirMoneda(String primerCodigo, String segundoCodigo, double valor) throws IOException {
        double conversion = -1.0;
        String direccion = "https://v6.exchangerate-api.com/v6/84cf4f0ca20cdd2ed2c8338d/pair/"+
                primerCodigo+"/"+segundoCodigo+"/"+valor;

        conectarAPI(direccion);
        conversion = Double.valueOf(jsonObject.get("conversion_result").getAsString());

        historial.write(
                getFechaHora()+" | "+
                valor+" ["+
                primerCodigo+"] ==>> "+
                conversion+" ["+
                segundoCodigo+"]\n");
        historial.flush();
        return conversion;
    }
    public void mostrarCodigosDeMonedas() throws IOException {
        Type listType = new TypeToken<List<List<String>>>() {}.getType();
        String direccion = "https://v6.exchangerate-api.com/v6/84cf4f0ca20cdd2ed2c8338d/codes";
        conectarAPI(direccion);

        listaDeCodigos = new ArrayList<>(new Gson().fromJson(jsonObject.get("supported_codes"), listType));
        //lista.forEach((n) -> System.out.println(n));
        int contador = 1;
        for(List<String> item : listaDeCodigos){
            System.out.println(contador+" --> "+item);
            contador++;
        }
    }
    /*public double convertirOtrasMonedas(String primerCodigo, String segundoCodigo, double valor) throws IOException {
        setPrimerCodigoDeMoneda(primerCodigo);
        setSegundoCodigoDeMoneda(segundoCodigo);
        setValorAConvertir(valor);

        double resultado = convertirMoneda(primerCodigo, segundoCodigo, valor);
        System.out.println("Resultado: "+resultado);
        return resultado;
    }*/
    public void verHistorialDeConversiones() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Historial.txt"));
        String texto;
        int contador = 1;
        while ((texto = br.readLine()) != null){
            System.out.println(contador+" | "+texto);
            contador++;
        }
    }
    private String getFechaHora(){
        DateTimeFormatter formato = DateTimeFormatter
                //.ofPattern("EEEE dd 'de' MMMM 'de' yyyy 'a las' hh:mm:ss")
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(new Locale("es", "ES"));
        String fecha = LocalDateTime.now().format(formato);
        return fecha;
    }
    public void cerrarHistorial() throws IOException {
        historial.close();
    }
}
