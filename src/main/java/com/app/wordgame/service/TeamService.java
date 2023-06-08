package com.app.wordgame.service;

import com.app.wordgame.entity.Team;
import com.app.wordgame.entity.User;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    public Team[] createTeams(){
        Team orangeTeam = new Team ("Orange",null);
        Team blueTeam = new Team ("Blue",null);
        return new Team[]{orangeTeam,blueTeam};
    }
    public Team addUserToTeam(User user,Team team,Boolean master){
        user.master = master;
        team.members.add(user);
        return team;
    }

}
