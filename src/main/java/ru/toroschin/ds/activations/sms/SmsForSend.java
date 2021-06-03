package ru.toroschin.ds.activations.sms;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SmsForSend implements Serializable {
    private List<SmsMessage> messages;
    private boolean showBillingDetails;
    private String login;
    private String password;

    public SmsForSend(boolean showBillingDetails, String login, String password) {
        this.showBillingDetails = showBillingDetails;
        this.login = login;
        this.password = password;
        messages = new ArrayList<>();
    }

    public void addMessage(SmsMessage message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        return messages.toString();
    }
}
