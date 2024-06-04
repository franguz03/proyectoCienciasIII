package steps;

import data.Grammar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FourStep extends JFrame {
    private JPanel FirstPanel;

    public FourStep(List<Grammar> grammars) {
        HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
        for (Grammar grammar : grammars) {

            ArrayList<String> filtered = filterLettersUppercase(grammar.getValues());
            filtered.add(grammar.getName());
            dictionary.put(grammar.getName(), filtered);
        }
        HashMap<String, ArrayList<String>> currentDict = transformation(dictionary);
        List<Grammar> newList = setNewGrammar(grammars, currentDict);

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
                if (newList.size() != 0) {
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
                if (currentObj.getValues().size() != 0) { // Verificar si la code existe en el HashMap
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

    // public static void main(String[] args) {

    //     HashMap<String, ArrayList<String>> diccionario = new HashMap<>();
    //     // Aquí necesitas crear una lista de grammars
    //     List<Grammar> grammars = new ArrayList<>();
    //     ArrayList<String> valores = new ArrayList<>();
    //     valores.add("ACA");
    //     valores.add("CA");
    //     valores.add("AA");
    //     valores.add("A");
    //     valores.add("C");
    //     ArrayList<String> valores2 = new ArrayList<>();
    //     valores2.add("aAa");
    //     valores2.add("aa");
    //     valores2.add("B");
    //     valores2.add("C");

    //     ArrayList<String> valores3 = new ArrayList<>();
    //     valores3.add("cC");
    //     valores3.add("D");
    //     valores3.add("C");
    //     ArrayList<String> valores4 = new ArrayList<>();
    //     valores4.add("bC");
    //     ArrayList<String> valores5 = new ArrayList<>();
    //     valores5.add("aA");

    //     grammars.add(new Grammar("S", valores));
    //     grammars.add(new Grammar("A", valores2));
    //     grammars.add(new Grammar("B", valores3));
    //     grammars.add(new Grammar("C", valores4));
    //     grammars.add(new Grammar("D", valores5));

    //     for (Grammar grammar : grammars) {

    //         ArrayList<String> filtrados1 = filterLettersUppercase(grammar.getValues());
    //         filtrados1.add(grammar.getName());
    //         diccionario.put(grammar.getName(), filtrados1);
    //     }
    //     HashMap<String, ArrayList<String>> diccionarioact = transformation(diccionario);
    //     List<Grammar> newList = setNewGrammar(grammars, diccionarioact);
    //     for (Grammar grammar : newList) {
    //         System.out.println(grammar.getName());
    //         System.out.println(grammar.getValues());
    //     }

    // }
}
