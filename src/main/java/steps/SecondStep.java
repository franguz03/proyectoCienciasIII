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

public class SecondStep extends JFrame {
    private JPanel SecondPanel;
    private List<String> Var_ALC;

    public SecondStep(List<Grammar> grammars, List<Grammar> originalGrammars) {
        setTitle("Segundo paso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        SecondPanel = new JPanel();
        SecondPanel.setLayout(new BoxLayout(SecondPanel, BoxLayout.Y_AXIS));

        // // Mostrar el resultado del primer paso
        // JLabel firstStepLabel = new JLabel("Resultado de Variables Terminales:");
        // SecondPanel.add(firstStepLabel);
        // SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // for (Grammar grammar : originalGrammars) {
        //     JLabel originalValuesLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
        //     SecondPanel.add(originalValuesLabel);
        //     SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        // }

        // Inicializar la lista Var_ALC con el nombre de la primera fila de la gramática
        Var_ALC = new ArrayList<>();
        if (!grammars.isEmpty()) {
            Var_ALC.add(grammars.get(0).getName());

            // Recorrer los valores de la primera fila y agregar las letras mayúsculas que no están en Var_ALC
            Grammar firstGrammar = grammars.get(0);
            for (String value : firstGrammar.getValues()) {
                for (char c : value.toCharArray()) {
                    if (Character.isUpperCase(c) && !Var_ALC.contains(String.valueOf(c))) {
                        Var_ALC.add(String.valueOf(c));
                    }
                }
            }
        }

        // Bucle para buscar nuevas mayúsculas en las filas de Var_ALC
        boolean listChanged;
        do {
            listChanged = false;
            List<String> tempVarALC = new ArrayList<>(Var_ALC); // Copia de la lista actual
            for (String name : tempVarALC) {
                for (Grammar grammar : grammars) {
                    if (grammar.getName().equals(name)) {
                        for (String value : grammar.getValues()) {
                            for (char c : value.toCharArray()) {
                                if (Character.isUpperCase(c) && !Var_ALC.contains(String.valueOf(c))) {
                                    Var_ALC.add(String.valueOf(c));
                                    listChanged = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (listChanged);

        // Imprimir las variables alcanzables en consola
        System.out.println("Variales Alcanzables: " + String.join(", ", Var_ALC));

        // Encontrar las variables no alcanzables
        List<String> Var_NoALC = new ArrayList<>();
        for (Grammar grammar : grammars) {
            if (!Var_ALC.contains(grammar.getName())) {
                Var_NoALC.add(grammar.getName());
            }
        }

        // Eliminar las gramáticas excluidas de la lista original
        grammars.removeIf(grammar -> Var_NoALC.contains(grammar.getName()));

        // Eliminar los valores que contienen nombres excluidos
        for (Grammar grammar : grammars) {
            grammar.getValues().removeIf(value -> containsAnyExcluded(value, Var_NoALC));
        }

        // Crear la nueva gramática resultante
        List<Grammar> newGrammars = new ArrayList<>(grammars);

        // Mostrar la nueva gramática en la ventana
        JLabel newGrammarLabel = new JLabel("\nResultado de Variables Alcanzables:");
        SecondPanel.add(newGrammarLabel);
        SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));  

        for (Grammar grammar : newGrammars) {
            JLabel newValuesLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            SecondPanel.add(newValuesLabel);
            SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        }

        //Mostrar los resultados de varibales alcanzables y no alcanzables
        JLabel varALCLabel = new JLabel("\nVariables Alcanzables: " + String.join(", ", Var_ALC));
        SecondPanel.add(varALCLabel);
        SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        JLabel varNoALCLabel = new JLabel("\nVariables no Alcanzables: " + String.join(", ", Var_NoALC));
        SecondPanel.add(varNoALCLabel);
        SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));  

        // // Botón "Regresar"
        // JButton backButton = new JButton("Regresar");
        // backButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         new FirstStep(originalGrammars).setVisible(true);
        //         dispose();  // Cierra la ventana actual
        //     }
        // });

        // SecondPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        // SecondPanel.add(backButton);

        // Botón "Siguiente paso"
        JButton nextButton = new JButton("Siguiente paso");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new ThirdStep(newGrammars, originalGrammars).setVisible(true);
                new ThirdStep(newGrammars, originalGrammars).setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });

        SecondPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        SecondPanel.add(nextButton);

        JScrollPane scrollPane = new JScrollPane(SecondPanel);
        add(scrollPane);
    }

    // Método para verificar si una cadena contiene algún nombre excluido
    private boolean containsAnyExcluded(String value, List<String> Var_NoALC) {
        for (String excluded : Var_NoALC) {
            if (value.contains(excluded)) {
                return true;
            }
        }
        return false;
    }
}
