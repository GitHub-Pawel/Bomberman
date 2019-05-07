import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

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
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteArrayOutputStream);
            stream.writeUTF(message);
            stream.close();
            byte [] messageForwardByteArray = byteArrayOutputStream.toByteArray();


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GZIPOutputStream compressed = new GZIPOutputStream(outputStream);
            compressed.write(messageForwardByteArray);
            compressed.close();
            byte [] compressedMessageByteArray = outputStream.toByteArray();

            this.objectOutputStream.write(compressedMessageByteArray);
            this.objectOutputStream.reset();

            System.out.println("Data size before compression: " + byteArrayOutputStream.toByteArray().length + " [B]");
            System.out.println("Data size after compression: " + outputStream.toByteArray().length + " [B]");

            byteArrayOutputStream.close();
            outputStream.close();
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
        } catch (NullPointerException | IOException e){            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
