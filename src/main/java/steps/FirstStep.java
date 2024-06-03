package steps;

import data.Grammar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstStep extends JFrame {
    private JPanel FirstPanel;

    public FirstStep(List<Grammar> grammars) {
        setTitle("Primer paso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FirstPanel = new JPanel();
        FirstPanel.setLayout(new BoxLayout(FirstPanel, BoxLayout.Y_AXIS));

        JLabel VarTerm = new JLabel("Gramatica inicial: \n");
        FirstPanel.add(VarTerm);
        FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Set<String> grammarsWithLowercaseValues = new HashSet<>();  // Set para almacenar los nombres de gramáticas que cumplen la condición
        
        // Primera iteración para encontrar gramáticas con valores solo en minúsculas
        for (Grammar grammar : grammars) {
            JLabel valuesLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            FirstPanel.add(valuesLabel);
            FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add space between entries
            
            // Verificar si algún valor en la lista contiene solo letras minúsculas
            for (String value : grammar.getValues()) {
                if (value.chars().allMatch(Character::isLowerCase)) {
                    grammarsWithLowercaseValues.add(grammar.getName());
                    break;  // No necesitamos seguir verificando otros valores si ya encontramos uno que cumple la condición
                }
            }
        }

        boolean listChanged;
        do {
            listChanged = false;
            // Iteración para buscar valores que contengan los nombres de la lista
            for (Grammar grammar : grammars) {
                // Si la gramática ya está en la lista, la saltamos
                if (grammarsWithLowercaseValues.contains(grammar.getName())) {
                    continue;
                }
                
                for (String value : grammar.getValues()) {
                    // Obtener caracteres en mayúsculas del valor
                    List<Character> uppercaseChars = new ArrayList<>();
                    for (char c : value.toCharArray()) {
                        if (Character.isUpperCase(c)) {
                            uppercaseChars.add(c);
                        }
                    }

                    // Verificar si todos los caracteres en mayúsculas están en la lista
                    boolean allUppercaseInList = true;
                    for (Character uppercaseChar : uppercaseChars) {
                        if (!grammarsWithLowercaseValues.contains(uppercaseChar.toString())) {
                            allUppercaseInList = false;
                            break;
                        }
                    }

                    if (allUppercaseInList && !grammarsWithLowercaseValues.contains(grammar.getName())) {
                        grammarsWithLowercaseValues.add(grammar.getName());
                        listChanged = true; // Hubo un cambio en la lista
                        break;
                    }
                }
            }
        } while (listChanged); // Repetir hasta que no haya cambios en la lista

        // Encontrar las gramáticas excluidas
        List<String> excludedGrammars = new ArrayList<>();
        for (Grammar grammar : grammars) {
            if (!grammarsWithLowercaseValues.contains(grammar.getName())) {
                excludedGrammars.add(grammar.getName());
            }
        }

        // Eliminar las gramáticas excluidas de la lista original
        grammars.removeIf(grammar -> excludedGrammars.contains(grammar.getName()));

        // Eliminar los valores que contienen nombres excluidos
        for (Grammar grammar : grammars) {
            grammar.getValues().removeIf(value -> containsAnyExcluded(value, excludedGrammars));
        }

        // Crear la nueva gramática resultante
        List<Grammar> newGrammars = new ArrayList<>(grammars);

        // Mostrar la nueva gramática en la ventana
        JLabel newGrammarLabel = new JLabel("\nResultado Variables no terminales:");
        FirstPanel.add(newGrammarLabel);
        FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add space between entries

        for (Grammar grammar : newGrammars) {
            JLabel newValuesLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            FirstPanel.add(newValuesLabel);
            FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add space between entries
        }

        JScrollPane scrollPane = new JScrollPane(FirstPanel);
        add(scrollPane);
    }

    // Método para verificar si una cadena contiene exclusivamente minúsculas
    private boolean containsOnlyLowercase(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    // Método para verificar si una cadena contiene algún nombre excluido
    private boolean containsAnyExcluded(String value, List<String> excludedGrammars) {
        for (String excluded : excludedGrammars) {
            if (value.contains(excluded)) {
                return true;
            }
        }
        return false;
    }
}
