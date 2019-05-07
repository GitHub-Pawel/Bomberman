import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static InetAddress stringToAddress(String address){
        try {
            return InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Client client = new Client(stringToAddress("localhost"), 7000);
        client.startConnection();
        System.out.println("Message from Server: \n" + client.receiveMessage());
        client.stopConnection();
    }
}
