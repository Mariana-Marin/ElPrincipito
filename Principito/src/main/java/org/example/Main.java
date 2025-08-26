package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String texto = "Cuando yo tenía seis años vi en un libro una magnífica lámina. " +
                "Representaba una serpiente boa que se tragaba a una fiera. " +
                "En el libro se decía: Las boas tragan a sus presas enteras, sin masticarlas. " +
                "Después ya no pueden moverse y duermen durante los seis meses de su digestión. " +
                "Reflexioné mucho entonces sobre las aventuras de la selva y, a mi vez, logré" +
                "trazar con un lápiz de colores mi primer dibujo. " +
                "Era una obra maestra que representaba una serpiente boa digiriendo un elefante. " +
                "Mostré mi obra a las personas mayores y les pregunté si mi dibujo les asustaba. " +
                "Me respondieron: ¿Por qué habría de asustar un sombrero?. " +
                "Mi dibujo no representaba un sombrero. " +
                "Representaba una serpiente boa que digiere un elefante. " +
                "Es necesario explicar a los adultos muchas cosas, porque nunca comprenden nada por sí mismos.";

        try {

        //Limpio texto
        texto = texto.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]", "");

        // Examino excepcion que puede pasar despues de limpiar el texto, valido antes de guardar en Arraylist
        if  (texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto está vacio o solo contiene espacios.");

        }
        //Separo palabras, guardamos en un arreglo y despues en un ArrayList en minuscula
        String[] palabra = texto.split("\\s+");
            ArrayList<String> palabras = new ArrayList<>(
                    Arrays.stream(palabra).map(String::toLowerCase).toList()
            );

            // Comprobación parte 1: Procesamiento de texto
            System.out.println("Total de palabras: " + palabras.size()+ "\n");
            System.out.println("Texto completo limpio: " + palabras.subList(0, palabras.size()-1) + "\n");

            // Creo HashMap, defino clave-valor  y sumo palabras cada que se repitan
            HashMap<String, Integer> frecuenciaPalabras = new HashMap<>(palabras.stream().collect(Collectors.toMap(
                    p -> p, p -> 1, Integer::sum
            )));

            if (frecuenciaPalabras.isEmpty() || palabras.isEmpty()) {
                throw new NullPointerException("La lista de palabras no tiene nada.");
            }

            // Comprobacion parte 2: Mapa de frecuencias
            System.out.println("Mapa de frecuencias: " + frecuenciaPalabras );


            // Comprobación parte 3 : Estadisticas y consultas
            // Se usan las funciones
            System.out.println("\nTop 10 palabras más frecuentes:");
            mostrarTop(frecuenciaPalabras, 10);

            System.out.println("\nLongitud promedio de palabras: " + longitudPromedio(palabras));

            // Parte 4: Consultas Adicionales

            //Palabras distintas
            System.out.println("\nPalabras distintas: " + frecuenciaPalabras.size());

            //Frases en el texto
            String [] frases = texto.split("\\.");
            System.out.println("\nNumero de frases: " + frases.length);

            // Calcular el porcentaje de aparición de la palabra más frecuente frente al total
            int maxFrecuencia = frecuenciaPalabras.values().stream().max(Integer::compare).orElse(0);
            double porcentaje = (maxFrecuencia * 100.0) / palabras.size();
            System.out.println("\nLa palabra mas frecuente es igual al " + porcentaje + "% del texto.");

            //Frecuencia de una palabra en el texto
            Scanner sc = new Scanner(System.in);
            System.out.print("\nIngrese una palabra del texto que desea buscar: ");
            String busqueda = sc.nextLine().toLowerCase();

            if (!busqueda.matches("[a-záéíóúñ]+")) {
                throw new java.util.InputMismatchException("La busqueda no es valida");
            }

            int frecuencia = frecuenciaPalabras.getOrDefault(busqueda, 0);
            System.out.println("\nLa palabra '" + busqueda + " aparece " + frecuencia + " veces.");

        }
        catch (IllegalArgumentException e) {
            System.out.println( "Error: " + e.getMessage() );
        }
        catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("Error: " + e.getMessage() + "");
        }
        catch (Exception e) {
            System.out.println("Error con mensaje amigable :): " + e.getMessage());
        }


    }

    // Funcion Top 10 de palabras con mas frecuencia
    public static void mostrarTop(HashMap<String, Integer> frecuenciaPalabras, int n) {
        frecuenciaPalabras.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(n)
                .forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
    }

    // Funcion longitud promedio de palabras de la ArrayList Palabras
    public static double longitudPromedio(ArrayList<String> palabras) {
        return palabras.stream().mapToInt(String::length).average().orElse(0.0);

    }
}

