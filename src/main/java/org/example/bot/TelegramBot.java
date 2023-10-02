package org.example.bot;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {

//    @Autowired
//    BotConfig botConfig;

    private Message requestMessage = new Message();
    private final SendMessage response = new SendMessage();
//    private final Producer producerService;
//    private final UserService userService;

    private final String botUsername;
    private final String botToken;

    public TelegramBot(
            TelegramBotsApi telegramBotsApi,
            @Value("${bot.name}") String botUsername,
            @Value("${bot.token}") String botToken
    ) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;

        telegramBotsApi.registerBot(this);
    }

    /**
     * Этот метод вызывается при получении обновлений через метод GetUpdates.
     *
     * @param request Получено обновление
     */
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update request) {
        requestMessage = request.getMessage();
        response.setChatId(requestMessage.getChatId().toString());
        System.out.println(requestMessage.getChatId().toString());

//        var entity = new Form(
//                0, requestMessage.getChat().getUserName(),
//                requestMessage.getText());

        if (request.hasMessage() && requestMessage.hasText())
            log.info("Working onUpdateReceived, request text[{}]", request.getMessage().getText());

        if (requestMessage.getText().equals("/start"))
            defaultMsg(response, "Напишите команду для показа списка мыслей: \n " + "/idea - показать мысли");
        else if (requestMessage.getText().equals("/idea"))
//            onIdea(response);
            System.out.println("on Idea");
        else
            defaultMsg(response, "Я записал вашу мысль :) \n ");

        log.info("Working, text[{}]", requestMessage.getText());

//        if (requestMessage.getText().startsWith("/")) {
//            entity.setStartWord("команда: ");
//            producerService.sendMessage( entity);
//        } else {
//            entity.setStartWord("мысль: ");
//            producerService.sendMessage( entity);
//            userService.insert(entity);
//        }
    }

    /**
     * Метод отправки сообщения со списком мыслей - по команде "/idea"
     *
     * @param response - метод обработки сообщения
     */
//    private void onIdea(SendMessage response) throws TelegramApiException {
//        if (userService.getUserList().isEmpty()) {
//            defaultMsg(response, "В списке нет мыслей. \n");
//        } else {
//            defaultMsg(response, "Вот список ваших мыслей: \n");
//            for (User txt : userService.getUserList()) {
//                response.setText(txt.toString());
//                execute(response);
//            }
//        }
//    }

    /**
     * Шабонный метод отправки сообщения пользователю
     *
     * @param response - метод обработки сообщения
     * @param msg - сообщение
     */
    private void defaultMsg(SendMessage response, String msg) throws TelegramApiException {
        response.setText(msg);
        execute(response);
    }

    public void sendMsgForm(String msg) throws TelegramApiException {
//        String chatId = "30499721"; // Ivan
        String chatId = "362315382"; // Dishka
        response.setChatId(chatId);
        response.setText(msg);
        execute(response);

    }
}
