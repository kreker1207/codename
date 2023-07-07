package com.app.wordgame.entity.DTO;

import com.app.wordgame.entity.Room;
import com.app.wordgame.entity.Team;
import com.app.wordgame.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class RoomManager {
    @Autowired
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

    public void addToTeam(User user, String neededTeamColor) {
        Map<String, Team> teamsMap = room.getTeamMap();

        // Remove user from all teams
        findAndRemoveUser(user);

        Team team = teamsMap.get(neededTeamColor);
        if (team != null) {
            List<User> members = team.getMembers();
            if (members == null) {
                members = new ArrayList<>();
                team.setMembers(members);
            }
            members.add(user);
        }
    }

    private void findAndRemoveUser(User findUser) {
        Map<String, Team> teamsMap = room.getTeamMap();

        teamsMap.forEach((s, team) -> {
            List<User> members = team.getMembers();
            if (members != null) {
                members.removeIf(member -> member.getName().equals(findUser.getName()));
            }
        });
    }

}
