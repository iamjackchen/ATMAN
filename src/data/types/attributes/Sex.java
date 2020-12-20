package data.types.attributes;

import java.util.ArrayList;

public class Sex extends Enumerator {

    private static final String[] sexOptions = {"Male", "Female", "Other"};

    public Sex(String value) {
        super(sexOptions, value);
    }

    public Sex(Enumerator e) {
        super(sexOptions, e.getCurrentValue());
    }

    public static String[] getSexOptions() {return sexOptions;}

}
