package ui.components.menus;

import data.AttendanceTableModel;
import ui.containers.CloseableJTabbedPane;
import ui.panels.DataAnalyser;
import ui.panels.DataEditor;
import ui.panels.EventKiosk;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Window extends JMenu {


    public Window (ArrayList<JPanel> panels, CloseableJTabbedPane target, AttendanceTableModel data) {

        super("Window");

        JMenuItem openKiosk = new JMenuItem("Open Event Kiosk");
        openKiosk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.addTab("Event Kiosk", panels.get(0));
            }
        });

        JMenuItem openEditor = new JMenuItem("Open Data Editor");
        openEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.addTab("Data Editor", panels.get(1));
            }
        });

        JMenuItem openAnalyser = new JMenuItem("Open Data Analyser");
        openAnalyser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.addTab("Data Analyser", panels.get(2));
                //target.setSel
            }

        });

        this.add(openKiosk);
        this.add(openEditor);
        //this.add(openAnalyser);


    }


}
