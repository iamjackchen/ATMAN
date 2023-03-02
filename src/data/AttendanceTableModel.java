package data;

import data.types.Attendee;
import data.types.AttendeeComparator;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceTableModel extends AbstractTableModel {

    private List<Attendee> attendeeList;
    private List<Attendee> backupCopy = null;
    private String[] parameters;

    private Class[] parameterTypes;
    private boolean editable = false;

    private boolean needsKioskRefresh = false;
    public boolean needsKioskRefresh() {return needsKioskRefresh;}
    public void setNeedsKioskRefresh(boolean e) {this.needsKioskRefresh = e;}

    public AttendanceTableModel(List<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
        this.parameters = attendeeList.get(0).getParameterList();
        this.parameterTypes = attendeeList.get(0).getParameterTypes();
    }

    public void backup() {

        backupCopy = new ArrayList<Attendee>();
        for (Attendee a : attendeeList) {
            backupCopy.add(new Attendee(a));
        }

    }
    public void revertToBackup() { attendeeList = backupCopy; }
    public void removeBackup() {backupCopy = null;}

    public void setAttendeeList(List<Attendee> attendeeList) { this.attendeeList = attendeeList; }
    public List<Attendee> getAttendeeList() {return this.attendeeList;}

    public List<Attendee> getSortedAttendeeList(int index) {

        List<Attendee> returnVal = (List<Attendee>) ((ArrayList<Attendee>) attendeeList).clone();
        returnVal.sort(new AttendeeComparator(index));

        return returnVal;

    }


    private boolean isSaved = false;
    public boolean isSaved() {return isSaved;}
    public void setSaved(boolean e) {this.isSaved = e;}

    public void setEditable(boolean editable) { this.editable = editable; }

    public Attendee getAttendeeAt(int rowIndex) {return attendeeList.get(rowIndex);}

    @Override
    public int getRowCount() { return attendeeList.size(); }

    @Override
    public int getColumnCount() { return parameters.length; }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return parameterTypes[columnIndex]; }

    @Override
    public String getColumnName(int column) {return parameters[column];}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { return attendeeList.get(rowIndex).getData(columnIndex); }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {return (columnIndex == 0)?true:editable;}

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {

        try {
            attendeeList.get(rowIndex).setData(columnIndex, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clearData() {

        this.attendeeList.clear();
        this.extendData();


    }

    public void extendData() {

        this.attendeeList.add(new Attendee());
    }
}
