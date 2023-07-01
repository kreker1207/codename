package com.app.wordgame.entity.DTO;

import com.app.wordgame.entity.Room;
import com.app.wordgame.entity.Team;
import com.app.wordgame.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RoomManager {
    private Room room;

    public void createRoom(){
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        room = new Room(id.substring(1,8));
    }

    public String getRoomId(){
        return room.getId();
    }
    public Room getRoom(){
        return  room;
    }

    public void addToTeam(User user, String neededTeamColor){
       Map<String,Team> teamsMap = room.getTeamMap();
       Team team = teamsMap.get(neededTeamColor);
       if(team!=null){
           team.getMembers().add(user);
       }
    }

}
