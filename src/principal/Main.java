package principal;

import modelos.Conversor;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner lectura = new Scanner(System.in);
        boolean salir = false;
        double monto = 0.0;
        /*
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


            }catch (InputMismatchException e){
                System.out.println("[ERROR] Debe ingresar un número.");
                lectura.nextLine(); // limpia el buffer
            }catch (OpcionNoValidaException e){
                System.out.println(e.getMessage());
            }
        }
        */

        //Conversor conversor = new Conversor("USD", "ARS", 1.0);
        //conversor.convertirMoneda();

        new Conversor().mostrarCodigosDeMonedas();
        System.out.println("Programa finalizado!!!");
    }
}
