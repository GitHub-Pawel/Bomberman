import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private int port;
    private Socket clientSocket;
    private InetAddress inetAddress;
    private ObjectInputStream objectInputStream;

    public Client(InetAddress inetAddress, int port){
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public void startConnection(){
        try {
            this.clientSocket = new Socket(this.inetAddress, this.port);
            this.objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }

    public String receiveMessage(){
        try {
            return this.objectInputStream.readUTF();
        }catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stopConnection(){
        try {
            this.clientSocket.close();
            this.objectInputStream.close();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}