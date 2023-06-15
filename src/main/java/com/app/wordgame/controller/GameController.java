package com.app.wordgame.controller;

import com.app.wordgame.entity.Card;
import com.app.wordgame.service.GameService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @MessageMapping("/cards")
    @SendTo("/topic/greetings")
    public List<Card> generateCards(){
        System.out.println("Client connected /////////////////");
        return gameService.generateCards(5);
    }

}
