package mimsoft.io.smartcarwebsocket.cofig;

import mimsoft.io.smartcarwebsocket.bot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageHandler extends TextWebSocketHandler {

    private final TelegramBot telegramBot;

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public MessageHandler(@Qualifier("telegramBot1") TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessions.put(message.getPayload(), session);
        logger.info("Received message: " + message.getPayload() + " and put--> " + sessions.get(message.toString()));
        session.sendMessage(new TextMessage("server received your message"));
        telegramBot.sendText(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connected to: " + session.getRemoteAddress());
        for ( String i : sessions.keySet()) {
            logger.info("member of session: " + sessions.get(i));
        }
        telegramBot.sendText(Objects.requireNonNull(session.getRemoteAddress()).toString());
    }

    public void sendMessage(String uuid, String message) throws Exception {
        for (String i: sessions.keySet()) {
            logger.info("we have sessions--> " + sessions.get(i) + " with uuid--> " + i);
        }
        WebSocketSession session = sessions.get(new TextMessage(uuid).getPayload());
        logger.info("Sending message--> " + message + " with uuid--> " + uuid + " to--> " + session);
        if (session != null) {
            session.sendMessage(new TextMessage(message));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Connection closed: " + status.getReason());
    }
}
