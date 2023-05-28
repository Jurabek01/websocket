package mimsoft.io.smartcarwebsocket.controller;

import mimsoft.io.smartcarwebsocket.bot.TelegramBot;
import mimsoft.io.smartcarwebsocket.model.MessageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class SmartCarController {

    private final TelegramBot telegramBot;

    public SmartCarController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/status")
    public ResponseEntity<Void> postCommands(
            @RequestParam("engine") String engine,
            @RequestParam("block") String block,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude
    ) {
        telegramBot.sendTelegram(
                new MessageModel(
                        engine,
                        block,
                        latitude,
                        longitude
                )
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reaction")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("{\n" +
                "    \"engine\": \"start\",\n" +
                "    \"block\": \"on\"\n" +
                "}");
    }
}
