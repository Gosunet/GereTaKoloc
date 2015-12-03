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
    private Date date;

    public Note(String content, Date date) {
        this.id = new ObjectId();
        this.content = content;
        this.date = date;
    }
    private Note(){
        this.id = new ObjectId();
        this.content="no_content";
        this.date = null;
    }
    public String getContent() {
        return content;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }
}
