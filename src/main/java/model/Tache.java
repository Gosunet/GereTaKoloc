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
    private String date;
    private boolean urgency;
    private boolean done;
    private String heure;

    public Tache(String content, String date, boolean urgency) {
        this.id= new ObjectId();
        this.content = content;
        this.date = date;
        this.urgency = urgency;
        this.done = false;
    }

    // fot morphia

    private Tache(){
        this.id=new ObjectId();
        this.content="nope";
        this.date = "";
        this.urgency=false;
        this.done=false;
        this.heure = "";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedOn(String date) {
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
