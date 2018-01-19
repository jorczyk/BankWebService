package com.majorczyk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Piotr on 2018-01-19.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private ObjectId id;

//    @Setter
//    @Getter
//    private List<String> accounts;

    @Setter
    @Getter
    @Column(unique = true)
    private String login;

    @Setter
    @Getter
    @Column
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
