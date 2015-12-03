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
        post(API_CONTEXT + "/colocs","application/json",(request, response) -> {
            colocService.createNewColoc(request.body());
            response.status(201);
            return response;
        });

        get(API_CONTEXT + "/colocs/:name","application/json",(request, response) ->
            colocService.find(request.params(":name")), new JsonTransformer());

        get(API_CONTEXT + "/colocs","application/json",(request, response)
                -> colocService.findAll(),new JsonTransformer());

        // USER

        get(API_CONTEXT + "/colocs/:name/users", "application/json", (request, response) ->
            colocService.findAllUsers(request.params(":name")),new JsonTransformer());

        get(API_CONTEXT + "/colocs/:name/:user","application/json",(request, response) ->
            colocService.findOneUser(request.params(":name"),request.params(":user")),new JsonTransformer());

        //REGLE



    }
}
