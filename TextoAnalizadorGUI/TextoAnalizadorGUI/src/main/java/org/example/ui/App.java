package org.example.ui;

import org.example.TextAnalyzer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class App {

    private static final String SAMPLE = String.join(" ",
            "Cuando yo tenía seis años vi en un libro una magnífica lámina.",
            "Representaba una serpiente boa que se tragaba a una fiera.",
            "En el libro se decía: Las boas tragan a sus presas enteras, sin masticarlas.",
            "Después ya no pueden moverse y duermen durante los seis meses de su digestión.",
            "Reflexioné mucho entonces sobre las aventuras de la selva y, a mi vez, logré",
            "trazar con un lápiz de colores mi primer dibujo.",
            "Era una obra maestra que representaba una serpiente boa digiriendo un elefante.",
            "Mostré mi obra a las personas mayores y les pregunté si mi dibujo les asustaba.",
            "Me respondieron: ¿Por qué habría de asustar un sombrero?.",
            "Mi dibujo no representaba un sombrero.",
            "Representaba una serpiente boa que digiere un elefante.",
            "Es necesario explicar a los adultos muchas cosas, porque nunca comprenden nada por sí mismos."
    );

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Analizador de Texto - Front");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 720);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(16,16));
        root.setBorder(new EmptyBorder(16,16,16,16));

        // Text area panel
        JTextArea input = new JTextArea(SAMPLE, 10, 80);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(input);
        scroll.setBorder(BorderFactory.createTitledBorder("Texto de entrada"));

        // Buttons panel
        JButton btnProcesar = new JButton("Procesar");
        JButton btnLimpiar = new JButton("Limpiar");
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        actions.add(btnProcesar);
        actions.add(btnLimpiar);

        JPanel left = new JPanel(new BorderLayout(8,8));
        left.add(scroll, BorderLayout.CENTER);
        left.add(actions, BorderLayout.SOUTH);

        // Right panel: results
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JTextArea resumen = new JTextArea(8, 40);
        resumen.setEditable(false);
        resumen.setLineWrap(true);
        resumen.setWrapStyleWord(true);
        JScrollPane resumenScroll = new JScrollPane(resumen);
        resumenScroll.setBorder(BorderFactory.createTitledBorder("Resumen"));

        // Table for Top N
        String[] cols = {"Palabra", "Frecuencia"};
        DefaultTableModel modelTop = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tablaTop = new JTable(modelTop);
        JScrollPane spTop = new JScrollPane(tablaTop);
        spTop.setBorder(BorderFactory.createTitledBorder("Top 10 palabras"));

        // Frequency map button
        JButton btnVerMapa = new JButton("Ver mapa de frecuencias completo");
        btnVerMapa.setEnabled(false);

        // Search controls
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JTextField txtBuscar = new JTextField(16);
        JButton btnBuscar = new JButton("Buscar frecuencia");
        JLabel lblResultado = new JLabel("—");
        searchPanel.add(new JLabel("Palabra:"));
        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        searchPanel.add(lblResultado);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Consulta de palabra"));

        right.add(resumenScroll);
        right.add(Box.createVerticalStrut(8));
        right.add(spTop);
        right.add(Box.createVerticalStrut(8));
        right.add(searchPanel);
        right.add(Box.createVerticalStrut(8));
        right.add(btnVerMapa);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(520);
        root.add(split, BorderLayout.CENTER);

        frame.setContentPane(root);
        frame.setVisible(true);

        final TextAnalyzer[] analyzerRef = new TextAnalyzer[1];

        btnLimpiar.addActionListener(e -> {
            input.setText("");
            resumen.setText("");
            modelTop.setRowCount(0);
            lblResultado.setText("—");
            btnVerMapa.setEnabled(false);
        });

        btnProcesar.addActionListener(e -> {
            try {
                TextAnalyzer analyzer = new TextAnalyzer(input.getText());
                analyzer.process();
                analyzerRef[0] = analyzer;

                // Resumen
                StringBuilder sb = new StringBuilder();
                sb.append("Total de palabras: ").append(analyzer.getWords().size()).append("\n\n");
                sb.append("Longitud promedio de palabras: ")
                        .append(String.format("%.2f", analyzer.avgWordLength())).append("\n");
                sb.append("Palabras distintas: ").append(analyzer.distinctCount()).append("\n");
                sb.append("Número de frases: ").append(analyzer.sentencesCount()).append("\n");
                sb.append("La palabra más frecuente representa el ")
                        .append(String.format("%.2f", analyzer.topWordSharePercent()))
                        .append("% del texto.").append("\n\n");
                sb.append("Texto completo limpio: ").append(analyzer.getCleanText());
                resumen.setText(sb.toString());

                // Top 10
                modelTop.setRowCount(0);
                List<Map.Entry<String,Integer>> top = analyzer.topN(10);
                for (Map.Entry<String,Integer> en : top) {
                    modelTop.addRow(new Object[]{en.getKey(), en.getValue()});
                }

                btnVerMapa.setEnabled(true);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBuscar.addActionListener(e -> {
            TextAnalyzer analyzer = analyzerRef[0];
            if (analyzer == null) {
                JOptionPane.showMessageDialog(frame, "Primero procesa el texto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String word = txtBuscar.getText().trim();
            try {
                int f = analyzer.frequencyOf(word);
                lblResultado.setText("'" + word.toLowerCase() + "' aparece " + f + " veces.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Búsqueda inválida", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnVerMapa.addActionListener(e -> {
            TextAnalyzer analyzer = analyzerRef[0];
            if (analyzer == null) return;
            JTextArea area = new JTextArea(20, 40);
            StringBuilder sb = new StringBuilder();
            analyzer.getFrequencyMap().entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .forEach(en -> sb.append(en.getKey()).append(" : ").append(en.getValue()).append("\n"));
            area.setText(sb.toString());
            area.setEditable(false);
            JScrollPane sp = new JScrollPane(area);
            sp.setPreferredSize(new Dimension(480, 420));
            JOptionPane.showMessageDialog(frame, sp, "Mapa de Frecuencias", JOptionPane.PLAIN_MESSAGE);
        });
    }
}
