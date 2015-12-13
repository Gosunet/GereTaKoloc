package model;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gosunet on 28/11/15.
 */
@Entity
public class User {
    @Id
    private ObjectId id= new ObjectId();
    private String login;
    private String name;
    private String surname;
    private int color;
    private String avatar;
    private String mdp;
    private String description;
    private String mail;

    @Reference
    private List<Tache> taches = new ArrayList<Tache>();

    public User(String name, String surname, int color, String login, String mdp, String mail) {
        this.id=new ObjectId();
        this.name = name;
        this.surname = surname;
        this.color = color;
        this.login = login;
        this.mdp = mdp;
        this.avatar="default";
        this.description="";
        this.mail=mail;

    }

    //for morphia

    private User(){
        this.id = new ObjectId();
        this.name="noName";
        this.login="toto";
        this.surname="null";
        this.color=100;
        this.avatar="/colocataires/img/titile.jpg";
        this.mdp="azerty";
        this.description="";
        this.mail="";
        this.taches=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public List<Tache> getTaches(){
        return taches;
    }

    public void setTaches(List<Tache> taches){
        this.taches=taches;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTache(Tache tache){
        this.taches.add(tache);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
