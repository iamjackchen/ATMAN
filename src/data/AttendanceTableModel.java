package data;

import data.types.Attendee;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AttendanceTableModel extends AbstractTableModel {

    private List<Attendee> attendeeList;

    private String[] parameters;

    private Class[] parameterTypes;

    public AttendanceTableModel(List<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
        this.parameters = attendeeList.get(0).getParameterList();
        this.parameterTypes = attendeeList.get(0).getParameterDatatypes();
    }

    @Override
    public int getRowCount() { return attendeeList.size(); }

    @Override
    public int getColumnCount() { return parameters.length; }

    @Override
    public Class<?> getColumnClass(int columnIndex) { return parameterTypes[columnIndex]; }

    @Override
    public String getColumnName(int column) {return parameters[column];}

    @Override
    public Object getValueAt(int i, int i1) {
        return null;
    }

    public Object getvalueAt(int rowIndex, int columnIndex) {

        Attendee row = attendeeList.get(rowIndex);

        return null;




    }


}
