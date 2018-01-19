package com.majorczyk.services.implementations;

import com.majorczyk.database.UserRepository;
import com.majorczyk.model.User;
import com.majorczyk.services.intefraces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Piotr on 2018-01-11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User user = userRepository.findByLoginAndPassword(login,password);
//        if(user == null
        return user;
    }

    @Override
    public User findByLogin(String token) {
        return userRepository.findByLogin(token);
    }
}
