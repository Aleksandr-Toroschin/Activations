package ru.toroschin.ds.activations.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.toroschin.ds.activations.email.Sendmail;
import ru.toroschin.ds.activations.error_handling.ErrorMessage;
import ru.toroschin.ds.activations.models.Activation;
import ru.toroschin.ds.activations.dtos.ActivationDTO;
import ru.toroschin.ds.activations.dtos.ActivationGroupDTO;
import ru.toroschin.ds.activations.models.ActivationLK;
import ru.toroschin.ds.activations.repositories.ActivationRepository;
import ru.toroschin.ds.activations.sms.SenderSms;
import ru.toroschin.ds.activations.util.DateFileHandler;
import ru.toroschin.ds.activations.util.DateParser;
import ru.toroschin.ds.activations.util.Dates;
import ru.toroschin.ds.activations.util.RestHandler;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class ActivationService {
    private final ActivationRepository activationRepository;
    private final Sendmail senderEmail;
    private final SenderSms senderSms;
    private final DateParser dateParser;
    private final DateFileHandler dateFileHandler;
    private final RestHandler restHandler;

    public List<Activation> findAll() {
        return activationRepository.findAllActiv();
    }

    public List<ActivationDTO> findForThisMonth() {
        List<Activation> activations = findAll();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateParser.parse(activations.get(0).getDateDownload());
        log.info("Определена дата выгрузки: " + formatter.format(date) + " из поля: " + activations.get(0).getDateDownload());

        Calendar calendar = getCalendar(date);
        Calendar calendarFuture = getCalendarFuture(date);

        List<ActivationDTO> activationDTOS = activations
                .stream()
                .map((a) -> new ActivationDTO(a, date))
                .filter(a -> a.getDateDisconnectNew().after(calendar.getTime()))
                .filter(a -> a.getDateConnectNew().before(calendarFuture.getTime()))
                .collect(Collectors.toList());
        log.info("Выбраны активации за предыдущий месяц. Определены даты интервала: " + formatter.format(calendar.getTime()) + " - " + formatter.format(calendarFuture.getTime()));
        return activationDTOS;
    }

    public List<ActivationLK> findAllForLK() {
        List<Activation> activations = activationRepository.findAllLK();
        return activations
                .stream()
                .map(ActivationLK::new)
                .filter(a -> !a.getCode_diler().equals("delete"))
                .collect(Collectors.toList());
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar;
    }

    private Calendar getCalendarFuture(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public ErrorMessage savePDF() {
        return new ErrorMessage("Функция в разработке!");
    }

    public String updateData() {
        Optional<Dates> dates = readDates();
        if (dates.isPresent()) {
            if (isDateInDBNew(dates.get())) {
                String text = "Обновлена информация об активациях";
                senderEmail.sendEmail("info@dedal-service.ru", text, 2);
                senderSms.sendSmsAboutUpdate("79896155590", text, "1");
                log.info("Обновлена информация об активациях. Старая дата: " + dates.get().getOldDate() + " новая дата: " + dates.get().getNewDate());
                if (isNewMonth(dates.get())) {
                    log.info("Новый месяц в активациях. Старая дата: " + dates.get().getOldDate() + " новая дата: " + dates.get().getNewDate());
                    senderEmail.sendEmail("sales@dedal-service.ru", text, 2);
                    senderSms.sendSmsAboutUpdate("79185524982", text, "2");
                    senderEmail.sendEmail("anastaskovalenko61@mail.ru", text, 2);
                    senderSms.sendSmsAboutUpdate("79895005715", text, "3");
                }
                restHandler.sendPOSTRequest(findAllForLK());
                return "Данные обновлены";
            }
        }
        return "Данные не обновлены";
    }

    public Optional<Dates> readDates() {
        String dateOld;
        String dateNew = "";
        try {
            dateNew = readDateFromDB();
            dateOld = dateFileHandler.readDateFromFile();
            Dates dates = new Dates(dateOld, dateNew);
            return Optional.of(dates);
        } catch (Exception e) {
            log.warn("Ошибка чтения из файла saveDate.txt или из БД " + e.getMessage() + Arrays.toString(e.getStackTrace()));
            dateFileHandler.writeDateToFile(dateNew);
            return Optional.empty();
        }
    }

    public boolean isDateInDBNew(Dates dates) {
        String dateOld = dates.getOldDate();
        String dateNew = dates.getNewDate();
        if (!dateOld.equals(dateNew)) {
            dateFileHandler.writeDateToFile(dateNew);
            log.info("Проверка данных об активациях: сведения об активациях изменены. Новая дата: " + dateNew + " Старая дата: " + dateOld);
            return true;
        }
        return false;
    }

    public boolean isNewMonth(Dates dates) {
        String dateOld = dates.getOldDate();
        String dateNew = dates.getNewDate();
        if (dateOld.length() > 3) {
            dateOld = dateOld.substring(1, 3);
        } else {
            return false;
        }
        if (dateNew.length() > 3) {
            dateNew = dateNew.substring(1, 3);
        } else {
            return false;
        }
        return !dateOld.equals(dateNew);
    }

    private String readDateFromDB() {
        Activation activations = activationRepository.findOneForDate().orElseThrow();
        return activations.getDateDownload();
    }

    public Set<ActivationGroupDTO> findForThisMonthByGroup() {
        List<ActivationDTO> activationDTOS = findForThisMonth();
        Set<ActivationGroupDTO> activationGroupDTOSet = new TreeSet<>();
        for (ActivationDTO activationDTO : activationDTOS) {
            activationGroupDTOSet.add(new ActivationGroupDTO(activationDTO));
        }
//        activationGroupDTOSet.stream().sorted(a -> a.getName()).collect();

        for (ActivationGroupDTO activationGroupDTO : activationGroupDTOSet) {
            String code = activationGroupDTO.getCode();
            for (ActivationDTO activationDTO : activationDTOS) {
                if (code.equals(activationDTO.getCode())) {
                    activationGroupDTO.incrementCount();
                    if (activationGroupDTO.getName().equals("")) {
                        activationGroupDTO.setName(activationDTO.getName());
                    }
                }
            }
        }
        return activationGroupDTOSet;
    }

    public String sendTestSms() {
        return senderSms.sendSmsAboutUpdate("89896155590", "Test send sms from activations api", "1");
    }

}
