package bomberman.network;

import bomberman.component.BoardForward;
import bomberman.observers.BoardForwardObserver;
import bomberman.observers.KeyboardObserver;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable{
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private Socket clientSocketTx;      // Transmit socket
    private Socket clientSocketRx;      // Receive socket
    private String ip;
    private int portTx;
    private int portRx;
    private BoardForwardObserver boardForwardObserver;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    private boolean continueConnection;
    private int id;

    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public Client(){
        this("localhost", 65432, 65433);
    }

    public Client(String ip, int portTx, int portRx){
        this.ip = ip;
        this.portTx = portTx;
        this.portRx = portRx;
        this.continueConnection = true;
    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public void startConnection(){
        try {
            this.clientSocketTx = new Socket(this.ip, this.portTx);
            this.clientSocketRx = new Socket(this.ip, this.portRx);
        } catch (Exception e) {
            try {
                Thread.sleep(1000, 33);       //try connect 30 times per second
            } catch (InterruptedException e1) {
            }
            startConnection();
        }

        try {
            this.objectOutputStream = new ObjectOutputStream(this.clientSocketTx.getOutputStream());
            this.objectInputStream = new ObjectInputStream(this.clientSocketRx.getInputStream());


            this.id = objectInputStream.readInt();
            System.out.print("My id is: ");
            System.out.println(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendKeyEvent(int keyEvent){
        try {
            this.objectOutputStream.reset();
            this.objectOutputStream.writeObject(keyEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BoardForward receiveBoard(){     //
        BoardForward boardForward = null;
        try{
            boardForward = (BoardForward) this.objectInputStream.readObject();
        } catch (IOException e) {
            //e.printStackTrace();
            try {
                this.stopConnection();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return boardForward;
    }


    public void stopConnection() throws IOException {
        this.continueConnection = false;
        clientSocketTx.close();
        clientSocketRx.close();
    }


    public void subscribe(BoardForwardObserver o){
        this.boardForwardObserver = o;
    }

    public void unsubscribe(KeyboardObserver o){
        if (this.boardForwardObserver == o){
            this.boardForwardObserver = null;
        }
    }

    @Override
    public void run() {
        while (this.continueConnection){
            this.boardForwardObserver.boardUpdate(this.receiveBoard());
        }
    }

    public int getId() {
        return id;
    }
}