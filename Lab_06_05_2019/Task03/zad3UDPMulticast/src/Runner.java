import java.io.IOException;

public class Runner {

    String groupAddress;
    int port;

    UDPMulticastSender server;
    UDPMulticastReceiver client;

    public Runner(String groupAddress, int port) throws IOException{
        this.groupAddress = groupAddress;
        this.port = port;
        this.server = new UDPMulticastSender(this.groupAddress, this.port);
        this.client = new UDPMulticastReceiver(this.groupAddress, this.port);

        Thread clientThread = new Thread(this.client);
        clientThread.start();
    }


    public UDPMulticastSender getServer() {
        return server;
    }

    public UDPMulticastReceiver getClient() {
        return client;
    }
}
