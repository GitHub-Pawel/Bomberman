package bomberman.network;

public class ServerTest {
    public static void main(String[] args) {
        Server server = new Server(65432);
        server.connect(1);
        //server.closeServer();
    }
}
