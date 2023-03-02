package data.types.attributes;

public class Attendance extends Enumerator {

    private static final String[] attendanceOptions = {"Present", "Absent", "Left"};

    public Attendance(String value) {
        super(attendanceOptions, value);
    }

    public Attendance(Enumerator e) {
        super(attendanceOptions, e.getCurrentValue());
    }

    public static String[] getAttendanceOptions() {return attendanceOptions;}


}
