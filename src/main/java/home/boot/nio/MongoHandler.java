package home.boot.nio;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import home.boot.HandlerPath;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by alex on 9/6/2015.
 */
//@Component
//@HandlerPath(path = "/mongo")
public class MongoHandler implements HttpHandler {

    private final static String id="eefa89c4-ec21-11e4-b08b-b75697636679-8e488775";

    @Autowired
    private MongoDatabase mongoDatabase;

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.dispatch();
        MongoCollection<Document> collection = mongoDatabase.getCollection("Person");
        collection.find().filter(Filters.eq("_id", id)).first((p, throwable) -> exchange.getResponseSender().send(((Document) p).toJson()));
    }
}
