package com.majorczyk.database;

import com.majorczyk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Piotr on 2018-01-19.
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    public void run(ApplicationArguments args) {
        userRepository.deleteAll();
        userRepository.save(new User("user1", "user1"));
        userRepository.save(new User("user2", "user2"));
//        userRepository.save(new User("user3", Validator.encryptPassword("user3")));

    }
}
