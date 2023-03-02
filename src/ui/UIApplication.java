package ui;

import com.github.sarxos.webcam.Webcam;
import data.AttendanceTableModel;
import data.types.Attendee;
import data.types.attributes.Attendance;
import data.types.attributes.Sex;
import ui.components.menus.Settings;
import ui.containers.CloseableJTabbedPane;
import ui.containers.MenuBar;
import ui.panels.DataAnalyser;
import ui.panels.DataEditor;
import ui.panels.EventKiosk;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIApplication extends JFrame {

    public UIApplication() {

        List<Attendee> list = new ArrayList<Attendee>();
        list.add(new Attendee());


        AttendanceTableModel dataModel  = new AttendanceTableModel(list);
        dataModel.setSaved(true);

        Webcam webcam = Webcam.getDefault();


        ArrayList<JPanel> panels = new ArrayList<JPanel>();
        EventKiosk eventKiosk = new EventKiosk(dataModel);
        DataEditor dataEditor = new DataEditor(dataModel);
        DataAnalyser dataAnalyser = new DataAnalyser();
        panels.add(eventKiosk);
        panels.add(dataEditor);
        panels.add(dataAnalyser);

        CloseableJTabbedPane panelHolder = new CloseableJTabbedPane();
        this.add(panelHolder);
        this.setJMenuBar(new MenuBar(dataModel, panelHolder, panels));

        this.setTitle("Attendance Manager v1.0");
        this.setPreferredSize(new Dimension(1920,  1080));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

}
