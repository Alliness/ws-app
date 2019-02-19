package alliness.zzone.engine;

import alliness.zzone.socket.SocketConnection;

public class User {

    private SocketConnection connection;

    public User(SocketConnection connection) {
        this.connection = connection;
        connection.onClose(() -> World.getInstance().remove(this));
        connection.onMessage("exit", message -> exit());
    }

    public void exit() {
        connection.close(1000, "user exit");
    }

    public SocketConnection getConnection() {
        return connection;
    }
}
