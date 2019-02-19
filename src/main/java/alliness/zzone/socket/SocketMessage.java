package alliness.zzone.socket;

import alliness.zzone.core.utils.Serializable;
import org.json.JSONObject;

public class SocketMessage extends Serializable{

    private String route;
    private JSONObject data;


    public SocketMessage(String route, JSONObject data) {
        this.route = route;
        this.data = data;
    }

    public String getRoute() {
        return route;
    }

    public JSONObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return serialize(this).toString();
    }
}
