import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ObjectOutputStream objectOutputStream;

    public Server(int port){
        this.port = port;
    }

    public void startConnection(){
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.clientSocket = this.serverSocket.accept();
            this.objectOutputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }

    public void sendMessageUTF(String message){
        try{
            this.objectOutputStream.reset();
            this.objectOutputStream.writeUTF(message);
            this.objectOutputStream.flush();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection(){
        try {
            if(this.clientSocket.isConnected()){
                this.clientSocket.close();
                this.serverSocket.close();
                this.objectOutputStream.close();
            }
        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
