package bomberman.network;

import bomberman.component.Board;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class Client {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private Socket clientSocket;
    private String ip;
    private int port;


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
    public void startConnection(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
        } catch (Exception e) {
            try {
                Thread.sleep(1000, 33);       //try connect 30 times per second
            } catch (InterruptedException e1) {
            }
            startConnection(ip, port);
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

    public Board receiveBoard(){
        Board board = null;
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
            board = (Board) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return board;
    }


    public void stopConnection() throws IOException {
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("localhost", 65432);
        client.sendKeyEvent(KeyEvent.VK_RIGHT);
        Board board = client.receiveBoard();
        System.out.println(board.getTableLength());
        client.stopConnection();
    }
}