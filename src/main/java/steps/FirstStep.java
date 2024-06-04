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

public class FirstStep extends JFrame {
    private JPanel FirstPanel;

    public FirstStep(List<Grammar> grammars) {
        setTitle("Primer paso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FirstPanel = new JPanel();
        FirstPanel.setLayout(new BoxLayout(FirstPanel, BoxLayout.Y_AXIS));

        //JLabel VarTerm = new JLabel("Gramatica inicial: \n");
        //FirstPanel.add(VarTerm);
        //FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Set<String> Var_Term = new HashSet<>();  // Set para almacenar las variables terminales

       
        for (Grammar grammar : grammars) {
            // JLabel valuesLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            // FirstPanel.add(valuesLabel);
            // FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Verificar si algún valor en la lista contiene solo letras minúsculas
            for (String value : grammar.getValues()) {
                if (value.chars().allMatch(Character::isLowerCase)) {
                    Var_Term.add(grammar.getName());
                    break;
                }
            }
        }

        boolean listChanged;
        do {
            listChanged = false;
            // Iteración para buscar valores que contengan los nombres de la lista
            for (Grammar grammar : grammars) {
                // Si la gramática ya está en la lista, la saltamos
                if (Var_Term.contains(grammar.getName())) {
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
                        if (!Var_Term.contains(uppercaseChar.toString())) {
                            allUppercaseInList = false;
                            break;
                        }
                    }

                    if (allUppercaseInList && !Var_Term.contains(grammar.getName())) {
                        Var_Term.add(grammar.getName());
                        listChanged = true; // Hubo un cambio en la lista
                        break;
                    }
                }
            }
        } while (listChanged); // Repetir hasta que no haya cambios en la lista

        // Encontrar las variables no terminales
        List<String> Var_NoTerm = new ArrayList<>();
        for (Grammar grammar : grammars) {
            if (!Var_Term.contains(grammar.getName())) {
                Var_NoTerm.add(grammar.getName());
            }
        }

        // Eliminar las gramáticas excluidas de la lista original
        grammars.removeIf(grammar -> Var_NoTerm.contains(grammar.getName()));

        // Eliminar los valores que contienen nombres excluidos
        for (Grammar grammar : grammars) {
            grammar.getValues().removeIf(value -> containsAnyExcluded(value, Var_NoTerm));
        }

        // Crear la nueva gramática resultante
        List<Grammar> newGrammars = new ArrayList<>(grammars);

        // Mostrar la nueva gramática en la ventana
        JLabel newGrammarLabel = new JLabel("\nResultado Variables no terminales:");
        FirstPanel.add(newGrammarLabel);
        FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  

        for (Grammar grammar : newGrammars) {
            JLabel newValuesLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            FirstPanel.add(newValuesLabel);
            FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        }

        //Mostraar los resultados de variables terminales y no terminales
        JLabel varTerm = new JLabel("\nVariables Terminales: " + String.join(", ", Var_Term));
        FirstPanel.add(varTerm);
        FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        JLabel VarNoTerm = new JLabel("\nVariables no Terminales: " + String.join(", ", Var_NoTerm));
        FirstPanel.add(VarNoTerm);
        FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  

        // Botón "Siguiente paso"
        JButton nextButton = new JButton("Siguiente paso");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SecondStep(newGrammars, new ArrayList<>(grammars)).setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });

        FirstPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        FirstPanel.add(nextButton);

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
    private boolean containsAnyExcluded(String value, List<String> Var_NoTerm) {
        for (String excluded : Var_NoTerm) {
            if (value.contains(excluded)) {
                return true;
            }
        }
        return false;
    }
}
