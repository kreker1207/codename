package com.app.wordgame.controller;

import com.app.wordgame.entity.Card;
import com.app.wordgame.service.GameService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @MessageMapping("/cards/{roomId}")
    @SendTo("/topic/greetings/{roomId}")
    public List<Card> generateCards(){
        return gameService.generateCards(5);
    }

}
