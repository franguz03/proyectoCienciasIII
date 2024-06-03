package steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Crear el HashMap
        Map<String, ArrayList<String>> hashMap = new HashMap<>();
        ArrayList<String> valoresB = new ArrayList<>();
        valoresB.add("C");
        valoresB.add("B");
        hashMap.put("B", valoresB);

        ArrayList<String> valoresS = new ArrayList<>();
        valoresS.add("B");
        valoresS.add("C");
        valoresS.add("S");
        hashMap.put("S", valoresS);

        ArrayList<String> valoresC = new ArrayList<>();
        valoresC.add("D");
        valoresC.add("c");
        hashMap.put("c", valoresC);

        ArrayList<String> valoresD = new ArrayList<>();
        valoresD.add("D");
        hashMap.put("D", valoresD);

        // Transformación recursiva
        Main.transformacionRecursiva(hashMap);

        // Imprimir el HashMap transformado
        for (Map.Entry<String, ArrayList<String>> entry : hashMap.entrySet()) {
            String clave = entry.getKey();
            ArrayList<String> valores = entry.getValue();
            System.out.println("Clave: " + clave);
            System.out.println("Valores: " + valores);
        }
    }

    public static void transformacionRecursiva(Map<String, ArrayList<String>> hashMap) {
        Set<String> processedKeys = new HashSet<>(); // Para evitar procesar la misma clave varias veces
        for (Map.Entry<String, ArrayList<String>> entry : hashMap.entrySet()) {
            String clave = entry.getKey();
            ArrayList<String> valores = entry.getValue();
            ArrayList<String> nuevosValores = new ArrayList<>();

            for (String valor : valores) {
                if (!processedKeys.contains(valor)) { // Evita la recursión infinita
                    processedKeys.add(valor);
                    if (hashMap.containsKey(valor)) {
                        transformacionRecursiva(hashMap);
                        nuevosValores.addAll(hashMap.get(valor));
                    } else {
                        nuevosValores.add(valor);
                    }
                }
            }

            entry.setValue(nuevosValores);
        }
    }
}

