package com.majorczyk.database;

import com.majorczyk.model.Account;
import com.majorczyk.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Piotr on 2018-01-15.
 */
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);
//    User findByToken(String token);

}
