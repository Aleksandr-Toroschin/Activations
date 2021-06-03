package ru.toroschin.ds.activations;

import lombok.extern.slf4j.Slf4j;
import ru.toroschin.ds.activations.email.MailWithAttachmentService;
import ru.toroschin.ds.activations.email.Sendmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class Test {
    private static Sendmail sendmail;
    public static void main(String[] args) throws IOException, MessagingException, ParseException {

//        MailMail mail = new MailMail();
//        mail.setMailSender(new MyJavaMailSender());
//        mail.sendMail("dear", "content");

//        MailWithAttachmentService mail =  new MailWithAttachmentService("3cad@dedal-service.ru", "3CADsupport", "smtp.timeweb.ru", "25");
//        sendMoreEmails(mail);

//        DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("MMMM d yyyy h:ma", Locale.forLanguageTag("ru"));
//        DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.forLanguageTag("ru"));
//        LocalDate date = LocalDate.parse("июн  1 2021  7:47AM", formatterF); //.parse(data);
//        LocalDate date = LocalDate.parse("Июнь 1 2021", formatterF); //.parse(data);

//        DateFormat formatterF = new SimpleDateFormat("MMM  d yyyy  h:ma");
//        Date date = formatterF.parse("Июн  1 2021  7:47AM");
//        System.out.println(date);

//        String testDate = "июн  1 2021  7:47AM";
//        String[] months = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
//        Locale ru = new Locale("ru");
//        DateFormatSymbols symbols = DateFormatSymbols.getInstance(ru);
//        symbols.setMonths(months);
//        SimpleDateFormat format = new SimpleDateFormat("MMM  d yyyy  h:ma", ru);
//        format.setDateFormatSymbols(symbols);
//        try {
//            System.out.println("Date is: " + format.parse(testDate));
//        } catch (Exception e) {
//            System.out.println("Error while parsing");
//        }


//        readFile();
//        SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.MONTH, -1);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        System.out.println(formatter.format(calendar.getTime()));
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("LLLL yyyy");
//        LocalDate localDate = LocalDate.now();
//        System.out.println(dateTimeFormatter.format(localDate));
        //        Locale.setDefault(Locale.forLanguageTag("en"));
//        System.out.println(Locale.getDefault());

//        Locale.setDefault(Locale.forLanguageTag("ru"));
//        System.out.println(Locale.getDefault());

//        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d yyyy h:ma", Locale.forLanguageTag("ru"));
//        String s = formatter.parse("май  3 2021  2:18PM").toString();
//        System.out.println(s);
        int year = 2021;
//        int month = 5;
//        String data = "01/01/2018";
        Calendar calendar = new GregorianCalendar(2021, 0, 1);
        System.out.println(calendar.getTime());
//        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println(calendar.getTime());

//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.MONTH, -1);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//
//        System.out.println(calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR));
////        calendar.roll(Calendar.MONTH, 1);
////        System.out.println(calendar.getTime());
//        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date = formater.parse(data);
////            System.out.println(date);
////            System.out.println(formater2.format(date));
//        }
//        catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    public static void sendMoreEmails(MailWithAttachmentService mail) throws IOException, MessagingException {
        Session session = mail.getSession();
        Message message = mail.createMail(session, "kuhnimalina@yandex.ru");
        for (int i = 0; i < 10; i++) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kuhnimalina@yandex.ru"));
            mail.sendManyMail(message);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("info@dedal-service.ru"));
            mail.sendManyMail(message);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("avetti@list.ru"));
            mail.sendManyMail(message);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("info@avetti.ru"));
            mail.sendManyMail(message);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dedal-service@yandex.ru"));
            mail.sendManyMail(message);
        }
    }

    public static void readFile() throws IOException, ParseException {
        File file = new File("saveDate.txt");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date date2 = new Date();
        if (!file.exists()) {
            if (!file.createNewFile()) {
                return;
            }
        } else {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            date2 = formatter.parse(line);
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date2);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH) + " / " +calendar.get(Calendar.MONTH));

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(formatter.format(date));

        fileWriter.close();
    }
}
