package ru.toroschin.ds.activations.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivationLK {
    private String activation_code; // код активации
    private String activation_id;
    private String code_diler;  // буквы
    private String status;
    private String coefficlient;
    private String dateDisconnect; // дата отключения
    private String dateConnect; // дата активации

    public ActivationLK(Activation activation) {
        activation_id = activation.getId();
        code_diler = activation.getCode();
        dateDisconnect = activation.getDateDisconnect();
        dateConnect = activation.getDateConnect();
        activation_code = activation.getCodeActivation();
        coefficlient = activation.getCoeff();
        String remark = activation.getRemark();
        if (activation.getActive() == 1) {
            status = "(active)";
        } else {
            status = code_diler;
            if (remark.indexOf(" ") > 0) {
                code_diler = remark.substring(0, remark.indexOf(" "));
            } else {
                code_diler = remark.trim();
            }
        }

    }
}
