package alliness.zzone.engine;

import alliness.zzone.socket.ConnectionStorage;
import alliness.zzone.socket.SocketConnection;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World extends Thread {


    private static final Logger     log = Logger.getLogger(World.class);
    private static       World      instance;
    private              List<User> users;


    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    private World() {

        users = Collections.synchronizedList(new ArrayList<>());
        ConnectionStorage.getInstance().onConnection(this::addUser);
    }

    void addUser(SocketConnection connection) {
        User user = new User(connection);
        connection.sendMessage("world/add", new JSONObject().put("lol", "kek"));
        users.add(user);
    }

    void remove(User user) {
        System.out.println("World.remove");
        users.remove(user);
    }

    @Override
    public void run() {

    }
}
