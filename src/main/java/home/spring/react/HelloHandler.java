package home.spring.react;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Created by alex on 9/6/2015.
 */
@Component
@HandlerPath(path = "/")
public class HelloHandler implements HttpHandler {

    @Value("${mongo.db}")
    private String mongoDatabase;

    private final static String id="eefa89c4-ec21-11e4-b08b-b75697636679-8e488775";

    @Autowired
    private MongoClient mongoClient;

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.dispatch();
        MongoDatabase database = mongoClient.getDatabase(mongoDatabase);
        MongoCollection<Document> collection = database.getCollection("Person");
        collection.find().filter(Filters.eq("_id", id)).first((p, throwable) -> exchange.getResponseSender().send(((Document) p).toJson()));
    }
}
