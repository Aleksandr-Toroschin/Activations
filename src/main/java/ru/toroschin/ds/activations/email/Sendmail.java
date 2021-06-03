package ru.toroschin.ds.activations.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.toroschin.ds.activations.templater.PageGenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@Slf4j
public class Sendmail {
    public void send(String addressTo, String topic, String text, int type) throws MessagingException, UnsupportedEncodingException {
        //Объект properties хранит параметры соединения.
        //Конкретно для Yandex параметры соединения можно подсмотреть тут:
        Properties properties = new Properties();
        //Хост или IP-адрес почтового сервера
        properties.put("mail.smtp.host", "smtp.timeweb.ru");
        //Требуется ли аутентификация для отправки сообщения
        properties.put("mail.smtp.auth", "true");
        //Порт для установки соединения
        properties.put("mail.smtp.socketFactory.port", "25");
        //Фабрика сокетов, так как при отправке сообщения Yandex требует SSL-соединения
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Создаем соединение для отправки почтового сообщения
        Session session = Session.getDefaultInstance(properties,
                //Аутентификатор - объект, который передает логин и пароль
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("3cad@dedal-service.ru", "3CADsupport");
                    }
                });

        //Создаем новое почтовое сообщение
        MimeMessage message = new MimeMessage(session);
        //От кого
        message.setFrom(new InternetAddress("3cad@dedal-service.ru","IT отдел", "UTF-8"));
        message.addHeader("Content-Type", "text/html; charset=UTF-8");
        //Кому
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
        //Тема письма
        message.setSubject(topic, "UTF-8");

        switch (type) {
            // простой текст
            case 1: message.setText(text, StandardCharsets.UTF_8.toString());
            // html формат
            case 2: message.setContent(text, "text/html; charset=\"utf-8\"");
        }
        Transport.send(message);
    }

    public void sendEmail(String addressTo, String topic, int type) {
        try {
            Map<String, Object> pageVariables = new HashMap<>();
            String page = PageGenerator.instance().getPage("letter.html", pageVariables);
            send(addressTo, topic, page, type);
            log.info("Отправлено письмо по адресу: " + addressTo + " тема: " + topic);
        } catch (UnsupportedEncodingException | MessagingException e) {
            log.warn(e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }

}