import java.io.IOException;

public class Runner {

    String groupAddress;
    int port;

    UDPMulticastServer server;
    UDPMulticastClient client;

    public Runner(String groupAddress, int port) throws IOException{
        this.groupAddress = groupAddress;
        this.port = port;
        this.server = new UDPMulticastServer(this.groupAddress, this.port);
        this.client = new UDPMulticastClient(this.groupAddress, this.port);

        Thread clientThread = new Thread(this.client);
        clientThread.start();
    }


    public UDPMulticastServer getServer() {
        return server;
    }

    public UDPMulticastClient getClient() {
        return client;
    }
}
