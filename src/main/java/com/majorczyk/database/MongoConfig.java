package com.majorczyk.database;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * MongoDB configuration
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    /**
     * Gets database name
     * @return database name
     */
    @Override
    protected String getDatabaseName() {
        return "test";
    }

    /**
     * Sets mongo client's name and port
     * @return mongo client
     */
    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 8004);
    }
}
