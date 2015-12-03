/**
 * Created by gosunet on 30/11/15.
 */

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import model.Coloc;
import model.Tache;
import model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;
import java.util.List;

import static spark.Spark.*;


public class Bootstrap {
    public static void main(String[] args) {
       // get("/", (request, response) -> "Hello World");

        final Morphia morphia = new Morphia();

        morphia.mapPackage("model");

        final Datastore datastore2 = morphia.createDatastore(new MongoClient("localhost"),"geretacolocV20");
        datastore2.ensureIndexes();

        final User user = new User("jean","dujardin",100,"cacao","1234","jean@dujardin.fr");
        final User user2 = new User("jeanddddd","dujardddddin",100,"cadddcao","1ddd234","cacao@enib.fr");
        final Tache tache1 = new Tache("écouter chanter ando",new Date(),false);
        datastore2.save(tache1);
        user.addTache(tache1);
        datastore2.save(user);
        datastore2.save(user2);

        final Query<User> queryUser = datastore2.createQuery(User.class);

        final List<User> users = queryUser.asList();

        System.out.println(users.get(0).getName());
        System.out.println(users.get(0).getId());

        final Coloc coloc = new Coloc("32 rue du machin", user,"cooloc");
        datastore2.save(coloc);

        final Coloc coloc1 = new Coloc("45 rue de pateouchnic", user,"trocoloc");
        coloc1.addUsers(user2);
        datastore2.save(coloc1);

        final Query<Coloc> queryColoc = datastore2.createQuery(Coloc.class);
        final List<Coloc> colocs = queryColoc.asList();

        System.out.println(colocs.get(0).getName());
        System.out.println(colocs.get(0).getId());

        new ColocResource(new ColocService(datastore2));



/* test de la mongo bdd + morphia

        final Datastore datastore2 = morphia.createDatastore(new MongoClient("localhost"),"geretacolocV4");
        datastore2.ensureIndexes();
        //test


        final Tache tache = new Tache("course", new Date(),false);
        datastore2.save(tache);

       //final User jean = new User("jean","dujardin",100,"cacao","1234");
        //final Coloc pandal = new Coloc("32 rue du docteur gestin", jean,"ocbbb");
        //pandal.setId(new ObjectId());
        //datastore2.save(pandal);


        //final Coloc panda = new Coloc("32 rue du docteur gestin", jean,"pandalocbbb");
        //datastore2.save(panda);
        final Query<Tache> query = datastore2.createQuery(Tache.class);

        final List<Tache> taches = query.asList();

        System.out.println(taches.get(0).getContent());
        System.out.println(taches.get(0).getId());

        final User user = new User("jean","dujardin",100,"cacao","1234");
        datastore2.save(user);

        final Query<User> queryUser = datastore2.createQuery(User.class);

        final List<User> users = queryUser.asList();

        System.out.println(users.get(0).getName());
        System.out.println(users.get(0).getId());

        final Coloc coloc = new Coloc("32 rue du machin", user,"cooloc");
        datastore2.save(coloc);

        final Query<Coloc> queryColoc = datastore2.createQuery(Coloc.class);
        final List<Coloc> colocs = queryColoc.asList();

        System.out.println(colocs.get(0).getName());
        System.out.println(colocs.get(0).getId());

*/
    }
}