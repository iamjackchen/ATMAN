package ui.containers;

import data.AttendanceTableModel;
import ui.components.menus.Session;
import ui.components.menus.Settings;
import ui.components.menus.Window;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(AttendanceTableModel a, CloseableJTabbedPane panelHolder) {

        this.add(new Session(a));
        this.add(new Window(panelHolder, a));
        this.add(new Settings());

    }


}
