package mimsoft.io.smartcarwebsocket;

import mimsoft.io.smartcarwebsocket.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
public class SmartCarWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCarWebSocketApplication.class, args);
    }

    @Bean(name = "telegramBot1")
    public TelegramBot telegramBot1(){
        return new TelegramBot();
    }
}
