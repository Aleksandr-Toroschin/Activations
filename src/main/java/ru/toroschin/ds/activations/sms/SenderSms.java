package ru.toroschin.ds.activations.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SenderSms {
    public String sendSmsByProstor(SmsForSend message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SmsForSend> entity = new HttpEntity(message, headers);
        String answer = restTemplate.postForObject("http://api.prostor-sms.ru/messages/v2/send.json", entity, String.class);
        log.info("Отправлено смс: " + message + " Ответ от сервера после отправки смс: " + answer);
        return answer;
    }

    public String sendSmsAboutUpdate(String phone, String text, String clientId) {
        String login = "t89896159404";
        String password = "148654";
        SmsForSend smsForSend = new SmsForSend(true, login, password);

        String sender = "DEDAL";
        SmsMessage smsMessage = new SmsMessage(phone, sender, text, clientId);

        smsForSend.addMessage(smsMessage);
        return sendSmsByProstor(smsForSend);
    }
}
