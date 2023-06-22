package com.app.wordgame.controller;

import com.app.wordgame.service.GameService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestGameController {
    private final GameService gameService;

    @PostMapping("/roomId")
    public String createRoom(){
      return gameService.createRoom();
    }
}
