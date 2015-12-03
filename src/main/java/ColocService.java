import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import model.Coloc;
import model.Regle;
import model.Tache;
import model.User;
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

    public void deleteColoc(String nameColoc){
        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        datastore.findAndDelete(queryOne.filter("name =",nameColoc));
    }

    //TODO test deleteColoc


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

    //TODO deleteUser()

    //REGLE

    public List<Regle> findRegles(String nameColoc){
        return find(nameColoc).getRegles();
    }

    public Regle findOneRegle(String nameColoc, int numberRegle){
        for(Regle regle: findRegles(nameColoc)){
            if (regle.getNumber().equals(numberRegle)){
                return regle;
            }
        }
        return null;
    }

    public void addRegle(String nameColoc, String body){
        Regle regle = new new Gson().fromJson(body, Regle.class);
        datastore.save(regle);

        final Query<Coloc> queryOne = datastore.createQuery(Coloc.class);
        Coloc coloc= datastore.findAndDelete(queryOne.filter("name =", nameColoc));

        coloc.addRegle(regle);
        datastore.save(coloc);
    }
    //TODO delete regle


    //TODO NOTES
    //TODO TACHES

    //Tâche

    //public List<Tache> findAllTask(String user){
      //  return ;;
//    }


}
