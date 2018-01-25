package com.majorczyk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.List;

/**
 * Bank customer model
 */

@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * Database unique id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)//?
    private String id;

    /**
     * User login
     */
    @Setter
    @Getter
    @Column(unique = true)
    private String login;

    /**
     * User password
     */
    @Setter
    @Getter
    @Column
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
