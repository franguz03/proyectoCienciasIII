package steps;

import data.Grammar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FourStep extends JFrame {
    private JPanel FourPanel;

    public FourStep(List<Grammar> grammars, List<Grammar> originalGrammars) {
        setTitle("Cuarto paso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FourPanel = new JPanel();
        FourPanel.setLayout(new BoxLayout(FourPanel, BoxLayout.Y_AXIS));

        HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
        for (Grammar grammar : grammars) {
            ArrayList<String> filtered = filterLettersUppercase(grammar.getValues());
            filtered.add(grammar.getName());
            dictionary.put(grammar.getName(), filtered);
        }

        HashMap<String, ArrayList<String>> currentDict = transformation(dictionary);
        List<Grammar> newList = setNewGrammar(grammars, currentDict);

        // Mostrar la gramática resultante del tercer paso
        JLabel thirdStepLabel = new JLabel("Resultado Eliminacion de producciones unitarias:");
        FourPanel.add(thirdStepLabel);
        FourPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        Grammar newGrammar = searchGrammarByName(newList, "S");
        if (newGrammar != null) {
            JLabel grammarLabel = new JLabel("\n" + newGrammar.getName() + " --> " + String.join(" | ", newGrammar.getValues()));
            FourPanel.add(grammarLabel);
            FourPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        for (Grammar grammar : newList) {
            if (!grammar.getName().equals("S")) {
                System.out.println(grammar.getName() + "\n" + grammar.getValues());
                JLabel grammarLabel2 = new JLabel(grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
                FourPanel.add(grammarLabel2);
                FourPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(FourPanel);
        add(scrollPane);

    //     // Botón "Regresar"
    //     JButton backButton = new JButton("Regresar");
    //     backButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             new ThirdStep(grammars, originalGrammars).setVisible(true);
    //             dispose();  // Cierra la ventana actual
    //         }
    //     });
    //     FourPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    //     FourPanel.add(backButton);
 }

    public static ArrayList<String> filterLettersUppercase(List<String> words) {
        ArrayList<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.length() == 1 && Character.isUpperCase(word.charAt(0))) {
                result.add(word);
            }
        }
        return result;
    }

    public static HashMap<String, ArrayList<String>> transformation(Map<String, ArrayList<String>> hashMap) {
        HashMap<String, ArrayList<String>> newDictionary = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> values = entry.getValue();
            ArrayList<String> unionList = new ArrayList<>(values);
            int index = 0;
            while (index < unionList.size()) {
                String code = unionList.get(index);
                ArrayList<String> newList = hashMap.get(code);
                if (newList != null && !newList.isEmpty()) {
                    unionList = Stream.concat(unionList.stream(), newList.stream())
                            .distinct()
                            .collect(Collectors.toCollection(ArrayList::new)); // Convertir el flujo a ArrayList
                }
                index++;
            }
            newDictionary.put(key, unionList);
        }
        return newDictionary;
    }

    public static Grammar searchGrammarByName(Collection<Grammar> grammarList, String name) {
        Optional<Grammar> grammarFound = grammarList.stream()
                .filter(grammar -> name.equals(grammar.getName()))
                .findFirst();

        return grammarFound.orElse(null);
    }

    public static List<Grammar> setNewGrammar(List<Grammar> prevStep, HashMap<String, ArrayList<String>> diccionario) {
        List<Grammar> newGrammar = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : diccionario.entrySet()) {
            String code = entry.getKey();
            ArrayList<String> names = entry.getValue();
            ArrayList<String> newValues = new ArrayList<>();
            for (String name : names) {
                Grammar currentObj = searchGrammarByName(prevStep, name);
                if (currentObj != null && currentObj.getValues().size() != 0) { // Verificar si la code existe en el HashMap
                    newValues = Stream.concat(newValues.stream(), currentObj.getValues().stream())
                            .distinct()
                            .collect(Collectors.toCollection(ArrayList::new)); // Convertir el flujo a ArrayList
                }
            }
            newValues.removeIf(p -> p.length() == 1 && Character.isUpperCase(p.charAt(0)));
            newGrammar.add(new Grammar(code, newValues));
        }
        return newGrammar;
    }
}
