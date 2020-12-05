package data.types;

import data.AttendanceTableModel;

import java.util.Arrays;

public class Attendee {

    private String name;
    private int id;
    private int age;
    private char sex; //possible values: M/F for Male/Female, O for other/default for uninitialised value;
    private String nationality;
    private int grade;

    private String[] additionalParameterLabels;
    private Object[] additionalParameters;
    private Class[] additionalParameterDatatypes;

    public Attendee() {

        this.name = "";
        this.id = 99999;
        this.age = 0;
        this.sex = 'O';
        this.nationality = "";
        this.grade = 0;
        this.additionalParameterLabels = null;
        this.additionalParameterDatatypes = null;

    }

    public Attendee(String name, int id, int age, char sex, String nationality, int grade) {

        this.name = name;
        this.id = id;
        this.age = age;
        this.sex = sex;
        this.nationality = nationality;
        this.grade = grade;
        this.additionalParameterLabels = null;
        this.additionalParameterDatatypes = null;


    }

    public Attendee(String[] additionalParameterLabels, Class[] additionalParameterDatatypes, Object[] additionalParameters) {

        if (additionalParameterLabels.length != additionalParameterDatatypes.length && additionalParameterDatatypes.length != additionalParameters.length) System.exit(0);

        this.additionalParameterLabels = additionalParameterLabels;
        this.additionalParameterDatatypes = additionalParameterDatatypes;
        this.additionalParameters = additionalParameters;

        this.name = "";
        this.id = 99999;
        this.age = 0;
        this.sex = 'O';
        this.nationality = "";
        this.grade = 0;
    }

    public Attendee(String[] additionalParameterLabels, Class[] additionalParameterDatatypes, Object[] additionalParameters, String name, int id, int age, char sex, String nationality, int grade) {

        if (additionalParameterLabels.length != additionalParameterDatatypes.length && additionalParameterDatatypes.length != additionalParameters.length) System.exit(0);

        this.additionalParameterLabels = additionalParameterLabels;
        this.additionalParameterDatatypes = additionalParameterDatatypes;
        this.additionalParameters = additionalParameters;


        this.name = name;
        this.id = id;
        this.age = age;
        this.sex = sex;
        this.nationality = nationality;
        this.grade = grade;
    }


    public String[] getParameterList() {

        if (additionalParameterLabels != null) {
            String[] returnVal = Arrays.copyOf(new String[]{"Name", "ID", "Age", "Sex", "Nationality", "Grade"}, 6 + additionalParameterLabels.length);
            System.arraycopy(additionalParameterLabels, 0, returnVal, 6, additionalParameterLabels.length);
            return returnVal;
        } else return new String[]{"Name", "ID", "Age", "Sex", "Nationality", "Grade"};

    }

    public Class[] getParameterDatatypes() {

        if (additionalParameterDatatypes != null) {
            Class[] returnVal = Arrays.copyOf(new Class[]{String.class, Integer.class, Integer.class, Character.class, String.class, Integer.class}, 6 + additionalParameterDatatypes.length);
            System.arraycopy(additionalParameterDatatypes, 0, returnVal, 6, additionalParameterDatatypes.length);
            return returnVal;
        } else return new Class[]{String.class, Integer.class, Integer.class, Character.class, String.class, Integer.class};

    }

    public Object getData(int dataID) {





    }





}
