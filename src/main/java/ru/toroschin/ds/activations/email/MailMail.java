package ru.toroschin.ds.activations.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.toroschin.ds.activations.email.MyJavaMailSender;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailMail {
    // должен работать, но не проверял.
    // предпочтительный вариант, т.к. работает в спринге

    private final JavaMailSender mailSender;
//    private SimpleMailMessage simpleMailMessage;

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
//        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
    }

    public void sendMail(String dear, String content) {

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("3cad@dedal-service.ru");
            helper.setTo("kuhnimalina@yandex.ru");
            helper.setSubject("Test");
            helper.setText("Text of the letter");
            FileSystemResource file = new FileSystemResource("F:\\Temp\\332.doc");
//            helper.addInline("file name", file);
            helper.addAttachment(file.getFilename(), file);

        }catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }

}
