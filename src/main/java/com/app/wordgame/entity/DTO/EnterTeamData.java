package com.app.wordgame.entity.DTO;

import com.app.wordgame.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnterTeamData {
    public User user;
    public String teamColor;
}
