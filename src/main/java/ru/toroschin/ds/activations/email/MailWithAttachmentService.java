package ru.toroschin.ds.activations.email;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailWithAttachmentService {
    // работающий вариант для отправки с вложениями
    private String username = "";
    private String password = "";
    private String host = "";
    private String port = "";

    MailWithAttachmentService() {
    }

    public MailWithAttachmentService(String username, String password, String host, String port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    public Message createMail(Session session, String toAddress) throws AddressException, MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("3cad@dedal-service.ru"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject("Testing Subject");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This is message body");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachmentPart = new MimeBodyPart();
//        MimeBodyPart attachmentPart2 = new MimeBodyPart();

        attachmentPart.attachFile(new File("F:\\Temp\\Карта цветов.pdf"));
//        attachmentPart2.attachFile(new File("F:\\Temp\\20191217_205654.jpg"));

        multipart.addBodyPart(attachmentPart);
//        multipart.addBodyPart(attachmentPart2);

        message.setText("Test text");
        message.setContent(multipart);

        return message;
    }

    public void sendMail(Session session, String toAddress) throws MessagingException, IOException {
        Message message = createMail(session, toAddress);
        Transport.send(message);
    }

    public void sendManyMail(Message message) throws MessagingException, IOException {
        Transport.send(message);
    }
}
