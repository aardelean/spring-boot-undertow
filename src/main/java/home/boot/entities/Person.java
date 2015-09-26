package home.boot.entities;


import lombok.Data;
import org.bson.Document;

/**
 * Created by alex on 9/26/2015.
 */
@Data
public class Person {
    private int age;
    private String gender;
    private String name;
    public Person(){}
    public Person(int age, String gender, String name) {
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    public static Person convert(Document document){
        return new Person(
                document.getDouble("age").intValue(),
                document.getString("gender"),
                document.getString("name")
        );
    }
}
