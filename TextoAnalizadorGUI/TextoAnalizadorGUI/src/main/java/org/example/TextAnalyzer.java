package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class TextAnalyzer {
    private final String rawText;
    private String cleanText;
    private List<String> words;
    private Map<String, Integer> freq;

    public TextAnalyzer(String text) {
        this.rawText = text == null ? "" : text;
    }

    public void process() {
        cleanText = rawText.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ ]", " ");
        if (cleanText.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto está vacío o solo contiene espacios.");
        }
        String[] arr = cleanText.trim().split("\s+");
        words = Arrays.stream(arr).map(String::toLowerCase).collect(Collectors.toList());
        freq = words.stream().collect(Collectors.toMap(w -> w, w -> 1, Integer::sum, LinkedHashMap::new));
    }

    public List<String> getWords() { return words; }
    public Map<String, Integer> getFrequencyMap() { return freq; }
    public String getCleanText() { return cleanText; }

    public double avgWordLength() {
        return words.stream().mapToInt(String::length).average().orElse(0.0);
    }

    public int distinctCount() { return freq.size(); }

    public int sentencesCount() {
        // Contamos frases del texto original usando puntos. Para robustez, contamos en el raw.
        String[] sentences = rawText.split("\\.");
        int c = 0;
        for (String s: sentences) {
            if (s.trim().length() > 0) c++;
        }
        return c;
    }

    public double topWordSharePercent() {
        int max = freq.values().stream().max(Integer::compareTo).orElse(0);
        if (words.isEmpty()) return 0.0;
        return (max * 100.0) / words.size();
    }

    public List<Map.Entry<String,Integer>> topN(int n) {
        return freq.entrySet().stream()
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public int frequencyOf(String word) {
        if (word == null) return 0;
        String w = word.toLowerCase();
        if (!w.matches("[a-záéíóúñ]+")) {
            throw new InputMismatchException("La búsqueda no es válida (use solo letras sin espacios).");
        }
        return freq.getOrDefault(w, 0);
    }
}
