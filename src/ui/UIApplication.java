package ui;

import data.AttendanceTableModel;
import data.types.Attendee;
import data.types.attributes.Sex;
import ui.containers.CloseableJTabbedPane;
import ui.containers.MenuBar;
import ui.panels.DataEditor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIApplication extends JFrame {

    public UIApplication() {

        List<Attendee> list = new ArrayList<Attendee>();
        Attendee a = new Attendee("Chen", "Jack", 37352, 16, new Sex("Male"), "British", 11);
        Attendee b = new Attendee("Das", "Rishit", 96969, 17, new Sex("Male"), "Indian", 11);
        Attendee c = new Attendee("Camacho", "Jonathan", 20420, 16, new Sex("Male"), "American", 11);
        list.add(a);
        list.add(b);
        list.add(c);

        AttendanceTableModel test  = new AttendanceTableModel(list);
        test.setEditable(true);

        /*
        JTable table = new JTable(test);
        table.setDefaultRenderer(Enumerator.class, new EnumCellRenderer());
        table.setDefaultEditor(Sex.class, new EnumCellEditor(Sex.getSexOptions(), Sex.class));
        table.setDefaultEditor(Attendance.class, new EnumCellEditor(Attendance.getAttendanceOptions(), Attendance.class));
        this.add(new JScrollPane(table));
        */

        CloseableJTabbedPane panelHolder = new CloseableJTabbedPane();
        panelHolder.addTab("Analyser", new DataEditor(test));



        this.add(panelHolder);
        this.setJMenuBar(new MenuBar(test));

        this.setTitle("Table Example");
        this.setPreferredSize(new Dimension(1920,  1080));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);


    }

}
