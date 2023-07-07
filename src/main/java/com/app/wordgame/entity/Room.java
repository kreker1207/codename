package com.app.wordgame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class Room {
    private String id;
    private Map<String,Team> teamMap;

    public Room(String id) {
        this.id = id;
        this.teamMap = createTeamsMap();
    }
    private Map<String, Team> createTeamsMap() {
        Team orangeTeam = new Team("Orange",null);
        Team blueTeam = new Team("Blue",null);
        Team spectator = new Team("spectator",null);
        Map<String, Team> teamMap = new HashMap<>();
        teamMap.put(orangeTeam.getColor(),orangeTeam);
        teamMap.put(blueTeam.getColor(),blueTeam);
        teamMap.put(spectator.getColor(),spectator);
        return teamMap;
    }
}
