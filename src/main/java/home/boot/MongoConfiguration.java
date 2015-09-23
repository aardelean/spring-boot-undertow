package home.boot;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by alex on 9/12/2015.
 */
@Configuration
public class MongoConfiguration {
    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.username}")
    private String mongoUsername;

    @Value("${mongo.password}")
    private String mongoPassword;

    @Value("${mongo.db}")
    private String mongoDatabase;

    @Value("${mongo.port}")
    private Integer mongoPort;

    @Bean
    public MongoDatabase mongoClient(){
        ConnectionString connectionString = new ConnectionString("mongodb://" + mongoHost + ":" + mongoPort);
        MongoClient client = MongoClients.create(MongoClientSettings.builder()
                .clusterSettings(ClusterSettings.builder().applyConnectionString(connectionString).build()).build());
        return client.getDatabase(mongoDatabase);
    }
}
