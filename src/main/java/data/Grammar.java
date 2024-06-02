package data;

import java.util.ArrayList;

public class Grammar {
    // Atributos
    private String name;
    private ArrayList<String> values;

    // Constructor
    public Grammar(String name, ArrayList<String> values) {
        this.name = name;
        this.values = values;
    }

    // Métodos para obtener el name
    public String getName() {
        return name;
    }

    // Métodos para establecer el name
    public void setName(String name) {
        this.name = name;
    }

    // Métodos para obtener el ArrayList values
    public ArrayList<String> getValues() {
        return values;
    }

    // Métodos para agregar un valor al ArrayList values
    public void addValue(String value) {
        this.values.add(value);
    }

    // Métodos para establecer el ArrayList values
    public void setValues(ArrayList<String> values) {
        this.values = values;
    }
}
