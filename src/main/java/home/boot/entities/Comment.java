package home.boot.entities;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by alex on 9/20/2015.
 */
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String author;

    private String text;

    public Comment(Long id, String author, String text) {
        this.id = id;
        this.author = author;
        this.text = text;
    }

    private Comment(){}
}
