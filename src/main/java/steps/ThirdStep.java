package steps;

import data.Grammar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThirdStep extends JFrame {
    private JPanel ThirdPanel;

    public ThirdStep(List<Grammar> grammars, List<Grammar> originalGrammars) {
        setTitle("Tercer paso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ThirdPanel = new JPanel();
        ThirdPanel.setLayout(new BoxLayout(ThirdPanel, BoxLayout.Y_AXIS));

        // Mostrar la gramática resultante del segundo paso
        JLabel thirdStepLabel = new JLabel("Resultado de Variables Alcanzables:");
        ThirdPanel.add(thirdStepLabel);
        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Grammar grammar : grammars) {
            JLabel grammarLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            ThirdPanel.add(grammarLabel);
            ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Identificar y agregar a Var_Anul las filas con valor "λ" y eliminar dicho valor
        Set<String> Var_Anul = new HashSet<>();
        List<String> AUX = new ArrayList<>();
        for (Grammar grammar : grammars) {
            if (grammar.getValues().contains("λ")) {
                Var_Anul.add(grammar.getName());
                AUX.add(grammar.getName());
                grammar.getValues().remove("λ");
            }
        }

        // Imprimir Var_Anul y AUX en consola para verificación
        System.out.println("Variables anulables: " + Var_Anul);
        System.out.println("AUX: " + AUX);

        boolean listChanged;
        do {
            listChanged = false;
            for (Grammar grammar : grammars) {
                if (Var_Anul.contains(grammar.getName())) {
                    continue;
                }
                for (String value : grammar.getValues()) {
                    boolean containsVarAnul = value.chars().anyMatch(c -> Var_Anul.contains(String.valueOf((char) c)));
                    boolean containsAux = value.chars().anyMatch(c -> AUX.contains(String.valueOf((char) c)));

                    if (containsVarAnul && containsAux) {
                        Var_Anul.add(grammar.getName());
                        listChanged = true;
                        break;
                    }
                }
            }
        } while (listChanged);

        // Imprimir Var_Anul actualizado en consola para verificación
        System.out.println("Variables anulables después de iteraciones: " + Var_Anul);

        // Agregar "λ" en la fila llamada "S"
        for (Grammar grammar : grammars) {
            if (grammar.getName().equals("S")) {
                grammar.addValue("λ");
            }
        }

        // Modificar los valores de acuerdo a las reglas especificadas
        for (Grammar grammar : grammars) {
            List<String> newValues = new ArrayList<>();
            for (String value : grammar.getValues()) {
                List<Integer> indices = new ArrayList<>();
                for (int i = 0; i < value.length(); i++) {
                    if (Var_Anul.contains(String.valueOf(value.charAt(i)))) {
                        indices.add(i);
                    }
                }
                if (indices.size() == 1) {
                    // Un solo caracter anulable
                    newValues.add(new StringBuilder(value).deleteCharAt(indices.get(0)).toString());
                } else if (indices.size() == 2) {
                    // Dos caracteres anulables
                    newValues.add(new StringBuilder(value).deleteCharAt(indices.get(0)).toString());
                    newValues.add(new StringBuilder(value).deleteCharAt(indices.get(1)).toString());
                } else if (indices.size() > 2) {
                    // Más de dos caracteres anulables
                    for (int index : indices) {
                        newValues.add(new StringBuilder(value).deleteCharAt(index).toString());
                    }
                    for (int i = 0; i < indices.size(); i++) {
                        for (int j = i + 1; j < indices.size(); j++) {
                            newValues.add(new StringBuilder(value)
                                    .deleteCharAt(indices.get(j))
                                    .deleteCharAt(indices.get(i))
                                    .toString());
                        }
                    }
                }
            }
            grammar.getValues().addAll(newValues);
        }

        // Mostrar la gramática resultante después de eliminar "λ"
        JLabel resultLabel = new JLabel("\nResultado Eliminación de las producciones λ:");
        ThirdPanel.add(resultLabel);
        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Grammar grammar : grammars) {
            JLabel updatedGrammarLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            ThirdPanel.add(updatedGrammarLabel);
            ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }


        // Botón "Regresar"
        JButton backButton = new JButton("Regresar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SecondStep(grammars, originalGrammars).setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });

        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        ThirdPanel.add(backButton);

        // Botón "Siguiente paso"
        JButton nextButton = new JButton("Siguiente paso");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FourStep(originalGrammars).setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
    

        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        ThirdPanel.add(nextButton);

        JScrollPane scrollPane = new JScrollPane(ThirdPanel);
        add(scrollPane);
    }
    
}
