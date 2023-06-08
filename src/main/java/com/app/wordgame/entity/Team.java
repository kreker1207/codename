package com.app.wordgame.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Team {
    public String color;
    public List<User> members;
}
