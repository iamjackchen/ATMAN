package ui.panels;

import data.AttendanceTableModel;
import data.MathUtilities;
import data.types.attributes.Attendance;
import data.types.attributes.Enumerator;
import data.types.attributes.Sex;
import io.MailSender;
import ui.components.EnumCellEditor;
import ui.components.EnumCellRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

                JLabel messagePrompt = new JLabel("Please enter your email configuration, and the event name that emails will be labeled with:  ");

                JTextField senderAddress = new JTextField("Email Address");
                senderAddress.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                senderAddress.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (senderAddress.getText().equals("Email Address")) {
                            senderAddress.setText("");
                            senderAddress.setForeground(UIManager.getColor("TextField.foreground"));
                        }
                    }
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (senderAddress.getText().isEmpty()) {
                            senderAddress.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                            senderAddress.setText("Email Address");
                        }
                    }
                });
                
                
                JPasswordField emailPassword =  new JPasswordField("Password");
                char defaultEchoChar = emailPassword.getEchoChar();
                emailPassword.setEchoChar((char)0);
                emailPassword.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                emailPassword.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (new String(emailPassword.getPassword()).equals("Password")) {
                            emailPassword.setEchoChar(defaultEchoChar);
                            emailPassword.setText("");
                            emailPassword.setForeground(UIManager.getColor("TextField.foreground"));
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (new String(emailPassword.getPassword()).isEmpty()) {
                            emailPassword.setEchoChar((char)0);
                            emailPassword.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                            emailPassword.setText("Password");
                        }
                    }
                });
                
                
                JTextField smtpHost = new JTextField("SMTP Host");
                smtpHost.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                smtpHost.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (smtpHost.getText().equals("SMTP Host")) {
                            smtpHost.setText("");
                            smtpHost.setForeground(UIManager.getColor("TextField.foreground"));
                        }
                    }
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (smtpHost.getText().isEmpty()) {
                            smtpHost.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                            smtpHost.setText("SMTP Host");
                        }
                    }
                });
                

                JTextField smtpPort = new JTextField("SMTP Port");
                smtpPort.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                smtpPort.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (smtpPort.getText().equals("SMTP Port")) {
                            smtpPort.setText("");
                            smtpPort.setForeground(UIManager.getColor("TextField.foreground"));
                        }
                    }
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (smtpPort.getText().isEmpty()) {
                            smtpPort.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                            smtpPort.setText("SMTP Port");
                        }
                    }
                });
                
                
                JTextField eventName = new JTextField("Event Name");
                eventName.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                eventName.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (eventName.getText().equals("Event Name")) {
                            eventName.setText("");
                            eventName.setForeground(UIManager.getColor("TextField.foreground"));
                        }
                    }
                    @Override
                    public void focusLost(FocusEvent e) {
                        if (eventName.getText().isEmpty()) {
                            eventName.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                            eventName.setText("Event Name");
                        }
                    }
                });
                

                if (JOptionPane.showOptionDialog(new JFrame(),
                        new Object[] {messagePrompt, new JSeparator(), senderAddress, emailPassword, smtpHost, smtpPort, eventName},
                        "Enter Email Details",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {

                    MailSender mailSender = new MailSender(senderAddress.getText(), new String(emailPassword.getPassword()), smtpHost.getText(), smtpPort.getText(), attendanceTableModel.getAttendeeList(), eventName.getText());
                    mailSender.start();


                }



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
