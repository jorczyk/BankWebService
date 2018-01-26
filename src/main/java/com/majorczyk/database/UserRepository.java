package com.majorczyk.database;

import com.majorczyk.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface extending spring MongoRepository to access users' repository
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds user by login
     * @param login - user's login
     * @return User object with given login
     */
    User findByLogin(String login);

}
