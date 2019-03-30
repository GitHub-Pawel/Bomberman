package bomberman.network;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    //=============dane==============
    private ServerSocket serverSocket;
    private int numberOfClients;
    private int port;
    private Thread [] threadsOfClients;


    //====================konstruktory===================
    public Server() throws IOException {
        this.port = 65432;          //default port number
        this.numberOfClients = 1;   //default number of clients
        this.start();
    }

    public Server(int port) throws IOException {
        this.port = port;
        this.numberOfClients = 1;   //default number of clients
        this.start();
    }

    public Server(int port, int numberOfClients) throws IOException {
        this.port = port;
        this.numberOfClients = numberOfClients;
        this.start();
    }

    //====================metody===================
    public void start() throws IOException {    //TO DO: Why is throws exception needed?
        this.serverSocket = new ServerSocket(this.port);     //Create a socket to connect with this server
        connect();                                      //Connect with clients
    }

    public void stop() throws IOException {     //TO DO: Why is throws exception needed?
        this.stopThreads();
        this.serverSocket.close();
    }

    public void connect() throws IOException {
        this.threadsOfClients = new Thread[this.numberOfClients];
        System.out.println("Waiting for clients ...");

        for (int i = 0; i<this.numberOfClients; ++i){
            this.threadsOfClients[i] = new Thread(new ClientHandler(this.serverSocket.accept(), i));
            System.out.println("Connected Client NO.:" + i);
        }

        System.out.println("All clients have connected");
        this.startThreads();
    }


    public void startThreads(){
        for (int i = 0; i<this.numberOfClients; ++i){
            this.threadsOfClients[i].start();
        }
    }

    public void stopThreads(){
        for (int i = 0; i<this.numberOfClients; ++i){
            this.threadsOfClients[i].stop();
        }
    }


    public static void main(String[] args) throws IOException {     //TO DO: Why is throws exception needed?
        Server server = new Server(65432, 1);
        //server.start(65432);
        //server.stop();
    }
}