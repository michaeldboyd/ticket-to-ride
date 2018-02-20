import org.java_websocket.client.WebSocketClient;

public class DummyClient {
    private static DummyClient dummy = new DummyClient();
    public static DummyClient instance() { return dummy; }
    private DummyClient() {}
    public static void resetClient() { dummy = new DummyClient();}

    // *** DATA MEMBERS w/ getters & setters ***
    WebSocketClient socket;
    public WebSocketClient getSocket() {
        return socket;
    }

    public void setSocket(WebSocketClient socket) {
        this.socket = socket;
    }




}
