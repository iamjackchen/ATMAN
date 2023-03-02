package ui.containers;

import com.github.sarxos.webcam.Webcam;
import data.AttendanceTableModel;
import ui.components.menus.Session;
import ui.components.menus.Settings;
import ui.components.menus.Window;

import javax.swing.*;
import java.util.ArrayList;

public class MenuBar extends JMenuBar {

    public MenuBar(AttendanceTableModel tableModel, CloseableJTabbedPane panelHolder, ArrayList<JPanel> panels ) {

        this.add(new Session(tableModel));
        this.add(new Window(panels, panelHolder, tableModel));
        this.add(new Settings(panels));

    }


}
