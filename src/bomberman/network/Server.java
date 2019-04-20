package bomberman.network;

import bomberman.component.BoardForward;
import bomberman.engine.ServerEngine;
import bomberman.observers.ClientDisconnectedObserver;

import java.io.*;
import java.net.ServerSocket;

public class Server implements ClientDisconnectedObserver {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private ServerSocket serverSocketRx;    //Receiver socket
    private ServerSocket serverSocketTx;    //Transmit socket
    private ClientHandler [] clientHandlers;
    private Thread [] threadsOfClients;
    private int numberOfClients;
    private int portRx;
    private int portTx;
    private ServerEngine refToEngine;
    private BoardForward refToBoardForward;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public Server() throws IOException {
        this(65432, 65433, 1, null, null);
    }

    public Server(int portRx, int portTx) throws IOException {
        this(portRx, portTx, 1, null, null);
    }

    public Server(int portRx, int portTx, int numberOfClients, BoardForward boardForward, ServerEngine serverEngine) throws IOException {    //
        this.portRx = portRx;
        this.portTx = portTx;
        this.numberOfClients = numberOfClients;
        this.refToBoardForward = boardForward; //
        this.refToEngine = serverEngine;
        this.start();
    }

    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public void start() throws IOException {                    //TO DO: Why is throws exception needed?
        this.serverSocketRx = new ServerSocket(this.portRx);
        this.serverSocketTx = new ServerSocket(this.portTx);
        connect();                                              //Connect with clients
    }

    public void stop() throws IOException {                     //TO DO: Why is throws exception needed?
        this.stopThreads();                                     //Stop handling clients
        this.serverSocketRx.close();
        this.serverSocketTx.close();
    }

    public void connect() throws IOException {
        this.clientHandlers = new ClientHandler[this.numberOfClients];
        this.threadsOfClients = new Thread[this.numberOfClients];   //Each client is handled by another thread
        System.out.println("Waiting for clients ...");

        for (int i = 0; i<this.numberOfClients; ++i){
            this.clientHandlers[i] = new ClientHandler(this.serverSocketRx.accept(), this.serverSocketTx.accept(), i, this.refToBoardForward, this.refToEngine);    //
            this.clientHandlers[i].subscribe(this);
            this.refToEngine.getPlayers(i).setRealId(i);
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
            try {
                this.clientHandlers[i].stopHandler();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThreads(int id){
        this.threadsOfClients[id].interrupt();
    }

    public void broadcastBoardUpdate(){
        for (int i = 0; i<this.numberOfClients; ++i){
            this.clientHandlers[i].sendBoardForward();
        }
    }

    /********************************************************************
     *                Client Disconnected Observer                      *
     ********************************************************************/

    @Override
    public void exitConnection() {
        try {
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientHandler getClientHandlers(int id) {
        return clientHandlers[id];
    }
}