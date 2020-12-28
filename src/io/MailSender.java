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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MailSender extends Thread {

    private String senderAddress;
    private String host;
    private Session session;
    private String name;

    private List<Attendee> recipients;

    public MailSender(String senderAddress, String password, String host, List<Attendee> recipients, String eventName) {
        this.senderAddress = senderAddress;
        this.recipients = recipients;
        this.name = eventName;
        this.host = host;

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", senderAddress);
        properties.setProperty("mail.password", password);


        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderAddress,password);
            }
        });


    }


    public void run() {

        for (int i = 0; i < recipients.size(); i++) {
            try {

                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress((senderAddress)));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress((recipients.get(i).getData(3).toString() + "mail.ssis-suzhou.net")));
                message.setSubject("E-Ticket for [" + name + "]");


                BodyPart textMessage = new MimeBodyPart();
                textMessage.setText("This is an automated email generated and sent by Attendance Manager v1.0. Please find your e-ticket attached to this email. Coded with <3 by Jack Chen");


                BodyPart imgAttachment = new MimeBodyPart();
                File temp = new File("temp");
                ImageIO.write(MathUtilities.generateQRCodeImage(recipients.get(i).getQRContents()),
                        "png",
                        temp);
                DataSource imgSrc = new FileDataSource(temp);
                imgAttachment.setDataHandler(new DataHandler(imgSrc));
                imgAttachment.setFileName("E-Ticket #" + recipients.get(i).getData(3).toString());


                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textMessage);
                multipart.addBodyPart(textMessage);
                message.setContent(multipart);


                Transport.send(message);
                temp.delete();


            } catch (MessagingException | WriterException | IOException e) {
                e.printStackTrace();

            }
        }



    }



}
