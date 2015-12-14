import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import model.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateException;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gosunet on 01/12/15.
 */
public class ColocService {
    private final Datastore datastore;
    private final Query<Coloc> query;

    public ColocService(Datastore datastore){
        this.datastore=datastore;
        this.query = datastore.createQuery(Coloc.class);
    }


    //Service COLOC

    public List<Coloc> findAll(){
        List<Coloc> colocs = query.asList();
        return colocs;
    }

    public void createNewColoc(String body){
        Coloc coloc = new Gson().fromJson(body, Coloc.class);
        datastore.save(coloc);
        return;
    }

    public Coloc find(String name){
        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        return queryOne.filter("name =", name).get();
    }

    public Coloc findOneColoc(String login, String mdp){
        final Query<User> queryUser = datastore.createQuery(User.class);
        if (queryUser.filter("login =",login).filter("mdp =",mdp).get()!=null){
            final Query<Coloc> queryColoc = datastore.createQuery(Coloc.class);
            queryColoc.field("users").hasAnyOf(queryUser);
            return queryColoc.get();
        };
        return null;
    }

    public void deleteColoc(String nameColoc){
        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        datastore.findAndDelete(queryOne.filter("name =",nameColoc));
    }


    //Service USER

    public List<User> findAllUsers(String nameColoc){
        return find(nameColoc).getUsers();
    }

    public User findOneUser(String nameColoc, String login){
        List<User> users = find(nameColoc).getUsers();
        for (User user : users)
        {
        if(user.getLogin().equals(login)){
            return user;
        }
        }
        return null;
    }

    public void addUser(String nameColoc, String body){
        User user = new Gson().fromJson(body, User.class);
        datastore.save(user);

        Coloc coloc = find(nameColoc);
        coloc.addUsers(user);
        datastore.save(coloc);
        //
    }

    //REGLE

    public List<Regle> findRegles(String nameColoc){
        return find(nameColoc).getRegles();
    }

    public Regle findOneRegle(String nameColoc, String numberRegle){
        for(Regle regle: findRegles(nameColoc)){
            if (regle.getNumber().equals(numberRegle)){
                return regle;
            }
        }
        return null;
    }

    public void addRegle(String nameColoc, String body){
        Regle regle = new Gson().fromJson(body, Regle.class);
        datastore.save(regle);

        Coloc coloc= find(nameColoc);
        coloc.addRegle(regle);
        datastore.save(coloc);
    }

    public Coloc deleteRegle(String nameColoc, String index){

        Coloc coloc = find(nameColoc);

        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        datastore.delete(queryOne.filter("name =",nameColoc));

        final Query<Regle> queryRegle = datastore.createQuery((Regle.class));
        List<Regle> regles = coloc.getRegles();
        Regle regle = regles.get(Integer.parseInt(index));
        datastore.delete(queryRegle.filter("_id =", regle.getId()));
        regles.remove(Integer.parseInt(index));

        coloc.setRegles(regles);
        datastore.save(coloc);
        return coloc;
    }


    //NOTE

    public List<Note> findNotes(String nameColoc){
        return find(nameColoc).getNotes();
    }

    public void addNote(String nameColoc,String body){
        Note note = new Gson().fromJson(body, Note.class);
        datastore.save(note);

        Coloc coloc = find(nameColoc);

        coloc.addNote(note);
        datastore.save(coloc);
    }

    public Coloc deleteNote(String nameColoc, String index){

        Coloc coloc = find(nameColoc);

        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        datastore.delete(queryOne.filter("name =",nameColoc));

        final Query<Note> queryNotes = datastore.createQuery((Note.class));
        List<Note> notes = coloc.getNotes();
        Note note = notes.get(Integer.parseInt(index));
        datastore.delete(queryNotes.filter("_id =", note.getId()));
        notes.remove(Integer.parseInt(index));

        coloc.setNotes(notes);
        datastore.save(coloc);
        return coloc;
    }



    //TACHE

    public List<Tache> findTaches(String nameColoc, String nameUser){
        return findOneUser(nameColoc,nameUser).getTaches();
    }

    public void addTache(String nameColoc, String nameUser, String body){
        Tache tache = new Gson().fromJson(body, Tache.class);
        datastore.save(tache);

        User user = findOneUser(nameColoc, nameUser);
        user.addTache(tache);

        datastore.save(user);

    }

    public Coloc deleteTache(String nameColoc, String nameUser, String nomEvent){

        Coloc coloc = find(nameColoc);

//        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
//        datastore.delete(queryOne.filter("name =",nameColoc));
//
//        User user = findOneUser(nameColoc,nameUser);
//        final Query<User> queryUser = datastore.createQuery(User.class);
//        datastore.delete(queryUser.filter("login =", nameUser));
//        List<User> users = coloc.getUsers();
//        users.remove(user);
//
//
//        final Query<Tache> queryTache = datastore.createQuery((Tache.class));
//        List<Tache>  taches = user.getTaches();
//        Tache tache = datastore.findAndDelete(queryTache.filter("content =", nomEvent));
//
//        taches.remove(tache);
//
//        user.setTaches(taches);
//        users.add(user);
//        coloc.setUsers(users);
//
//        datastore.save(coloc);
        return coloc;
    }

   //CHARGE

    public List<Charge> findCharges(String nameColoc){
        return find(nameColoc).getCharges();
    }

    public void addCharge(String nameColoc, String body){
        Charge charge = new Gson().fromJson(body, Charge.class);
        datastore.save(charge);

        Coloc coloc = find(nameColoc);
        coloc.addCharge(charge);
        datastore.save(coloc);

    }

    public Coloc deleteCharge(String nameColoc, String index){

        Coloc coloc = find(nameColoc);

        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        datastore.delete(queryOne.filter("name =",nameColoc));

        final Query<Charge> queryCharge = datastore.createQuery((Charge.class));
        List<Charge> charges = coloc.getCharges();
        Charge charge = charges.get(Integer.parseInt(index));
        datastore.delete(queryCharge.filter("_id =", charge.getId()));
        charges.remove(Integer.parseInt(index));

        coloc.setCharges(charges);
        datastore.save(coloc);
        return coloc;
    }

}
