/**
 * Created by gosunet on 24/11/15.
 */

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.Coloc;
import model.User;
import org.bson.Document;

import java.awt.*;
import java.util.ArrayList;

import static spark.Spark.*;

public class GereTaColocAPI {
    public static void main(String[] args) {
        get("/", (request, response) -> "Hello World");


        //Gestion de la base de donnée Mongo
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mydb");
        MongoCollection<Document> coll = mongoDatabase.getCollection("testCollection");

        mongoClient.setWriteConcern(WriteConcern.JOURNALED);

      //  ArrayList<Coloc> colocs = new ArrayList<>();

        //Coloc pandaLoc = new Coloc("pandaLoc","32 rue du docteur gestin");

       // pandaLoc.getUsers();

        Document doc = new Document("name","MongoDB")
                .append("type","database")
                .append("count",1)
                .append("info", new Document("x",203).append("y",102));


        coll.insertOne(doc);

        ArrayList<Document> documents = new ArrayList<Document>();

        for (int i = 0; i< 100; i++){
            documents.add(new Document("i",i));
        }

        coll.insertMany(documents);

        System.out.println(coll.count());

        Document mydoc = coll.find().first();
        System.out.println(mydoc.toJson());

        MongoCursor<Document> cursor = coll.find().iterator();
        try{
            while (cursor.hasNext()){
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        //mydoc = coll.find(eq("i",71)).first();






    }



}
