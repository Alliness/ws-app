package alliness.zzone.socket;

import alliness.zzone.core.utils.Serializable;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SocketConnection extends Thread {

    private static final Logger log = Logger.getLogger(SocketConnection.class);

    public final Session                                         session;
    public final String                                          uuid;
    protected    List<SocketListener.OnClose>                    onConnectionClose;
    private      HashMap<String, List<SocketListener.OnMessage>> messageHandlers;


    public SocketConnection(Session session, String uuid) {
        this.session = session;
        this.uuid = uuid;

        onConnectionClose = new ArrayList<>();
        messageHandlers = new HashMap<>();
    }

    public void sendMessage(String route, JSONObject message) {
        try {
            session.getRemote().sendString(new JSONObject().put("route", route).put("data", message).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

    }

    public void onClose(SocketListener.OnClose action) {
        onConnectionClose.add(action);
    }

    public void onMessage(String action, SocketListener.OnMessage handler) {
        if (messageHandlers.containsKey(action)) {
            messageHandlers.get(action).add(handler);
        } else {
            List<SocketListener.OnMessage> list = new ArrayList<>();
            list.add(handler);
            messageHandlers.put(action, list);
        }
    }

    public void close(int code, String reason) {
        sendMessage("exit", new JSONObject().put("code", code).put("reason", reason));
    }

    void receive(String message) {
        SocketMessage msg = Serializable.deserialize(message, SocketMessage.class);
        if (msg != null) {
            if (messageHandlers.containsKey(msg.getRoute())) {
                for (SocketListener.OnMessage onMessage : messageHandlers.get(msg.getRoute())) {
                    onMessage.invoke(msg);
                }
            }
        } else {
            log.error("unable to deserialize message");
        }
    }
}
