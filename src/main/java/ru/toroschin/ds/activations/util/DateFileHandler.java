package ru.toroschin.ds.activations.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.Scanner;

@Component
@Data
@Slf4j
@NoArgsConstructor
public class DateFileHandler {

    public String readDateFromFile() throws IOException {
        File file = new File("saveDate.txt");
        if (!file.exists()) {
            log.info("Не найден файл saveDate.txt");
            if (!file.createNewFile()) {
                log.info("Ошибка создания файла saveDate.txt");
            }
        } else {
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                scanner.close();
                return line;
            }
        }
        throw new FileSystemException("Ничего не удалось прочитать в файле saveDate.txt");
    }

    public void writeDateToFile(String date) {
        try {
            File file = new File("saveDate.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(date);
            fileWriter.close();
        } catch (IOException e) {
            log.warn("Ошибка записи в файл saveDate.txt " + e.getMessage() + Arrays.toString(e.getStackTrace()));
        }
    }

}
