package ui.panels.subpanels;


import data.MathUtilities;
import data.types.Attendee;
import data.types.attributes.Attendance;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EventKioskDataDisplayPanel extends JPanel {

    Attendee attendee;

    JLabel name;
    JLabel age;
    JLabel id;
    JLabel status;
    JPanel attendeeInfo;
    JLabel qrImage;

    public EventKioskDataDisplayPanel() {

        attendee = null;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        EmptyBorder emptyBorder = new EmptyBorder(15, 15, 15, 15);

        /////////////////////////////////////////////////////////////////

        attendeeInfo = new JPanel();
        attendeeInfo.setBorder(BorderFactory.createTitledBorder("Attendee Information"));

        attendeeInfo.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = 0;

        name = new JLabel("Name: ");
        name.setBorder(emptyBorder);
        age = new JLabel("Age: ");
        age.setBorder(emptyBorder);
        id = new JLabel("ID: ");
        id.setBorder(emptyBorder);
        status = new JLabel("Status: ");
        status.setBorder(emptyBorder);


        attendeeInfo.add(name, cons);
        attendeeInfo.add(age, cons);
        attendeeInfo.add(id, cons);
        attendeeInfo.add(status, cons);

        this.add(attendeeInfo);
        this.add(Box.createRigidArea(new Dimension(0, 15)));

        ////////////////////////////////////////////////////////////////

        JPanel attendanceEditor = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        attendanceEditor.setBorder(BorderFactory.createTitledBorder("Attendance Editor"));

        JButton markPresent = new JButton("Mark Present");
        markPresent.setMnemonic(KeyEvent.VK_P);
        markPresent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (attendee != null)
                    attendee.setData(8, new Attendance("Present"));
                try {
                    update(attendee);
                } catch (Exception e) {}
            }
        });

        JButton markAbsent = new JButton("Mark Absent");
        markAbsent.setMnemonic(KeyEvent.VK_A);
        markAbsent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (attendee != null)
                    attendee.setData(8, new Attendance("Absent"));
                try {
                    update(attendee);
                } catch (Exception e) {}
            }
        });

        JButton markLeft = new JButton("Mark Left");
        markLeft.setMnemonic(KeyEvent.VK_L);
        markLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (attendee != null)
                    attendee.setData(8, new Attendance("Left"));
                try {
                    update(attendee);
                } catch (Exception e) {
                }
            }
        });

        attendanceEditor.add(markPresent);
        attendanceEditor.add(markAbsent);
        attendanceEditor.add(markLeft);

        this.add(attendanceEditor);
        this.add(Box.createRigidArea(new Dimension(0, 15)));

        //////////////////////////////////////////////////////////////////

        JPanel qrImageContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        qrImageContainer.setBorder(BorderFactory.createTitledBorder("Scanned QR"));

        BufferedImage icon = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = icon.createGraphics();
        graphics.setPaint(new Color(255, 255, 255));
        graphics.fillRect(0, 0, icon.getWidth(), icon.getHeight());

        qrImage = new JLabel(new ImageIcon(icon));
        qrImageContainer.add(qrImage);

        this.add(qrImageContainer);


        /////////////////////////////////////////////////////////////////



    }

    public void update(Attendee e) throws Exception {

        this.attendee = e;

        name.setText("Name: " + attendee.getData(1) + ", " + attendee.getData(2));
        age.setText("Age: " + attendee.getData(4));
        id.setText("ID: " + attendee.getData(3));
        status.setText("Status: " + attendee.getData(8));

        if (attendee != null)
            qrImage.setIcon(new ImageIcon(MathUtilities.generateQRCodeImage(attendee.getQRContents())));

        attendeeInfo.revalidate();
        attendeeInfo.repaint();
        this.revalidate();
        this.repaint();

    }



}
