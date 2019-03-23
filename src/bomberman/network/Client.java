package bomberman.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Socket socket;
    private String serverAddress;
    private int port;

    public Client(String serverAddress, int port){
        this.serverAddress = serverAddress;
        this.port = port;
        try {
            this.socket = new Socket(serverAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getMessage(){
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Server say: " + message);

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setMessage(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            String message = new String("Hello Server");
            oos.writeObject(message);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        this.getMessage();
        this.setMessage();
        this.closeSocket();
    }
}
