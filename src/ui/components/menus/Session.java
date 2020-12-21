package ui.components.menus;

import com.formdev.flatlaf.FlatLightLaf;
import data.AttendanceTableModel;
import data.MathUtilities;
import io.CsvUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class Session extends JMenu {

    public Session(AttendanceTableModel attendanceTableModel) {
        super("Session");

        JMenuItem exportEvent = new JMenuItem("Export Event");
        exportEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser directorySelector = new JFileChooser();
                directorySelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = directorySelector.showOpenDialog(new JFrame());

                if (option == JFileChooser.APPROVE_OPTION) {

                    //directorySelector.getSelectedFile().getAbsolutePath() + "/" +
                    attendanceTableModel.setSaved(true);

                    }
                }
        });
        exportEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        this.add(exportEvent);


        JMenu importEvent = new JMenu("Open Event");
        this.add(importEvent);


        JMenuItem importFromCSV = new JMenuItem("Import from CSV");
        importFromCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (attendanceTableModel.isSaved()
                        || JOptionPane.showConfirmDialog(new JFrame(),
                        "You have not exported your current session yet. Current session data will be lost. Proceed?",
                        "Session Unsaved",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION
                ) {
                    JFileChooser fileSelector = new JFileChooser();
                    fileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileSelector.setAcceptAllFileFilterUsed(false);
                    fileSelector.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
                    int option = fileSelector.showOpenDialog(new JFrame());

                    if (option == JFileChooser.APPROVE_OPTION) {

                        attendanceTableModel.setAttendeeList(CsvUtilities.getAttendeeListFromCSV(fileSelector.getSelectedFile().getAbsolutePath()));
                        attendanceTableModel.fireTableDataChanged();

                    }
                }

            }
        });
        importFromCSV.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.SHIFT_DOWN_MASK + Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        importEvent.add(importFromCSV);


        JMenuItem newBlank = new JMenuItem("New Blank Event");
        newBlank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (attendanceTableModel.isSaved()
                        || JOptionPane.showConfirmDialog(new JFrame(),
                        "You have not exported your current session yet. Current session data will be lost. Proceed?",
                        "Session Unsaved",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION
                ) {
                    attendanceTableModel.clearData();
                    attendanceTableModel.fireTableDataChanged();
                }

            }
        });
        newBlank.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        importEvent.add(newBlank);


    }

}
