package com.app.wordgame.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @MessageMapping("/")
    public String home(Model model) {
        return "../static/index";
    }
}
