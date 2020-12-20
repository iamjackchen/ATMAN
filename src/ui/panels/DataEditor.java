package ui.panels;

import data.AttendanceTableModel;
import data.MathUtilities;
import data.types.attributes.Attendance;
import data.types.attributes.Enumerator;
import data.types.attributes.Sex;
import ui.components.EnumCellEditor;
import ui.components.EnumCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataEditor extends JPanel {

    public DataEditor(AttendanceTableModel attendanceTableModel) {
        this.setLayout(new BorderLayout());



        JTable dataDisplay = new JTable(attendanceTableModel);
        dataDisplay.setAutoCreateRowSorter(true);
        dataDisplay.setDefaultRenderer(Enumerator.class, new EnumCellRenderer());
        dataDisplay.setDefaultEditor(Sex.class, new EnumCellEditor(Sex.getSexOptions(), Sex.class));
        dataDisplay.setDefaultEditor(Attendance.class, new EnumCellEditor(Attendance.getAttendanceOptions(), Attendance.class));



        JButton generateQRData = new JButton();
        generateQRData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {
                    attendanceTableModel.getAttendeeAt(i).setQRData(MathUtilities.generateQRData(25));

                }

            }
        });


        JButton exportQRData = new JButton();
        exportQRData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {
                    attendanceTableModel.getAttendeeAt(i).setQRData(MathUtilities.generateQRData(25));

                }

            }
        });




        //this.add(getSelected, BorderLayout.SOUTH);
        this.add(new JScrollPane(dataDisplay), BorderLayout.CENTER);



    }


}
