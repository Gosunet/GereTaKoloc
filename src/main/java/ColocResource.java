/**9gag
 * Created by gosunet on 01/12/15.
 */

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static spark.Spark.*;

public class ColocResource {

    private static final String API_CONTEXT = "/api/v1";
    private final ColocService colocService;

    public ColocResource(ColocService colocService){
        this.colocService = colocService;
        setupEndpoints();
    }

    //COLOC

    private void setupEndpoints(){

        // You have to create a coloc first then create and add a user to the coloc, so no new user directly when you create a coloc.
        // Otherwise ID bug in DATABASE because the User isn't in the database.

        //new coloc
        post(API_CONTEXT + "/colocs","application/json",(request, response) -> {
            colocService.createNewColoc(request.body());
            response.status(201);
            return response;
        });

        //get une coloc from NameColoc
        get(API_CONTEXT + "/colocs/:name","application/json",(request, response) ->
            colocService.find(request.params(":name")), new JsonTransformer());

        //get coloc from user login and mdp

        get(API_CONTEXT + "/login/:login/:mdp", "application/json", (request, response) ->
            colocService.findOneColoc(request.params(":login"),request.params(":mdp")), new JsonTransformer());



        //get all colocs
        get(API_CONTEXT + "/colocs","application/json",(request, response)
                -> colocService.findAll(),new JsonTransformer());

        //-------------------------------USER-----------------------------------------//

        //Get all users from coloc :name
        get(API_CONTEXT + "/colocs/:name/users", "application/json", (request, response) ->
            colocService.findAllUsers(request.params(":name")),new JsonTransformer());

        //Get an user from coloc :name
        get(API_CONTEXT + "/colocs/:name/users/:user","application/json",(request, response) ->
            colocService.findOneUser(request.params(":name"),request.params(":user")),new JsonTransformer());

        //Add an user in coloc :name

        post(API_CONTEXT + "/colocs/:name/users","application/json",(request,response) -> {
            colocService.addUser(request.params(":name"),request.body());
            response.status(201);
            return response;
        });

        //REGLE

        get(API_CONTEXT + "/colocs/:name/regles","application/json", (request, response) ->
            colocService.findRegles(request.params(":name")),new JsonTransformer());

        get(API_CONTEXT + "/colocs/:name/regles/:nb","application/json", (request, response) ->
            colocService.findOneRegle(request.params(":name"),request.params(":nb")),new JsonTransformer());

        post(API_CONTEXT + "/colocs/:name/regles","application/json", (request, response) -> {
            colocService.addRegle(request.params(":name"),request.body());
            response.status(201);
            return response;
        });

        delete(API_CONTEXT + "/colocs/:name/regles/:nb",(request, response) ->
            colocService.deleteRegle(request.params(":name"),request.params(":nb")),new JsonTransformer());

        //NOTE

        get(API_CONTEXT + "/colocs/:name/notes", "application/json", (request, response) ->
            colocService.findNotes(request.params(":name")),new JsonTransformer());

        post(API_CONTEXT + "/colocs/:name/notes", "application/json", (request, response) -> {
            colocService.addNote(request.params(":name"),request.body());
            response.status(201);
            return response;
        });

        delete(API_CONTEXT + "/colocs/:name/notes/:index","application.json", (request, response) ->
            colocService.deleteNote(request.params(":name"),request.params(":index")), new JsonTransformer());

        //TACHE
        get(API_CONTEXT + "/colocs/:name/users/:user/taches", "application/json", (request, response) ->
            colocService.findTaches(request.params(":name"),request.params(":user")), new JsonTransformer());

        post(API_CONTEXT + "/colocs/:name/users/:user/taches", "application/json", (request, response) -> {
            colocService.addTache(request.params(":name"),request.params(":user"),request.body());
            response.status(201);
            return response;
        });

        delete(API_CONTEXT + "/colocs/:name/users/:user/taches/:nomEvent", "application/json", (request, response) ->
            colocService.deleteTache(request.params(":name"), request.params(":user"), request.params(":nomEvent")),new JsonTransformer());

        get(API_CONTEXT + "/colocs/:name/charges/delete/:index", (request, response) ->
            colocService.deleteCharge(request.params(":name"),request.params(":index")),new JsonTransformer());

        //CHARGE

        get(API_CONTEXT + "/colocs/:name/charges","application/json", (request, response) ->
            colocService.findCharges(request.params(":name")),new JsonTransformer());

        post(API_CONTEXT + "/colocs/:name/charges","application/json",(request, response) -> {
            colocService.addCharge(request.params(":name"),request.body());
            response.status(201);
            return response;
        });

        delete(API_CONTEXT + "/colocs/:name/charges/:index", (request, response) ->
                colocService.deleteCharge(request.params(":name"),request.params(":index")),new JsonTransformer());


    }
}
