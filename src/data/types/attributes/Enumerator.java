package data.types.attributes;

import java.awt.*;
import java.util.ArrayList;

public class Enumerator {

    private ArrayList<String> enumeratorOptions;
    private String currentValue;
    private ArrayList<Color> displayColors;

    public Enumerator(String[] enumeratorOptions, String value) {

        this.enumeratorOptions = new ArrayList<String>();

        for (String e : enumeratorOptions) {
            this.enumeratorOptions.add(e);
        }

        this.displayColors = null;

        if (this.enumeratorOptions.contains(value)) {
            this.currentValue = value;
        } else { System.out.println("Enumerator initialisation value not in provided options"); System.exit(0); }

    }

    public String getCurrentValue() { return currentValue; }

    public void setCurrentValue(String newValue) throws Exception {

        if (this.enumeratorOptions.contains(newValue)) {
            this.currentValue = newValue;
        } else { throw new Exception("Error: attempted to set enumerator to nonexistent option"); }

    }

    public void addOption(String newOption) { this.enumeratorOptions.add(newOption); }

    public void removeOption(String target) throws Exception {

        if (this.enumeratorOptions.contains(target)) {
            this.enumeratorOptions.remove(target);
        } else { throw new Exception("Error: attempted to remove nonexistent option"); }

    }

    public String toString() {
        return this.currentValue;
    }


}
