package io;

import data.AttendanceTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class EmailSettingsHandler {

    public static void sendEmail(AttendanceTableModel attendanceTableModel) {

        //

        JLabel messagePrompt0 = new JLabel("Please select whether you would like to use attendee IDs as email addresses, or whether you would \n" +
                " like to generate email addresses using attendee IDs attached to domain name (e.g. id@domain.com)");

        JTextField domain = new JTextField("Domain");
        domain.setForeground(UIManager.getColor("TextField.inactiveForeground"));
        domain.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (domain.getText().equals("Domain")) {
                    domain.setText("");
                    domain.setForeground(UIManager.getColor("TextField.foreground"));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (domain.getText().isEmpty()) {
                    domain.setForeground(UIManager.getColor("TextField.inactiveForeground"));
                    domain.setText("Domain");
                }
            }
        });

        JRadioButton idAsAddress = new JRadioButton("Use IDs as email addresses");
        JRadioButton generateWithDomain = new JRadioButton("Generate email addresses using IDs and a provided domain");

        ButtonGroup domainOptions = new ButtonGroup();
        idAsAddress.setSelected(true);

        domainOptions.add(idAsAddress);
        domainOptions.add(generateWithDomain);

        ActionListener domainOptionsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                domain.setEnabled(generateWithDomain.isSelected());
            }
        };

        generateWithDomain.addActionListener(domainOptionsListener);
        idAsAddress.addActionListener(domainOptionsListener);



        //

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
                new Object[] {messagePrompt0, new JSeparator(), idAsAddress, generateWithDomain, domain},
                "Select Email Options",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {

            if (JOptionPane.showOptionDialog(new JFrame(),
                    new Object[] {messagePrompt, new JSeparator(), senderAddress, emailPassword, smtpHost, smtpPort, eventName},
                    "Enter Email Details",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {

                MailSender mailSender = new MailSender(senderAddress.getText(), new String(emailPassword.getPassword()), smtpHost.getText(), smtpPort.getText(), attendanceTableModel.getAttendeeList(), eventName.getText(), generateWithDomain.isSelected(), domain.getText());
                mailSender.start();


            }

        }






    }
}
