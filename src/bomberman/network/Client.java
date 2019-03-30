package bomberman.network;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(){}
    public Client(String ip, int port){}

    public void tryToConnect(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
        } catch (Exception e) {
            try {
                Thread.sleep(1000, 33);       //try connect 30 times per second
            } catch (InterruptedException e1) {
            }
            tryToConnect(ip, port);
        }
    }

    public void startConnection(String ip, int port) throws IOException {
        tryToConnect(ip, port);
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    public void sendMessage(String msg){
        this.out.println(msg);
    }


    public void receiveMessage() throws IOException {
        String resp = in.readLine();
        System.out.println(resp);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("localhost", 65432);
        client.sendMessage("hello server");
        client.receiveMessage();
        client.stopConnection();
    }
}