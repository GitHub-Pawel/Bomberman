package bomberman.network;

public class ClientTest {
    public static void main(String[] args) {
        Client client = new Client("localhost", 65432);
        client.run();
        //client.closeSocket();
    }
}
