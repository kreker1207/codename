package com.app.wordgame.service;

import com.app.wordgame.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public User createUser(String name){
        return new User (name,null);
    }
}
