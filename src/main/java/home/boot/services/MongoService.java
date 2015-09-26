package home.boot.services;

import co.paralleluniverse.fibers.SuspendExecution;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import home.boot.entities.Person;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by alex on 9/26/2015.
 */
@Repository
public class MongoService {

    private final static String id="eefa89c4-ec21-11e4-b08b-b75697636679-8e488775";
    @Autowired
    private MongoDatabase mongoDatabase;


    public Person fetchFirstPerson() throws SuspendExecution, ExecutionException, InterruptedException {
        CompletableFuture<Person> result = new CompletableFuture<>();
        MongoCollection<Document> collection = mongoDatabase.getCollection("Person");
        collection.find().filter(Filters.eq("_id", id)).first((p, throwable) ->result.complete(Person.convert((Document)p)));
        return result.get();
    }
}
