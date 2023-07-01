package com.app.wordgame.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    public String id;
    public Map<String,Team> teamMap;

    public Room(String id) {
        this.id = id;
        this.teamMap = createTeamsMap();
    }
    private Map<String,Team> createTeamsMap(){
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
