# TextoAnalizadorGUI

Proyecto Maven con interfaz Swing que implementa un "front" para tu analizador de texto.

## Requisitos
- Java 17+
- Maven 3.8+

## Ejecutar en IntelliJ IDEA
1. **File > New > Project from Existing Sources...** y selecciona la carpeta `TextoAnalizadorGUI`.
2. IntelliJ detectará Maven automáticamente.
3. Para correr:
   - Opción A: Botón de *Play* en la clase `org.example.ui.App` (clic derecho > Run App.main()).
   - Opción B: Por Maven (View > Tool Windows > Maven > Plugins > exec > `exec:java`).

## Ejecutar por consola
```bash
mvn -q -e -DskipTests package
mvn exec:java -Dexec.mainClass="org.example.ui.App"
```

## ¿Qué hace?
- Limpia el texto (mantiene letras y espacios, incluyendo tildes).
- Muestra:
  - total de palabras,
  - longitud promedio,
  - palabras distintas,
  - número de frases,
  - porcentaje de la palabra más frecuente,
  - texto limpio completo.
- Top 10 palabras más frecuentes en una tabla.
- Búsqueda de frecuencia de una palabra específica.
- Mapa de frecuencias completo en un cuadro de diálogo.

## Notas
El texto de ejemplo es el mismo que usabas. Puedes pegar otro texto en el área y volver a **Procesar**.
