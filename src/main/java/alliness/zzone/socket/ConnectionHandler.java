package alliness.zzone.socket;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class ConnectionHandler {

    private static final Logger log = Logger.getLogger(ConnectionHandler.class);
    private SocketConnection connection;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        connection.onConnectionClose.forEach(SocketListener.OnClose::invoke);
        ConnectionStorage.getInstance().removeConnection(connection);
        log.info(String.format("[%s]Disconnected[code:%s]. %s", connection.uuid, statusCode, reason));
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        connection.close(400, t.getMessage());
        log.info(String.format("[%s]Error. %s", connection.uuid, t.getMessage()));
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        connection = ConnectionStorage.getInstance().addConnection(session);
        log.info(String.format("[%s]Connected.", connection.uuid));
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        connection.receive(message);
    }
}
