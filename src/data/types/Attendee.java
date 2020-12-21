package data.types;

import data.types.attributes.Attendance;
import data.types.attributes.Sex;


import java.util.Arrays;

public class Attendee {

    private String[] parameterList;
    private Object[] parameters;
    private Class[] parameterTypes;

    private String QRData = null;
    public String getQRContents() {return QRData;}
    public void setQRData(String QRData) {this.QRData = QRData;}

    private final String[] defaultParameterList = {"Select", "Family Name", "Given Name", "ID", "Age", "Sex", "Nationality", "Grade", "Status"};
    private final Class[] defaultParameterTypes = {Boolean.class, String.class, String.class, Integer.class, Integer.class, Sex.class, String.class, Integer.class, Attendance.class};
    private final Object[] defaultParameters = {false, "", "", 99999, 0, new Sex("Other"), "", 1, new Attendance("Absent")};

    public Attendee() {

        parameterList = defaultParameterList;
        parameterTypes = defaultParameterTypes;
        parameters = defaultParameters;

    }

    public Attendee(String lastName, String firstName, int id, int age, Sex sex, String nationality, int grade, Attendance attendance) {

        parameterList = defaultParameterList;
        parameterTypes = defaultParameterTypes;
        parameters = new Object[]{false, lastName, firstName, id, age, sex, nationality, grade, attendance};


    }

    public Attendee(String[] customParameterList, Class[] customParameterTypes, Object[] customParameters, boolean overrideDefault) {

        if (customParameterList.length != customParameterTypes.length && customParameterTypes.length != customParameters.length) System.exit(0);

        if (overrideDefault) {

            this.parameterList = customParameterList;
            this.parameterTypes = customParameterTypes;
            this.parameters = customParameters;

        } else {

            this.parameterList = Arrays.copyOf(defaultParameterList, defaultParameterList.length+customParameterList.length);
            System.arraycopy(customParameterList, 0, this.parameterList, defaultParameterList.length, customParameterList.length);

            this.parameterTypes = Arrays.copyOf(defaultParameterTypes, defaultParameterTypes.length+customParameterTypes.length);
            System.arraycopy(customParameterTypes, 0, this.parameterTypes, defaultParameterTypes.length, customParameterTypes.length);

            this.parameters = Arrays.copyOf(defaultParameters, defaultParameters.length+customParameters.length);
            System.arraycopy(customParameters, 0, this.parameters, defaultParameters.length, customParameters.length);

        }

    }

    public Attendee(String e) {

        parameterList = defaultParameterList;
        parameterTypes = defaultParameterTypes;
        parameters = new Object[]{false, null, null, null, null, new Sex("Other"), null, null, new Attendance( "Absent")};

    }

    public String[] getParameterList() { return this.parameterList; }

    public Class[] getParameterTypes() { return this.parameterTypes; }

    public Object getData(int dataID) {

        try {

            return parameters[dataID];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void setData(int dataID, Object value) throws ClassCastException {

        if (value.getClass().isAssignableFrom(parameterTypes[dataID])) {
            parameters[dataID] = value;
        } else {
            throw new ClassCastException("Error: Tried to assign data of invalid type to attendee: " + value.toString() + "->" + parameterTypes[dataID]);
        }

    }

    public String toString() {

        String returnValue = "";
        for (int i = 0; i < parameters.length-1; i++) {
            returnValue += (parameters[i].toString()) + ",";
        }

        returnValue += parameters[parameters.length-1];

        return returnValue;

    }

}
