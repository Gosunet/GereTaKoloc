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
public class Tache {
    @Id
    private ObjectId id = new ObjectId();
    private String content;
    private Date createdOn = new Date();
    private boolean urgency;
    private boolean done;

    public Tache(String content, Date createdOn, boolean urgency) {
        this.id= new ObjectId();
        this.content = content;
        this.createdOn = createdOn;
        this.urgency = urgency;
        this.done = false;
    }

    // fot morphia

    private Tache(){
        this.id=new ObjectId();
        this.content="nope";
        this.createdOn = null;
        this.urgency=false;
        this.done=false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isUrgency() {
        return urgency;
    }

    public void setUrgency(boolean urgency) {
        this.urgency = urgency;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
