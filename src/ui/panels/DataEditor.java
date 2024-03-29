package ui.panels;

import data.AttendanceTableModel;
import data.MathUtilities;
import data.types.attributes.Attendance;
import data.types.attributes.Enumerator;
import data.types.attributes.Sex;
import io.EmailSettingsHandler;
import ui.components.EnumCellEditor;
import ui.components.EnumCellRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DataEditor extends JPanel {

    public DataEditor(AttendanceTableModel attendanceTableModel) {
        this.setLayout(new BorderLayout());

        //Created a JPanel to contain the JTable instead of plopping the JTable directly onto the BorderLayout of the MainPanel for better component alignment
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(new EmptyBorder(0, 15, 0, 15));
        JTable dataDisplay = new JTable(attendanceTableModel);
        dataDisplay.setAutoCreateRowSorter(true);
        dataDisplay.setDefaultRenderer(Enumerator.class, new EnumCellRenderer());
        dataDisplay.setDefaultEditor(Sex.class, new EnumCellEditor(Sex.getSexOptions(), Sex.class));
        dataDisplay.setDefaultEditor(Attendance.class, new EnumCellEditor(Attendance.getAttendanceOptions(), Attendance.class));
        tableContainer.add(new JScrollPane(dataDisplay), BorderLayout.CENTER);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton selectAll = new JButton("Select All");
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {
                    attendanceTableModel.getAttendeeAt(i).setData(0, Boolean.TRUE);
                    attendanceTableModel.fireTableDataChanged();

                }

            }
        });

        JButton deselectAll = new JButton("Deselect All");
        deselectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {
                    attendanceTableModel.getAttendeeAt(i).setData(0, Boolean.FALSE);
                    attendanceTableModel.fireTableDataChanged();

                }

            }
        });


        JButton generateQRDataForSelected = new JButton("Generate QR Data for Selected");
        generateQRDataForSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {

                    if ((boolean) attendanceTableModel.getValueAt(i,0))
                        attendanceTableModel.getAttendeeAt(i).setQRData(MathUtilities.generateQRData(25));

                }

                JOptionPane.showMessageDialog(null,"QR Data Generated Successfully","Task Completed",1);

            }
        });


        JButton exportQRImagesForSelected = new JButton("Export QR Images for Selected");
        exportQRImagesForSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser directorySelector = new JFileChooser();
                directorySelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (directorySelector.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {

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

                    JOptionPane.showMessageDialog(null,"Export Successful","Task Completed",1);

                }

            }
        });

        JButton emailQRImagesForSelected = new JButton("Email QR Images for Selected");
        emailQRImagesForSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                EmailSettingsHandler.sendEmail(attendanceTableModel);
            }
        });

        JPanel qrTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));

        qrTools.add(selectAll);
        qrTools.add(deselectAll);
        qrTools.add(generateQRDataForSelected);
        qrTools.add(exportQRImagesForSelected);
        qrTools.add(emailQRImagesForSelected);



        /////////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel dataEditTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        JButton removeSelected = new JButton("Remove Selected Rows");
        JButton addRow = new JButton("Add Row");
        JButton saveEdit = new JButton("Save");
        JButton revertEdit = new JButton("Revert");
        JButton editTable = new JButton("Edit");



        addRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                attendanceTableModel.extendData();
                attendanceTableModel.fireTableDataChanged();

            }
        });


        removeSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (int i = 0; i < attendanceTableModel.getRowCount(); i++) {
                    if (attendanceTableModel.getAttendeeAt(i).getData(0) == Boolean.TRUE) {
                        attendanceTableModel.removeAttendeeAt(i);
                        i--;
                    }
                    attendanceTableModel.fireTableDataChanged();

                }

            }
        });


        saveEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                attendanceTableModel.setEditable(false);
                attendanceTableModel.removeBackup();
                attendanceTableModel.fireTableDataChanged();

                dataEditTools.removeAll();
                dataEditTools.add(editTable);
                dataEditTools.revalidate();
                dataEditTools.repaint();

            }
        });


        revertEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                attendanceTableModel.revertToBackup();
                attendanceTableModel.removeBackup();
                attendanceTableModel.setEditable(false);
                attendanceTableModel.fireTableDataChanged();

                dataEditTools.removeAll();
                dataEditTools.add(editTable);
                dataEditTools.revalidate();
                dataEditTools.repaint();

            }
        });


        editTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                attendanceTableModel.backup();
                attendanceTableModel.setEditable(true);

                dataEditTools.removeAll();
                dataEditTools.add(removeSelected);
                dataEditTools.add(addRow);
                dataEditTools.add(saveEdit);
                dataEditTools.add(revertEdit);
                dataEditTools.revalidate();
                dataEditTools.repaint();

            }
        });

        dataEditTools.add(editTable);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////




        this.add(qrTools, BorderLayout.NORTH);
        this.add(dataEditTools, BorderLayout.SOUTH);
        this.add(tableContainer, BorderLayout.CENTER);



    }


}
