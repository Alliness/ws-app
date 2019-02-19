package alliness.zzone.api;

import alliness.zzone.api.controllers.ApiController;
import alliness.zzone.core.utils.ConfigLoader;
import alliness.zzone.core.utils.Dir;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import static spark.Spark.*;
import static spark.Spark.port;

public class Api extends Thread{

    private static final Logger log = Logger.getLogger(Api.class);
    private static       Api    instance;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    public void run() {

        port(Integer.parseInt(ConfigLoader.getProperty("api.port")));
        externalStaticFileLocation(Dir.WEB);

        before("/*",(request, response) -> {
            response.type("application/json");
        });

        path("/app", () -> {
            get("/config", ApiController::getConfig);
        });

        notFound((request, response) -> {
            response.status(404);
            response.body(new JSONObject().put("success", false).put("error", 404).put("message", "page not found").toString());
            return response.body();
        });

        log.info(String.format("Api Web Server port: %s", port()));

    }

}
