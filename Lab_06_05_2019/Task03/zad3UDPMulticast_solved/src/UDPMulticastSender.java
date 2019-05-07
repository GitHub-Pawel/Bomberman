import java.io.IOException;
import java.net.*;

public class UDPMulticastSender {

    String groupAddress;
    InetAddress group;
    int port;

    public UDPMulticastSender(String groupAddress, int port) throws IOException {
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
