package bomberman.network;

import bomberman.component.Board;
import bomberman.component.BoardForward;
import bomberman.observers.BoardObserver;
import bomberman.observers.KeyboardObserver;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class Client implements Runnable{
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private Socket clientSocket;
    private String ip;
    private int port;
    private BoardObserver boardObserver;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public Client(){
        this("localhost", 65432);
    }

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public void startConnection() {
        try {
            this.clientSocket = new Socket(this.ip, this.port);
        } catch (Exception e) {
            try {
                Thread.sleep(1000, 33);       //try connect 30 times per second
            } catch (InterruptedException e1) {
            }
            startConnection();
        }
    }


    public void sendKeyEvent(int keyEvent){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(keyEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BoardForward receiveBoard(){     //
        BoardForward boardForward = null;
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
            boardForward = (BoardForward) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return boardForward;
    }


    public void stopConnection() throws IOException {
        clientSocket.close();
    }


    public void subscribe(BoardObserver o){
        this.boardObserver = o;
    }

    public void unsubscribe(KeyboardObserver o){
        if (this.boardObserver == o){
            this.boardObserver = null;
        }
    }

    @Override
    public void run() {
        while (true){
            this.boardObserver.boardUpdate(this.receiveBoard());
        }
    }
}


/*
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection();
        client.sendKeyEvent(KeyEvent.VK_RIGHT);
        Board board = client.receiveBoard();
        System.out.println(board.getTableLength());
        client.stopConnection();
    }
*/