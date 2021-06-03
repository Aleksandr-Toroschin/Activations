package ru.toroschin.ds.activations.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.toroschin.ds.activations.models.Activation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@Data
@NoArgsConstructor
@Slf4j
public class ActivationDTO {
    private String id;
    private String name;    // юр. наименование
    private String code;  // для активных показывает код клиента
    private int active;  // активен или нет. 1 - активен, 0 - нет
    private String activity;
    private String dateDisconnect; // дата отключения
    private String dateConnect; // дата активации
    private String dateFirstConnect; // дата первой активации
    private String codeActivation; // код активации
    private String remark;  // примечание. В нем лежит код дилера, если он отключен
    private String dateDownload; // дата выгрузки этой информации
    private Date dateDisconnectNew;
    private Date dateConnectNew;
    private Date dateDownloadNew;

    public ActivationDTO(Activation activation, Date dateDownloadNew) {
        this.id = activation.getId();
        this.name = activation.getName();
        this.code = activation.getCode();
        this.active = activation.getActive();
        this.dateDisconnect = activation.getDateDisconnect();
        this.dateConnect = activation.getDateConnect();
        this.dateFirstConnect = activation.getDateFirstConnect();
        this.codeActivation = activation.getCodeActivation();
        this.remark = activation.getRemark();
        this.dateDownload = activation.getDateDownload();
        this.activity = active == 1 ? "" : "Отключено";
        dateDisconnectNew = convertDate(dateDisconnect);
        dateConnectNew = convertDate(dateConnect);
        if ((!remark.isBlank()) && (active == 0) && (code.equals("(disable)") || code.equals("(stop)") || code.equals("(pause)") || code.equals("(test)"))) {
            if (remark.indexOf(" ") > 0) {
                this.code = remark.substring(0, remark.indexOf(" "));
            } else {
                this.code = remark.trim();
            }
        }
        this.dateDownloadNew = dateDownloadNew;
        this.dateDownload = convertToPayDate(dateDownloadNew);
    }

    private Date convertDate(String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.MONTH, 1);
        Calendar calendarOld = new GregorianCalendar(2017, 0, 1);
        try {
            if (data.equals("")) {
                return calendar.getTime();
            }
            return formatter.parse(data);
        } catch (ParseException e) {
            log.info("Ошибка парсинга даты из короткой даты = " + data + " (ActivationDTO)");
            return calendarOld.getTime();
        }
    }

    private String convertToPayDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("LLLL yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return formatter.format(calendar.getTime());
    }

}
