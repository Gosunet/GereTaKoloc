/**
 * Created by gosunet on 01/12/15.
 */

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

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

        //get une coloc
        get(API_CONTEXT + "/colocs/:name","application/json",(request, response) ->
            colocService.find(request.params(":name")), new JsonTransformer());

        //get all colocs
        get(API_CONTEXT + "/colocs","application/json",(request, response)
                -> colocService.findAll(),new JsonTransformer());

        //-------------------------------USER-----------------------------------------//

        //Get all users from coloc :name
        get(API_CONTEXT + "/colocs/:name/users", "application/json", (request, response) ->
            colocService.findAllUsers(request.params(":name")),new JsonTransformer());

        //Get an user from coloc :name
        get(API_CONTEXT + "/colocs/:name/:user","application/json",(request, response) ->
            colocService.findOneUser(request.params(":name"),request.params(":user")),new JsonTransformer());

        //Add an user in coloc :name

        post(API_CONTEXT + "/colocs/:name/users","application/json",(request,response) -> {
            colocService.addUser(request.params(":name"),request.body());
            response.status(201);
            return response;
        });



        //REGLE

    }
}
