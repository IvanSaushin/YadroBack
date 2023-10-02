package org.example;

import org.example.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5500", maxAge = 3600)
public class FormController {

    @Autowired
    TelegramBot bot;

    @GetMapping("/form")
    public ResponseEntity<Form> getAdmin() throws TelegramApiException {
        List<String> users = new ArrayList<>();
//        model.addAttribute("person", new User());
        Form form = new Form("Ivan", "9999999", "email");
        System.out.println("Поступил GET запрос на /api/form");
        System.out.println(form);

//        bot.sendMsgForm(form);

        return ResponseEntity.ok().body(form);
    }

//    @GetMapping("/form")
//    public String getAdmin2() {
//        List<String> users = new ArrayList<>();
////        model.addAttribute("person", new User());
//        Form form = new Form("Ivan", "89608554040", "email");
//        System.out.println("Поступил GET запрос на /api/form");
//        System.out.println(form);
//        return "ResponseEntity.ok().body(form)";
//    }

    @PostMapping("/form2")
    public void create(@RequestBody Form form) {
        System.out.println("Поступил POST запрос на /api/form");

        System.out.println(form);
//        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/form")
    public ResponseEntity<String> create2(@RequestBody Form form) throws TelegramApiException {
        System.out.println("Поступил POST запрос на /api/form");

        System.out.println(form);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        String time = LocalDateTime.now().format(formatter);

        String timeFormat = time.replace(" ", " в ");
//        String template = """
//                Поступила заявка от
//                Имя: %s,
//                Номер: %s,
//                Почта: %s,
//                Время поступления заявки: %s.
//                """;

        String template = """
                Зявка!
                %s поступила заявка от
                Имя: %s
                Номер: %s
                Почта: %s
                """;

//        String msg = template.format(form.getName(), form.getPhone(), form.getEmail(), time);
        String msg = String.format(template, timeFormat, form.getName(), form.getPhone(), form.getEmail());

        bot.sendMsgForm(msg);

        return ResponseEntity.ok().body("success");
    }
}
