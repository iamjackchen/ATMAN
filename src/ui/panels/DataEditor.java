package ui.panels;

import data.AttendanceTableModel;
import data.MathUtilities;
import data.types.attributes.Attendance;
import data.types.attributes.Enumerator;
import data.types.attributes.Sex;
import ui.components.EnumCellEditor;
import ui.components.EnumCellRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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


        JButton generateQRDataForSelected = new JButton();
        generateQRDataForSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {

                    if ((boolean) attendanceTableModel.getValueAt(i,0))
                        attendanceTableModel.getAttendeeAt(i).setQRData(MathUtilities.generateQRData(25));

                }

            }
        });


        JButton exportQRImages = new JButton();
        exportQRImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser directorySelector = new JFileChooser();
                directorySelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = directorySelector.showOpenDialog(new JFrame());

                if (option == JFileChooser.APPROVE_OPTION) {

                    for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {

                        try {
                            ImageIO.write(MathUtilities.generateQRCodeImage(attendanceTableModel.getAttendeeAt(i).getQRContents()),
                                    "png",
                                    new File(directorySelector.getSelectedFile().getAbsolutePath() + "/" + attendanceTableModel.getValueAt(i, 3) + ".png"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }

            }
        });


        JButton exportQRImagesForSelected = new JButton();
        exportQRImagesForSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser directorySelector = new JFileChooser();
                directorySelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = directorySelector.showOpenDialog(new JFrame());

                if (option == JFileChooser.APPROVE_OPTION) {

                    for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {

                        if ((boolean) attendanceTableModel.getValueAt(i,0))
                            try {
                                ImageIO.write(MathUtilities.generateQRCodeImage(attendanceTableModel.getAttendeeAt(i).getQRContents()),
                                        "png",
                                        new File(directorySelector.getSelectedFile().getAbsolutePath() + "/" + attendanceTableModel.getValueAt(i, 3) + ".png"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    }

                }

            }
        });



        JButton saveEdit = new JButton();
        saveEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                attendanceTableModel.setEditable(false);
                attendanceTableModel.removeBackup();
            }
        });


        JButton revertEdit = new JButton();
        saveEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                attendanceTableModel.setEditable(false);
                attendanceTableModel.revertToBackup();
                attendanceTableModel.removeBackup();
            }
        });


        JButton editTable = new JButton();
        editTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                attendanceTableModel.backup();
                attendanceTableModel.setEditable(true);

            }
        });
        






        //this.add(exportQRData, BorderLayout.SOUTH);
        this.add(new JScrollPane(dataDisplay), BorderLayout.CENTER);



    }


}
