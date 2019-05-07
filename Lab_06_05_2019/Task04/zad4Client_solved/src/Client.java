import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.zip.GZIPInputStream;

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
            byte [] receiveData = this.objectInputStream.readAllBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(receiveData);

            byte [] result = new byte[1100];
            GZIPInputStream decompressed = new GZIPInputStream(inputStream);
            decompressed.read(result);
            decompressed.close();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
            ObjectInputStream objectInputStream2 = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream2.readUTF();

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