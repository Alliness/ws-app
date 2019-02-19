package alliness.zzone.socket;

import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ConnectionStorage {

    private static ConnectionStorage                  instance;
    private final  List<SocketConnection>             connections;
    private        SocketListener.OnConnectionHandler onConnectionHandler;

    public static ConnectionStorage getInstance() {
        if (instance == null) {
            instance = new ConnectionStorage();
        }
        return instance;
    }

    private ConnectionStorage() {
        connections = Collections.synchronizedList(new ArrayList<>());
    }

    public SocketConnection addConnection(Session session) {
        UUID             uuid       = UUID.randomUUID();
        SocketConnection connection = new SocketConnection(session, uuid.toString());
        onConnectionHandler.invoke(connection);
        connection.run();
        connection.sendMessage("register", new JSONObject().put("uuid", connection.uuid));
        connections.add(connection);
        return connection;
    }

    public void onConnection(SocketListener.OnConnectionHandler handler) {
        this.onConnectionHandler = handler;
    }

    public SocketConnection getConnection(String uuid) {
        for (int i = 0; i < connections.size() - 1; i++) {
            if (connections.get(i).uuid.equals(uuid)) {
                return connections.get(i);
            }
        }
        return null;
    }

    public void removeConnection(SocketConnection connection) {
        System.out.println("ConnectionStorage.removeConnection");
        connections.remove(connection);
    }
}
