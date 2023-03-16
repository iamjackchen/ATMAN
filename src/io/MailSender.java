package io;


import com.google.zxing.WriterException;
import data.MathUtilities;
import data.types.Attendee;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


//Used thread for sending Mail so that in the future, more tasks can be run at the same time if needed without clogging up the call stack
public class MailSender extends Thread {
    
    //Class members
    private String senderAddress;
    private String host;
    private String port;
    private Session session;
    private String name;
    private Boolean generateWithDomain;
    private String domain;

    private List<Attendee> recipients;
    
    
    //Constructor
    public MailSender(String senderAddress, String password, String host, String port, List<Attendee> recipients, String eventName, Boolean generateWithDomain, String domain) {
        
        //Initialises members
        this.senderAddress = senderAddress;
        this.port = port;
        this.recipients = recipients;
        this.name = eventName;
        this.host = host;
        this.generateWithDomain = generateWithDomain;
        this.domain = domain;

        
        //System config
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.user", senderAddress);
        properties.setProperty("mail.password", password);

        //Attempts to communicate to mail server + authenticate (login)        
        session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderAddress,password);
                    }
                }
            );


    }


    public void run() {

        boolean success = true;


        try {

            for (Attendee recipient : recipients) {

                if ((boolean) (recipient.getData(0))) {

                    //Creates new email
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress((senderAddress)));

                    //Sets recipient email depending on Attendee ID

                    if (generateWithDomain) {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress((recipient.getData(3).toString() + "@" + domain)));
                    } else {
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress((recipient.getData(3).toString())));
                    }

                    message.setSubject("E-Ticket for [" + name + "]");


                    BodyPart textMessage = new MimeBodyPart();
                    textMessage.setText("This is an automated email generated and sent by Attendance Manager v1.0. Please find your e-ticket attached to this email. \n\nCoded with <3 by Jack Chen");

                    MimeBodyPart imgAttachment = new MimeBodyPart();


                    //Generates QR code + attahces it to email
                    File temp = new File("temp.png");
                    ImageIO.write(MathUtilities.generateQRCodeImage(recipient.getQRContents()),
                            "png",
                            temp);

                    imgAttachment.attachFile(temp);
                    imgAttachment.setFileName("E-Ticket [" + recipient.getData(1).toString() + ", " + recipient.getData(2).toString() + "].png");


                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(textMessage);
                    multipart.addBodyPart(imgAttachment);
                    message.setContent(multipart);

                    //Sends message
                    Transport.send(message);
                    temp.delete();
                } //checks if attendee is selected

            }

        } catch (Exception e) {
            //e.printStackTrace(); //Exception handling

            JTextArea errorMessage = new JTextArea(e.getMessage());
            errorMessage.setEditable(false);

            JOptionPane.showMessageDialog(new JFrame(), errorMessage);

            success = false;



        }

        if (recipients.size() == 0) JOptionPane.showMessageDialog(new JFrame(), new JLabel("No emails sent"));
            else if (success) JOptionPane.showMessageDialog(new JFrame(), new JLabel("Emails sent successfully"));

    }



}
