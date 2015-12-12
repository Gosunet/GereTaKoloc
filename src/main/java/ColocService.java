import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import model.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateException;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

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

    public User findOneUser(String nameColoc, String nameUser){
        List<User> users = find(nameColoc).getUsers();
        for (User user : users)
        {
        if(user.getName().equals(nameUser)){
            return user;
        }
        }
        return null;
    }

    public void addUser(String nameColoc, String body){
        User user = new Gson().fromJson(body, User.class);
        datastore.save(user);

        Coloc coloc = find(nameColoc);
        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        datastore.findAndDelete(queryOne.filter("name =", nameColoc));
        coloc.addUsers(user);
        datastore.save(coloc);
        //
    }

    //TODO Delete User

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

    public List<Regle> deleteRegle(String nameColoc, String nbRegle){
        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        Coloc coloc= datastore.findAndDelete(queryOne.filter("name =", nameColoc));

        List<Regle> regles = coloc.getRegles();
        int i=0;
        for (Regle regle: regles){
            if (regle.getNumber().equals(nbRegle)){
                regles.remove(i);
            }
            i++;
        }

        datastore.save(regles);
        coloc.setRegles(regles);
        datastore.save(coloc);
        return regles;
    }

    //TODO teste delete regle

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

    //TODO delete tache

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

    //TODO charges

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

}
