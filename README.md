# 📝 Análisis de Texto en Java

Este proyecto en **Java** realiza un procesamiento completo de un texto dado, aplicando técnicas básicas de análisis de lenguaje natural como limpieza, conteo, estadísticas y consultas interactivas.

---

## 🚀 Características principales

✔ **Limpieza del texto**:  
   - Elimina caracteres especiales, signos de puntuación y deja solo letras y espacios.  
   - Normaliza el texto a minúsculas.

✔ **Procesamiento y métricas**:  
   - Total de palabras.  
   - Muestra las palabras en una lista limpia.  
   - Calcula la **frecuencia de cada palabra** usando `HashMap`.  

✔ **Estadísticas avanzadas**:  
   - **Top N** palabras más frecuentes.  
   - Longitud promedio de las palabras.  
   - Cantidad de palabras distintas.  
   - Número de frases en el texto (usando Regex).  
   - Porcentaje de aparición de la palabra más frecuente respecto al total.  

✔ **Consultas interactivas**:  
   - Permite buscar una palabra ingresada por el usuario y muestra cuántas veces aparece.  
   - Valida la entrada para evitar errores.  

✔ **Manejo de errores** con `try-catch`:  
   - Texto vacío.  
   - Entrada inválida.  
   - Validaciones preventivas.  

---

## 📂 Estructura del Proyecto

src/
└── org.example/
    └── Main.java
README.md


---

## ⚙️ Tecnologías utilizadas

- **Lenguaje:** Java 17+  
- **Colecciones:** `ArrayList`, `HashMap`, `Stream API`  
- **Regex:** Limpieza y división del texto  
- **Scanner:** Entrada de usuario  
- **Paradigma:** Programación funcional y orientada a objetos  

---

Total de palabras: 105

Mapa de frecuencias: {serpiente=3, una=5, boa=3, ...}

Top 10 palabras más frecuentes:
una : 5
serpiente : 3
boa : 3
...

Longitud promedio de palabras: 5.23
Palabras distintas: 55
Número de frases: 10
La palabra más frecuente representa el 4.7% del texto.

Ingrese una palabra del texto que desea buscar: elefante
La palabra 'elefante' aparece 2 veces.

