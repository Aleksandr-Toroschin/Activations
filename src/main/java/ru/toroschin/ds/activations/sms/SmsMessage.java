package ru.toroschin.ds.activations.sms;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsMessage implements Serializable {
    private String phone;
    private String sender;
    private String text;
    private String clientId;

    public SmsMessage(String phone, String sender, String text, String clientId) {
        this.phone = phone;
        this.sender = sender;
        this.text = text;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "phone='" + phone + '\'' +
                ", sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
