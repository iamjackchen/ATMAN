package data.types;

import data.types.attributes.Attendance;
import data.types.attributes.Sex;
import ui.components.menus.Session;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class AttendeeComparator implements Comparator {

    public int index;

    public AttendeeComparator(int index) {
        this.index = index;

    }

    @Override
    public int compare(Object o, Object t1) {

        if (o instanceof Attendee && t1 instanceof Attendee) {
            if ( Arrays.equals((((Attendee)o).getParameterTypes()), (((Attendee)t1).getParameterTypes()))) {
                if  (((Attendee)o).getParameterTypes()[index] == Integer.class) {
                    return ((Integer) (((Attendee)o).getData(index))).compareTo(((Integer) (((Attendee)t1).getData(index))));
                } else if  (((Attendee)o).getParameterTypes()[index] == Character.class) {
                    return ((Character) (((Attendee)o).getData(index))).compareTo(((Character) (((Attendee)t1).getData(index))));
                } else if  (((Attendee)o).getParameterTypes()[index] == Boolean.class) {
                    return ((Boolean) (((Attendee)o).getData(index))).compareTo(((Boolean) (((Attendee)t1).getData(index))));
                } else if  (((Attendee)o).getParameterTypes()[index] == String.class) {
                    return ((String) (((Attendee)o).getData(index))).compareTo(((String) (((Attendee)t1).getData(index))));
                } else if  (((Attendee)o).getParameterTypes()[index] == Double.class) {
                    return ((Double) (((Attendee)o).getData(index))).compareTo(((Double) (((Attendee)t1).getData(index))));
                } else if  (((Attendee)o).getParameterTypes()[index] == Sex.class) {
                    return ((Sex) (((Attendee)o).getData(index))).compareTo(((Sex) (((Attendee)t1).getData(index))));
                } if  (((Attendee)o).getParameterTypes()[index] == Attendance.class) {
                    return ((Attendance) (((Attendee)o).getData(index))).compareTo(((Attendance) (((Attendee)t1).getData(index))));
                }

                //Make sure to add an entry here for any custom data types you implement

            }
        }

        return 0;
    }


}
