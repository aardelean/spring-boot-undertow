package home.spring.react;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ServerSettings;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import java.util.Collections;

/**
 * Created by alex on 9/5/2015.
 */
@Configuration
@ComponentScan("home.spring.react")
@Import(PropertiesConfiguration.class)
public class ReactAndSpringDataRestApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Value("${port}")
    private Integer serverPort;

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
        return application.sources(ReactAndSpringDataRestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactAndSpringDataRestApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
        UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory = new UndertowEmbeddedServletContainerFactory();
        undertowEmbeddedServletContainerFactory.addBuilderCustomizers(new UndertowBuilderCustomizer() {

            @Override
            public void customize(Undertow.Builder builder) {
                builder.setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        exchange.getResponseSender().send("error");
                    }
                });
                builder.addHttpListener(serverPort, "0.0.0.0");
            }
        });
        undertowEmbeddedServletContainerFactory.setIoThreads(threads);
        undertowEmbeddedServletContainerFactory.setWorkerThreads(workers);
        return undertowEmbeddedServletContainerFactory;
    }
    @Bean
    public MongoClient mongoClient(){
        MongoCredential credential = MongoCredential.createMongoCRCredential(mongoUsername, mongoDatabase, mongoPassword.toCharArray());
        ConnectionString connectionString = new ConnectionString("mongodb://" + mongoHost + ":" + mongoPort);
        MongoClient client = MongoClients.create(MongoClientSettings.builder().credentialList(Collections.singletonList(credential))
                .clusterSettings(ClusterSettings.builder().applyConnectionString(connectionString).build()).build());
        return client;
    }
}
