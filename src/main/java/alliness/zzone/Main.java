package alliness.zzone;

import alliness.zzone.api.Api;
import alliness.zzone.engine.World;
import alliness.zzone.socket.WebSocketServer;

public class Main {

    public static void main(String[] args) {
        Api api = Api.getInstance();
        WebSocketServer socket = WebSocketServer.getInstance();
        World world = World.getInstance();

        world.run();
        api.run();
        socket.run();

    }

}
