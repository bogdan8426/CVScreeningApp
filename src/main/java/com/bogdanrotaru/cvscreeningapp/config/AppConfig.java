package com.bogdanrotaru.cvscreeningapp.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /*
     * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
     */
    public @Bean
    MongoClient mongoClient() {
        String user = System.getProperty("user");
        String pass = System.getProperty("pass");
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://"+user+":"+pass+"@bogdancluster.3mxsw.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("test");

        return mongoClient;
    }
}
