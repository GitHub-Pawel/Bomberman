package bomberman.network;

import bomberman.component.BoardForward;
import bomberman.engine.ServerEngine;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    //private ServerSocket serverSocket;
    private ServerSocket serverSocketRx;    //Receiver socket
    private ServerSocket serverSocketTx;    //Transmit socket
    private ClientHandler [] clientHandlers;
    private Thread [] threadsOfClients;
    private int numberOfClients;
    //private int port;
    private int portRx;
    private int portTx;
    //private Board refToBoard;
    private ServerEngine refToEngine;
    private BoardForward refToBoardForward;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public Server() throws IOException {
        this(65432, 65433, 1, null, null);
    }

    public Server(int portRx, int portTx) throws IOException {
        //this(port, 1, null, null);
        this(portRx, portTx, 1, null, null);
    }

    public Server(int portRx, int portTx, int numberOfClients, BoardForward boardForward, ServerEngine serverEngine) throws IOException {    //
        this.portRx = portRx;
        this.portTx = portTx;
        this.numberOfClients = numberOfClients;
        //this.refToBoard = board;
        this.refToBoardForward = boardForward; //
        this.refToEngine = serverEngine;
        this.start();
    }

    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public void start() throws IOException {                    //TO DO: Why is throws exception needed?
        //this.serverSocket = new ServerSocket(this.port);        //Create a socket to connect with this server
        this.serverSocketRx = new ServerSocket(this.portRx);
        this.serverSocketTx = new ServerSocket(this.portTx);
        connect();                                              //Connect with clients
    }

    public void stop() throws IOException {                     //TO DO: Why is throws exception needed?
        this.stopThreads();                                     //Stop handling clients
        //this.serverSocket.close();                              //Close the server socket
        this.serverSocketRx.close();
        this.serverSocketTx.close();
    }

    public void connect() throws IOException {
        this.clientHandlers = new ClientHandler[this.numberOfClients];
        this.threadsOfClients = new Thread[this.numberOfClients];   //Each client is handled by another thread
        System.out.println("Waiting for clients ...");

        for (int i = 0; i<this.numberOfClients; ++i){
            this.clientHandlers[i] = new ClientHandler(this.serverSocketRx.accept(), this.serverSocketTx.accept(), i, this.refToBoardForward, this.refToEngine);    //
            this.threadsOfClients[i] = new Thread(this.clientHandlers[i]);
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

    public void broadcastBoardUpdate(){
        for (int i = 0; i<this.numberOfClients; ++i){
            this.clientHandlers[i].sendBoardForward();
        }
    }
}

/*
public static void main(String[] args) throws IOException {     //TO DO: Why is throws exception needed?
        Server server = new Server(65432);
        //server.stop();
    }
 */