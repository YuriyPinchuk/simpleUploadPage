package com.praktyka.demo.controllers;

import com.praktyka.demo.dao.DocumentDAO;
import com.praktyka.demo.models.Document;
import com.praktyka.demo.utils.Type;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
public class MainController  {
    private DocumentDAO documentDAO;

    //шлях для збереження файлів
    private static String UPLOAD_FOLDER = "C:\\test\\";

    @GetMapping("/home")
    public String showUpload() {
        return "home";
    }

    @PostMapping("/home")
    public String fileUpload(@RequestParam MultipartFile file,
                                   @RequestParam String type,
                                   @RequestParam String dateTime,
                                   Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Будь-ласка виберіть файл і спробуйте ще раз");
            return "status";
        }

        try {
            // читаємо і записуємо файл
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            //перетворюємо отриману стрінгу в об'єкт localDateTime
            LocalDateTime endLocalDateTime;
            try {
                String[] ts = dateTime.split("T");
                String splitedDateTime = ts[0] + " " + ts[1];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                endLocalDateTime = LocalDateTime.parse(splitedDateTime, formatter);

            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println(e);
                endLocalDateTime = null;
            }
            // перетворюємо отриману стрінгу в enum
            Type tempType;
            if ("image".equals(type)){
                tempType = Type.IMAGE;
            } else {
                tempType = Type.TEXT;
            }

            // записуємо отримані дані в новий об'єкт
            Document document = new Document();
            document.setPath(path.toAbsolutePath().toString());
            document.setType(tempType);
            document.setSize(file.getSize());
            document.setStartLocalDateTime(LocalDateTime.now());

            // зберігаємо новий об'єкт в базу даних
            documentDAO.save(document);

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("message", "Файл завантажений успішно");
        return "status";
    }
}
