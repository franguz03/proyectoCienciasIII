package steps;

import data.Grammar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FourStep extends JFrame {
    private JPanel FirstPanel;

    public FourStep(List<Grammar> grammars) {
        for (Grammar grammar : grammars) {
            grammar.getValues();
        }

    }

    public static ArrayList<String> filtrarLetrasMayusculas(List<String> palabras) {
        ArrayList<String> resultado = new ArrayList<>();
        for (String palabra : palabras) {
            if (palabra.length() == 1 && Character.isUpperCase(palabra.charAt(0))) {
                resultado.add(palabra);
            }
        }
        return resultado;
    }
    public static void transformacion(Map<String, ArrayList<String>> hashMap) {
        for (Map.Entry<String, ArrayList<String>> entry : hashMap.entrySet()) {
            String key=entry.getKey();
            ArrayList<String> values =entry.getValue();
            ArrayList<String> listaUnion = new ArrayList<>(values);
            int indice=0;
            System.out.println("gramatica "+key);
            System.out.println("lista union antes"+listaUnion);
            while(indice<listaUnion.size()){                
                String clave = listaUnion.get(indice);
                ArrayList<String> nuevalista=hashMap.get(clave);
                if (nuevalista.size()!=0) { // Verificar si la clave existe en el HashMap
                    listaUnion = Stream.concat(listaUnion.stream(), nuevalista.stream())
                                        .distinct()
                                        .collect(Collectors.toCollection(ArrayList::new)); // Convertir el flujo a ArrayList
                }
                
                indice++;
            }
            System.out.println("lista union despues"+listaUnion);
        }
    }
    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> diccionario = new HashMap<>();
        // Aquí necesitas crear una lista de grammars
        List<Grammar> grammars = new ArrayList<>();
        ArrayList<String> valores = new ArrayList<>();
        valores.add("ACA");
        valores.add("CA");
        valores.add("AA");
        valores.add("A");
        valores.add("C");
        ArrayList<String> valores2 = new ArrayList<>();
        valores2.add("aAa");
        valores2.add("aa");
        valores2.add("B");
        valores2.add("C");

        ArrayList<String> valores3 = new ArrayList<>();
        valores3.add("cC");
        valores3.add("D");
        valores3.add("C");
        ArrayList<String> valores4 = new ArrayList<>();
        valores4.add("bC");
        ArrayList<String> valores5 = new ArrayList<>();
        valores5.add("aA");
        

        grammars.add(new Grammar("S", valores));
        grammars.add(new Grammar("A", valores2));
        grammars.add(new Grammar("B", valores3));
        grammars.add(new Grammar("C", valores4));
        grammars.add(new Grammar("D", valores5));

        for (Grammar grammar : grammars) {

            ArrayList<String> filtrados1 = filtrarLetrasMayusculas(grammar.getValues());
            filtrados1.add(grammar.getName());
            diccionario.put(grammar.getName(), filtrados1);
        }
        for (Map.Entry<String, ArrayList<String>> entry : diccionario.entrySet()) {
            System.out.println("Clave: " + entry.getKey());
            System.out.println("Valores: " + entry.getValue());
        }

        // Crear una instancia de FourStep y pasarle la lista de grammars
        // FourStep ventana = new FourStep(grammars);

        transformacion(diccionario);
    }
}
