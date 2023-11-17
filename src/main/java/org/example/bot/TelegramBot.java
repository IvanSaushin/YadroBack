package org.example.bot;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Random;
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


        if (requestMessage.getText().equals("/help")) {
            defaultMsg(response, """
                    здесь будут команды которые т сможешшь ввести боту:
                    привет или салам
                    норм/нормально
                    сам иди/сам"""
            );
        } else if (requestMessage.getText().equals("/start")) {
            defaultMsg(response, "привет мой друг, я бот который может с тобой пообщеться, только не обижайся если будет очень адекватно");

//новая команда
        } else if (
                requestMessage.getText().equalsIgnoreCase("привет")                      ||
                        requestMessage.getText().equalsIgnoreCase("салам")
        ) {
            defaultMsg(response, "эсаламалекум!");
            defaultMsg(response, "как дела,бро?");

//новая команда
        } else if (
                requestMessage.getText().equalsIgnoreCase("у меня нормально, а у тебя?") ||
                        requestMessage.getText().equalsIgnoreCase("норм")                ||
                        requestMessage.getText().equalsIgnoreCase("нормально")           ||
                        requestMessage.getText().equalsIgnoreCase("нормик")
        ) {
            defaultMsg(response, "у меня все путем,я вот думаю позать тебя погулять, а потом пошел ты нахуц ебень");
//            onIdea(response);
            System.out.println("on Idea");
        } else if

//новая команда
        (requestMessage.getText().equalsIgnoreCase("сам иди") ||
                        requestMessage.getText().equalsIgnoreCase("сам")) {
            defaultMsg(response, "ты че блять выблядок ебаный вообще нахуй охуел я ьвоих родных в гробу ебал сын шалавы");

//новая команда
        } else if
        (requestMessage.getText().equalsIgnoreCase("я пидорас")) {
            defaultMsg(response, "все уже давно знают!");

//новая команда
        } else if
        (requestMessage.getText().equalsIgnoreCase("сколько время?")) {
            defaultMsg(response, "а тебя это ебать не должно");

//новая команда
        } else if (
                requestMessage.getText().equalsIgnoreCase("что делаешь?")            ||
                        requestMessage.getText().equalsIgnoreCase("что делаешь")     ||
                        requestMessage.getText().equalsIgnoreCase("чд?")             ||
                        requestMessage.getText().equalsIgnoreCase("чд") ||
                        requestMessage.getText().equalsIgnoreCase("чем занимаешься") ||
                        requestMessage.getText().equalsIgnoreCase("как настроение")  ||
                        requestMessage.getText().equalsIgnoreCase("как настроение?") ||
                        requestMessage.getText().equalsIgnoreCase("чем занимаешься?")

        ) {
            defaultMsg(response, "тебя это ебать не должно");

//новая команда
        } else if (requestMessage.getText().equalsIgnoreCase("/1"))
            defaultMsg(response, "привет еблан");
        else if (requestMessage.getText().equalsIgnoreCase("/2"))
            defaultMsg(response, "пошел нахуй сын шалавы эти кнопки сделанны для декора недоразвитый овощ");
        else if (requestMessage.getText().equalsIgnoreCase("/3"))
            defaultMsg(response, "сейчас я расскажу, как появилась Астрахань\n Как появилась Астрахань?\n" +
                    "В 1459-1556 годах Астрахань - главный город Астраханского ханства и окончательно занята русскими войсками и присоединена к Русскому государству в 1557году. Основанием современной Астрахани является сооружение в 1558 году новой деревянно-земляной крепости на высоком Заячьем холме, омываемом Волгой и её рукавами.");

//новая команда
        else if (
                requestMessage.getText().equalsIgnoreCase("Можно тебе нюдсы кинуть")          ||
                        requestMessage.getText().equalsIgnoreCase("Можно тебе нюдсы кинуть?") ||
                        requestMessage.getText().equalsIgnoreCase("Можна тебе нюдсы кинуть")  ||
                        requestMessage.getText().equalsIgnoreCase("Можно нюдсы кинуть")       ||
                        requestMessage.getText().equalsIgnoreCase("Можно нюдсы кинуть?"))
            defaultMsg(response, "конечно, только я не принимаю формат фото файлов по этому можешь отправить их мне на архив @tipohoshnacota");

//новая команда
        else if (
                requestMessage.getText().equalsIgnoreCase("иди нахуй") ||
                        requestMessage.getText().equalsIgnoreCase("ди нах")   ||
                        requestMessage.getText().equalsIgnoreCase("ди нахуй") ||
                        requestMessage.getText().equalsIgnoreCase("иди нах")  ||
                        requestMessage.getText().equalsIgnoreCase("пошел нахуй")
        ) {
            defaultMsg(response, "сам иди сын ободранной шлюхии и недоразвитого долбоеба");

//новая команда
        } else if (requestMessage.getText().equalsIgnoreCase("/ruletkame")) {
            Random random = new Random();
            int randomNumber = random.nextInt(2);
            System.out.println(randomNumber);

            defaultMsg(response, String.valueOf(randomNumber));

//новая команда
        } else if (requestMessage.getText().equalsIgnoreCase("/randomnumber")) {
            Random random = new Random();
            int randomNumber = random.nextInt(101);
            System.out.println(randomNumber);

            defaultMsg(response, String.valueOf(randomNumber));

        } else if (
                requestMessage.getText().equalsIgnoreCase("/pravila"))
            defaultMsg(response, "правила 1 игры:\n это игра называется рандомайзер\nв ней выгирывает тот у кого больше 50\n2 игра это подобие орла и решки\n 0 означает орел, а 1 означает решка"
            );

        else
        {
            defaultMsg(response, "нормально общайся неуч");
        }
    }

//        if (requestMessage.getText().startsWith("/")) {
//            entity.setStartWord("команда: ");
//            producerService.sendMessage( entity);
//        } else {
//            entity.setStartWord("мысль: ");
//            producerService.sendMessage( entity);
//            userService.insert(entity);
//        }


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

    private void sendPhoto(SendPhoto response, String msg) throws TelegramApiException {
        InputFile inputFile = new InputFile("");
        response.setPhoto(inputFile);
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
