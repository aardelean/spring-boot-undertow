package home.boot.services;

import co.paralleluniverse.fibers.SuspendExecution;
import com.google.common.collect.ImmutableList;
import home.boot.blocking.CommentDao;
import home.boot.entities.Comment;
import home.boot.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by alex on 9/26/2015.
 */
@Service
public class ComplexService {
    @Autowired
    private MongoService mongoService;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ExternalClient externalClient;

    private static final List<String> externalUrl = ImmutableList.of(
                "http://www.google.com",
                "http://ro.blastingnews.com/",
                "http://www.bogdandiaconu.ro/",
                "http://www.nationalisti.ro/",
                "http://192.168.1.145:15672/"
            );

    public List<String> calculateSomething() throws ExecutionException, InterruptedException, SuspendExecution {
        List<String> allNames = new LinkedList<>();
        Person person = mongoService.fetchFirstPerson();
        allNames.add(person.getName());
        Iterator<Comment> commentIterator = commentDao.findAll().iterator();
        while(commentIterator.hasNext()){
            Comment comment = commentIterator.next();
            allNames.add(comment.getAuthor());
        }
        Collections.sort(allNames, String.CASE_INSENSITIVE_ORDER);
        externalUrl.stream().forEach((p) -> {
            try {
                externalClient.available(p);
            } catch (SuspendExecution suspendExecution) {
                suspendExecution.printStackTrace();
            }
        });
        return allNames;
    }

}
