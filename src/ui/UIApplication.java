package ui;

import data.AttendanceTableModel;
import data.types.Attendee;
import data.types.attributes.Attendance;
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
        list.add(new Attendee());


        AttendanceTableModel test  = new AttendanceTableModel(list);
        test.setSaved(true);

        CloseableJTabbedPane panelHolder = new CloseableJTabbedPane();
        this.add(panelHolder);
        this.setJMenuBar(new MenuBar(test, panelHolder));

        this.setTitle("Attendance Manager v1.0");
        this.setPreferredSize(new Dimension(1920,  1080));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

}
