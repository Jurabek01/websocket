package mimsoft.io.smartcarwebsocket.cofig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageHandler messageHandler;

    public WebSocketConfig(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler, "/api/v1/ws");
    }
}
