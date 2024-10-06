package modelos;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Conversor {
    private final String CODIGO_MONEDAS = "https://v6.exchangerate-api.com/v6/84cf4f0ca20cdd2ed2c8338d/codes";
    private String direccion = "https://v6.exchangerate-api.com/v6/84cf4f0ca20cdd2ed2c8338d/pair/";
    private JsonObject jsonObject;

    public Conversor(String primeraMoneda, String segundaMoneda, double monto) throws IOException {
        direccion += primeraMoneda+"/"+segundaMoneda+"/"+monto;
        conectarAPI();
    }

    public Conversor() throws IOException {
        direccion = CODIGO_MONEDAS;
        conectarAPI();
    }

    private void conectarAPI() throws IOException {
        try{
            // Making Request
            URL url = new URL(direccion+"hola");
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            jsonObject = root.getAsJsonObject();
        }catch (IllegalArgumentException e){
            System.out.println("Error en la URL, verifique la dirección.");
        }catch (MalformedURLException e){
            System.out.println("[ERROR] No se ha ingresado una dirección válida.");
        }catch (FileNotFoundException e){
            System.out.println("[ERROR] No se ha encontrado ningún resulado.");
        }
    }

    public void convertirMoneda() throws IOException {
        // Accessing object
        String req_result = jsonObject.get("conversion_result").getAsString();
        System.out.println("Conversión: "+req_result);
    }

    public void mostrarCodigosDeMonedas(){
        Type listType = new TypeToken<List<List<String>>>() {}.getType();
        List<List<String>> lista = new ArrayList<>(new Gson().fromJson(jsonObject.get("supported_codes"), listType));
        lista.forEach((n) -> System.out.println(n));
    }
}
