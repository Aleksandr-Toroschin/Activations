package ru.toroschin.ds.activations.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.toroschin.ds.activations.models.ActivationLK;

import java.util.List;

@Component
@Slf4j
public class RestHandler {
    public String sendPOSTRequest(List<ActivationLK> list) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ActivationLK>> entity = new HttpEntity(list, headers);
        String answer = restTemplate.postForObject("https://lk.dedal-service.ru/api/activation", entity, String.class);
        log.info("Ответ от сервера после обновления информации об активации: " + answer);
        return answer;
    }

}
