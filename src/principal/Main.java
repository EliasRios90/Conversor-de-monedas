package principal;

import excepciones.ConversionNoRealizadaException;
import excepciones.OpcionNoValidaException;
import modelos.Conversor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner lectura = new Scanner(System.in);
        double valorAConvertir = 0.0;
        double resultado = 0.0;
        String codigoPrimeraMoneda = "";
        String codigoSegundaMoneda = "";
        Conversor conversor = new Conversor();

        while(true){
            try{
                System.out.println("\n*****************************************************");
                System.out.println("Bienvenidos/as al Conversor de Monedas\n");
                System.out.println("1) Dólar =>> Peso Argentino");
                System.out.println("2) Peso Argentino =>> Dólar");
                System.out.println("3) Dólar =>> Real Brasileño");
                System.out.println("4) Real Brasileño =>> Dólar");
                System.out.println("5) Dólar =>> Peso Colombiano");
                System.out.println("6) Peso Colombinao =>> Dólar");
                System.out.println("7) Elegir otras Monedas");
                System.out.println("8) Ver historial");
                System.out.println("9) Salir");
                System.out.println("Elija una opción válida: ");
                System.out.println("*****************************************************");
                int opcion = lectura.nextInt();

                if(opcion==9){
                    conversor.cerrarHistorial();
                    break;
                }else if(opcion >=1 && opcion <= 6){
                    switch (opcion){
                        case 1:
                            codigoPrimeraMoneda = "USD";
                            codigoSegundaMoneda = "ARS";
                            break;
                        case 2:
                            codigoPrimeraMoneda = "ARS";
                            codigoSegundaMoneda = "USD";
                            break;
                        case 3:
                            codigoPrimeraMoneda = "USD";
                            codigoSegundaMoneda = "BRL";
                            break;
                        case 4:
                            codigoPrimeraMoneda = "BRL";
                            codigoSegundaMoneda = "USD";
                            break;
                        case 5:
                            codigoPrimeraMoneda = "USD";
                            codigoSegundaMoneda = "COP";
                            break;
                        default:
                            codigoPrimeraMoneda = "COP";
                            codigoSegundaMoneda = "USD";
                            break;
                    }

                    System.out.println("Ingrese el valor que desea convertir: ");
                    valorAConvertir = lectura.nextDouble();

                    resultado = conversor.convertirMoneda(codigoPrimeraMoneda, codigoSegundaMoneda, valorAConvertir);

                    if(resultado >= 0){
                        System.out.println("El valor de "+valorAConvertir+" ["+codigoPrimeraMoneda+"] corresponde al valor final de =>>> "+resultado+" ["+codigoSegundaMoneda+"]");
                    }else throw new ConversionNoRealizadaException("[ERROR] No se ha podido realizar la conversión.");

                }else if(opcion==7){
                    conversor.mostrarCodigosDeMonedas();

                    System.out.println("\nIngrese el primer código de moneda: ");
                    codigoPrimeraMoneda = lectura.next().toUpperCase();
                    System.out.println("Ingrese el segundo código de moneda: ");
                    codigoSegundaMoneda = lectura.next().toUpperCase();
                    System.out.println("Ingrese el valor que desea convertir: ");
                    valorAConvertir = lectura.nextDouble();

                    resultado = conversor.convertirMoneda(codigoPrimeraMoneda, codigoSegundaMoneda, valorAConvertir);
                    if(resultado >= 0){
                        System.out.println("El valor de "+valorAConvertir+" ["+codigoPrimeraMoneda+"] corresponde al valor final de =>>> "+resultado+" ["+codigoSegundaMoneda+"]");
                    }else throw new ConversionNoRealizadaException("[ERROR] No se ha podido realizar la conversión.");

                }else if(opcion==8){
                    conversor.verHistorialDeConversiones();
                }else throw new OpcionNoValidaException("[ERROR] Debe ingresar una opción válida.\nVuelva a intentarlo.");

            }catch (InputMismatchException e){
                System.out.println("[ERROR] Debe ingresar un número positivo.");
                lectura.nextLine(); // limpia el buffer
            }catch (OpcionNoValidaException e){
                System.out.println(e.getMessage());
            }catch (ConversionNoRealizadaException e){
                System.out.println(e.getMessage());
            }catch (IllegalArgumentException e){
                System.out.println("Error en la URL, verifique la dirección.");
            }catch (MalformedURLException e){
                System.out.println("[ERROR] No se ha ingresado una dirección válida.");
            }catch (FileNotFoundException e){
                System.out.println("[ERROR] No se ha podido obtener una respuesta.");
            }catch (IOException e){
                System.out.println("[ERROR] Ha ocurrido un problema inesperado.");
                System.out.println(e.getStackTrace());
                System.out.println(e.getMessage());
            }
        }

        System.out.println("¡Programa finalizado!");
    }
}
