package alliness.zzone.socket;

public interface SocketListener {

    interface OnConnectionHandler {
        void invoke(SocketConnection connection);
    }

    interface OnMessage {
        void invoke(SocketMessage message);
    }

    interface OnClose{
        void invoke();
    }
}
