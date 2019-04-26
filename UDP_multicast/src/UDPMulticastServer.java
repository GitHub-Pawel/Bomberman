import java.io.IOException;
import java.net.*;

public class UDPMulticastServer {

    String groupAddress; //class D IP address except for 224.0.0.0 (224.0.0.1 to 239.255.255.255)
    InetAddress group;
    int port;

    public UDPMulticastServer(String groupAddress, int port) throws IOException {
        this.groupAddress = groupAddress;
        this.port = port;
        this.group = InetAddress.getByName(this.groupAddress);
    }

    public void send(String message) throws IOException{
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), this.group, this.port);
        socket.send(packet);
        socket.close();
    }



}
