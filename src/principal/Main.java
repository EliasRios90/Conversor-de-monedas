package principal;

import com.google.gson.*;
import excepciones.OpcionNoValidaException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        boolean salir = false;
        double monto = 0.0;
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        //
        while(!salir){
            try{
                System.out.println("\n*****************************************************");
                System.out.println("Bienvenidos/as al Conversor de Monedas\n");
                System.out.println("1) Dólar =>> Peso Argentino");
                System.out.println("2) Peso Argentino =>> Dólar");
                System.out.println("3) Dólar =>> Real Brasileño");
                System.out.println("4) Real Brasileño =>> Dólar");
                System.out.println("5) Dólar =>> Peso Colombiano");
                System.out.println("6) Peso Colombinao =>> Dólar");
                System.out.println("7) Salir");
                System.out.println("Elija una opción válida: ");
                System.out.println("*****************************************************");
                int opcion = lectura.nextInt();
                String compararcion = "";


                switch (opcion){
                    case 1: compararcion = "USD/ARS"; break;
                    case 2: compararcion = "ARS/USD"; break;
                    case 3: compararcion = "USD/BRL"; break;
                    case 4: compararcion = "BRL/USD"; break;
                    case 5: compararcion = "USD/COP"; break;
                    case 6: compararcion = "COP/USD"; break;
                    case 7: salir = true; break;
                    default: throw new OpcionNoValidaException("[ERROR] Debe ingresar una opción válida.\nIntentelo otra vez.");
                }
                if(salir){
                    break;
                }else{
                    System.out.println("Ingrese el monto que desea convertir: ");
                    monto = lectura.nextDouble();
                }
                String direccion = "https://v6.exchangerate-api.com/v6/84cf4f0ca20cdd2ed2c8338d/pair/"+compararcion+"/"+monto;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);

                JsonObject jo = new Gson().fromJson(json, JsonObject.class);
                System.out.println(jo.get("conversion_rate"));
                String primeraMoneda = compararcion.split("/")[0];
                String segundaMoneda = compararcion.split("/")[1];
                System.out.println("El valor "+monto+" ["+primeraMoneda+"] corresponde al valor final de =>> "+jo.get("conversion_result")+" ["+segundaMoneda+"]");



            }catch (IllegalArgumentException e){
                System.out.println("Error en la URI, verifique la dirección.");
            }catch (InputMismatchException e){
                System.out.println("[ERROR] Debe ingresar un número.");
                lectura.nextLine(); // limpia el buffer
            }catch (OpcionNoValidaException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Programa finalizado!!!");
    }
}
