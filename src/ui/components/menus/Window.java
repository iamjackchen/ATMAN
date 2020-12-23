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
import java.util.Arrays;
import java.util.Collections;

public class Window extends JMenu {


    public Window (CloseableJTabbedPane target, AttendanceTableModel data) {

        super("Window");

        JMenuItem openEditor = new JMenuItem("Open Data Editor");
        openEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.addTab("Data Editor", new DataEditor(data));
            }
        });

        JMenuItem openAnalyser = new JMenuItem("Open Data Analyser");
        openAnalyser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.addTab("Data Analyser", new DataAnalyser());
            }
        });

        JMenuItem openKiosk = new JMenuItem("Open Event Kiosk");
        openKiosk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                target.addTab("Event Kiosk", new EventKiosk((openEditor.isEnabled() && openAnalyser.isEnabled()), data));
            }
        });


        this.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent menuEvent) {

                if (target.getTabCount() > 0 ) {

                    String[] openTabs = new String[target.getTabCount()];
                    for (int i = 0; i < target.getTabCount(); i++) { openTabs[i] = target.getTitleAt(i); }


                    openKiosk.setEnabled(!Arrays.asList(openTabs).contains("Event Kiosk"));
                    openEditor.setEnabled(!Arrays.asList(openTabs).contains("Data Editor"));
                    openAnalyser.setEnabled(!Arrays.asList(openTabs).contains("Data Analyser"));

                } else {
                    openKiosk.setEnabled(true);
                    openEditor.setEnabled(true);
                    openAnalyser.setEnabled(true);
                }
            }

            @Override
            public void menuDeselected(MenuEvent menuEvent) {

            }

            @Override
            public void menuCanceled(MenuEvent menuEvent) {

            }

        });

        this.add(openKiosk);
        this.add(openEditor);
        this.add(openAnalyser);


    }


}
