package home.boot.blocking;

import home.boot.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by alex on 9/20/2015.
 */
@RepositoryRestResource(path = "comments")// default does this.
public interface CommentDao extends CrudRepository<Comment, Long> {
}
