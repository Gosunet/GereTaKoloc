import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by gosunet on 01/12/15.
 */
public class JsonTransformer implements ResponseTransformer{

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
