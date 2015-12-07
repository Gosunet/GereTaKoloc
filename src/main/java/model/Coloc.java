package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gosunet on 28/11/15.
 */

@Entity
public class Coloc {
    @Id private ObjectId id = new ObjectId();
    private String name;
    private String address;

    @Reference
    private List<User> users = new ArrayList<User>();
    @Reference
    private List<Note> notes = new ArrayList<Note>();
    @Reference
    private List<Regle> regles = new ArrayList<Regle>();

    public Coloc(String address, User user, String name) {
        this.address = address;
        this.users.add(user);
        this.name = name;
    }

    public Coloc(String name, String address, List<User> users, List<Note> notes, List<Regle> regles) {
        this.name = name;
        this.address = address;
        this.users = users;
        this.notes = notes;
        this.regles = regles;

    }

    //for morphia

    private Coloc() {
        this.id = new ObjectId();
        this.users=null;
        this.notes=null;
        this.regles=null;
        this.name="no_name";
        this.address="no_adress";
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Regle> getRegles() {
        return this.regles;
    }

    public void setRegles(List<Regle> regles) {
        this.regles = regles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUsers(User user) {
        this.users.add(user);
    }
    public void setUsers(List<User> users){
        this.users=users;
    }

    public List<Note> getNotes() {
        return notes;
    }
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addRegle(Regle regle) {
        this.regles.add(regle);
    }

    public void addNote(Note note){
        this.notes.add(note);
    }


}
