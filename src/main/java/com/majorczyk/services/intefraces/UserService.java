package com.majorczyk.services.intefraces;

import com.majorczyk.model.User;

/**
 * Created by Piotr on 2018-01-11.
 */
public interface UserService {
    void save(User user);
    User findByLoginAndPassword(String login,String password);
    User findByLogin(String token);

}
