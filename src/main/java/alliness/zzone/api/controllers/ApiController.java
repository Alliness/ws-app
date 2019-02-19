package alliness.zzone.api.controllers;

import alliness.zzone.core.utils.ConfigLoader;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class ApiController {
    public static Object getConfig(Request request, Response response) {
        return new JSONObject().put("socket",
                new JSONObject().put("host", ConfigLoader.getProperty("websocket.host"))
                                .put("port", ConfigLoader.getProperty("websocket.port")
                ));
    }
}
