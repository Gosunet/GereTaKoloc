package model;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Created by gosunet on 28/11/15.
 */
@Entity
public class Note {
    @Id
    private ObjectId id = new ObjectId();
    private String content;
    private String date;

    public Note(String content, String date) {
        this.id = new ObjectId();
        this.content = content;
        this.date = date;
    }
    private Note(){
        this.id = new ObjectId();
        this.content="no_content";
        this.date = "no date";
    }
    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
