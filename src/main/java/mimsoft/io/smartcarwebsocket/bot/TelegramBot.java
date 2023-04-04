package mimsoft.io.smartcarwebsocket.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import mimsoft.io.smartcarwebsocket.cofig.MessageHandler;
import mimsoft.io.smartcarwebsocket.model.MessageModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    final MessageHandler messageHandler;

    public TelegramBot(BotConfig config, MessageHandler messageHandler) {
        this.config = config;
        this.messageHandler = messageHandler;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.getMessage() != null){

            log.info("message->" + update.getMessage().getText());
            log.info("ChatId->" + update.getMessage().getChatId());

            String message = update.getMessage().getText();

            int index = message.indexOf("@");

            String text = message.substring(1, index);

            String username = update.getMessage().getFrom().getUserName();

            try {
                messageHandler.sendMessage(username, text);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void sendTelegram(MessageModel message) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(json);
        sendMessage.setChatId(String.valueOf(-926234247));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
