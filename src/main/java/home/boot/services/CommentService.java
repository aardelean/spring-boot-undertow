package home.boot.services;

import co.paralleluniverse.fibers.SuspendExecution;
import home.boot.blocking.CommentDao;
import home.boot.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alex on 9/26/2015.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    public Iterable<Comment> getAllComments() throws SuspendExecution{
        return commentDao.findAll();
    }
}
