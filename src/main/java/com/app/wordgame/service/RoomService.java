package com.app.wordgame.service;

import com.app.wordgame.entity.DTO.EnterTeamData;
import com.app.wordgame.entity.DTO.RoomManager;
import com.app.wordgame.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomManager roomManager;

    public String createRoom() {
        roomManager.createRoom();
        return roomManager.getRoomId();
    }

    public Room enterTeam(EnterTeamData enterTeamData) {
        roomManager.addToTeam(enterTeamData.user,enterTeamData.teamColor);
        return roomManager.getRoom();
    }
}
