package model;


import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by gosunet on 28/11/15.
 */
@Entity
public class Regle {
    @Id
    private ObjectId id = new ObjectId();
    private String content;
    private String number;

    public Regle(String content, String number) {
        this.id = new ObjectId();
        this.content = content;
        this.number = number;
    }
    //for morphia

    private Regle(){
        this.id = new ObjectId();
        this.content="dumb content";
        this.number="0";
    }

    public String getContent() {
        return content;
    }

    public String getNumber() {
        return number;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

