package com.app.wordgame.controller;

import com.app.wordgame.entity.DTO.EnterTeamData;
import com.app.wordgame.entity.Room;
import com.app.wordgame.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/roomId")
    public String createRoom(){
        return roomService.createRoom();
    }
//    @MessageMapping("/team/{roomId}")
//    @SendTo("/topic/team/{roomId}")
//    public Room enterTeam(@RequestBody EnterTeamData enterTeamData){
//       return roomService.enterTeam(enterTeamData);
//    }
    @MessageMapping("/team/{roomId}/join")
    @SendTo("/topic/team/{roomId}")
    public Room joinTeam(@DestinationVariable String roomId, @RequestBody EnterTeamData enterTeamData) {
        return roomService.enterTeam(enterTeamData);
    }
}
