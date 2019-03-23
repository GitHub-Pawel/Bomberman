package bomberman.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private static ServerSocket server;
    private int numberOfClients;
    private Thread [] threads;

    public Server(int port) {
        try {
            server = new ServerSocket(port);    //Creates a server socket, bound to the specified port.
        } catch (IOException e) {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

    public void closeServer(){
        this.stopThreads();
        try {
            this.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void connect(int numberOfClients){
        this.numberOfClients = numberOfClients;
        this.threads = new Thread[numberOfClients];

        System.out.println("Waiting for clients ...");

        for (int i = 0; i<numberOfClients; ++i){
            try {
                this.threads[i] = new Thread(new ClientHandler(server.accept(), i));
                System.out.println("Connected Client NO.:" + i);
            } catch (IOException e) {
                System.err.println( e.getMessage() );
                e.printStackTrace();
            }
        }

        System.out.println("All clients have connected");

        this.startThreads();
    }


    public void startThreads(){
        for (int i = 0; i<numberOfClients; ++i){
            this.threads[i].start();
        }
    }

    public void stopThreads(){
        for (int i = 0; i<numberOfClients; ++i){
            this.threads[i].stop();
        }
    }
}

class ClientHandler implements Runnable{
    private final Socket socket;
    private final int id;

    public ClientHandler(Socket socket, int id){
        this.socket = socket;
        this.id = id;
    }

    public void getMessage(){
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String message = (String) ois.readObject();
            System.out.println("Client NO: " + id + "  say: " + message);

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
            String message = new String("Hello Client NO: " + id);
            oos.writeObject(message);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.getMessage();
        this.setMessage();
        this.closeHandler();
    }

    public void closeHandler(){
        try {
            this.socket.close();
            System.out.println("Connection with host NO.: " + id + "has been closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
