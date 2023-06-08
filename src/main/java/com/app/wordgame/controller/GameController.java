package com.app.wordgame.controller;

import com.app.wordgame.entity.Card;
import com.app.wordgame.service.GameService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping("/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<Card> generateCards(){
        return gameService.generateCards(5);
    }
}
