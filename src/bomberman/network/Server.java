package bomberman.network;

import bomberman.component.Board;
import bomberman.engine.ServerEngine;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private ServerSocket serverSocket;
    private Thread [] threadsOfClients;
    private int numberOfClients;
    private int port;
    private Board refToBoard;
    private ServerEngine refToEngine;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public Server() throws IOException {
        this(65432, 1, null, null);
    }

    public Server(int port) throws IOException {
        this(port, 1, null, null);
    }

    public Server(int port, int numberOfClients, Board board, ServerEngine serverEngine) throws IOException {
        this.port = port;
        this.numberOfClients = numberOfClients;
        this.refToBoard = board;
        this.refToEngine = serverEngine;
        this.start();
    }

    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public void start() throws IOException {                    //TO DO: Why is throws exception needed?
        this.serverSocket = new ServerSocket(this.port);        //Create a socket to connect with this server
        connect();                                              //Connect with clients
    }

    public void stop() throws IOException {                     //TO DO: Why is throws exception needed?
        this.stopThreads();                                     //Stop handling clients
        this.serverSocket.close();                              //Close the server socket
    }

    public void connect() throws IOException {
        this.threadsOfClients = new Thread[this.numberOfClients];   //Each client is handled by another thread
        System.out.println("Waiting for clients ...");

        for (int i = 0; i<this.numberOfClients; ++i){
            this.threadsOfClients[i] = new Thread(new ClientHandler(this.serverSocket.accept(), i, this.refToBoard, this.refToEngine));
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
        Server server = new Server(65432);
        //server.stop();
    }
}