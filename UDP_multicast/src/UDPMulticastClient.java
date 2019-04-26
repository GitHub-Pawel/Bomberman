import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPMulticastClient implements Runnable {

    String groupAddress; //class D IP address except for 224.0.0.0 (224.0.0.1 to 239.255.255.255)
    int port;

    InetAddress group;
    MulticastSocket multicastSocket;

    String message;

    MessageObserver observer;

    public UDPMulticastClient(String groupAddress, int port) throws IOException {
        this.groupAddress = groupAddress;
        this.port = port;
        this.group = InetAddress.getByName(this.groupAddress);
        this.multicastSocket = new MulticastSocket(port);
        this.multicastSocket.joinGroup(this.group);
    }

    public void receive() throws IOException {
        byte[] buffer = new byte[1024];

        while(true) {
            System.out.println("Waiting for multicast message...");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("Message received: " + msg);
            this.message = msg;
            observer.showMessage();
        }
    }

    public void leave() throws IOException {
        this.multicastSocket.leaveGroup(this.group);
        this.multicastSocket.close();
    }

    @Override
    public void run() {
        try {
            receive();
        } catch (IOException e) {}
    }

    public void subscribe(MessageObserver o){
        observer = o;
    }
    public void unsubscribe(MessageObserver o){
        if (observer == o){
            observer = null;
        }
    }

    public String getMessage() {
        return message;
    }
}
