package home.spring.react;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.web.WebApplicationInitializer;

import java.util.Collections;

/**
 * Created by alex on 9/5/2015.
 */
@Configuration
@ComponentScan("home.spring.react")
@Import(PropertiesConfiguration.class)
public class Starter extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Value("${threads}")
    private Integer threads;

    @Value("${workers}")
    private Integer workers;

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


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Starter.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
        UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory = new UndertowEmbeddedServletContainerFactory();
        undertowEmbeddedServletContainerFactory.setIoThreads(threads);
        undertowEmbeddedServletContainerFactory.setWorkerThreads(workers);
        return undertowEmbeddedServletContainerFactory;
    }
    @Bean
    public MongoDatabase mongoClient(){
        MongoCredential credential = MongoCredential.createMongoCRCredential(mongoUsername, mongoDatabase, mongoPassword.toCharArray());
        ConnectionString connectionString = new ConnectionString("mongodb://" + mongoHost + ":" + mongoPort);
        MongoClient client = MongoClients.create(MongoClientSettings.builder().credentialList(Collections.singletonList(credential))
                .clusterSettings(ClusterSettings.builder().applyConnectionString(connectionString).build()).build());
        return client.getDatabase(mongoDatabase);
    }
}
