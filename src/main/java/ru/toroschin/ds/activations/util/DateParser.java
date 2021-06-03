package ru.toroschin.ds.activations.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
@Slf4j
public class DateParser {
    public Date parse(String data) {
        String[] months = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        Locale ru = new Locale("ru");
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(ru);
        symbols.setMonths(months);
        SimpleDateFormat format = new SimpleDateFormat("MMM  d yyyy  h:ma", ru);
        format.setDateFormatSymbols(symbols);
        Date date = new Date();
        try {
            date = format.parse(data);
        } catch (ParseException e) {
            log.warn("Ошибка парсинга даты: " + data);
        }
        return date;
    }

}
