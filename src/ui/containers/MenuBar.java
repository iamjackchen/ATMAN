package ui.containers;

import data.AttendanceTableModel;
import ui.components.menus.Session;
import ui.components.menus.Settings;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(AttendanceTableModel a) {

        this.add(new Session(a));
        this.add(new Settings());

    }


}
