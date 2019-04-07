package bomberman.network;

import bomberman.component.Board;
import bomberman.component.BoardForward;
import bomberman.engine.ServerEngine;
import bomberman.observers.KeyboardObserver;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ClientHandler implements Runnable{
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private final Socket client;
    private final int id;
    //private Board refereceToBoard;
    private BoardForward refToBoardForward; //
    private KeyboardObserver keyboardObserver;



    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientHandler(Socket socket, int id, BoardForward boardForward, ServerEngine serverEngineSubscribe){    //
        this.client = socket;
        this.id = id;
        //this.refereceToBoard = board;
        this.refToBoardForward = boardForward; //
        this.keyboardObserver = serverEngineSubscribe;
    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public int receiveKeyEvent(){
        int keyEvent = -1;
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(this.client.getInputStream());
            keyEvent = (int) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return keyEvent;
    }

    public void sendBoardForward(){
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(this.refToBoardForward); //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopHandler() throws IOException {
        this.client.close();
    }

    /********************************************************************
     *                       Keyboard Observer                          *
     ********************************************************************/

    public void update(int key) {       // Execution the methods assigned to keys
        switch (key){
            case KeyEvent.VK_W:
                keyboardObserver.moveUp((byte) this.id);
                break;
            case KeyEvent.VK_S:
                keyboardObserver.moveDown((byte) this.id);
                break;
            case KeyEvent.VK_A:
                keyboardObserver.moveLeft((byte) this.id);
                break;
            case KeyEvent.VK_D:
                keyboardObserver.moveRight((byte) this.id);
                break;
            case KeyEvent.VK_SPACE:
                keyboardObserver.plantBomb((byte) this.id);
                break;
            case KeyEvent.VK_UP:
                keyboardObserver.moveUp((byte) this.id);
                break;
            case KeyEvent.VK_DOWN:
                keyboardObserver.moveDown((byte) this.id);
                break;
            case KeyEvent.VK_LEFT:
                keyboardObserver.moveLeft((byte) this.id);
                break;
            case KeyEvent.VK_RIGHT:
                keyboardObserver.moveRight((byte) this.id);
                break;
            case KeyEvent.VK_ENTER:
                keyboardObserver.plantBomb((byte) this.id);
                break;
        }
    }

    /********************************************************************
     *                       Runnable method                            *
     ********************************************************************/


    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10);               //refresh 100 times per second
            } catch (InterruptedException e1) {
            }

            this.update(this.receiveKeyEvent());
        }

        //TO DO: Create disconnection system
        /*
        try {
            this.stopHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}


/*
    @Override
    public void run() {
        System.out.println(this.receiveKeyEvent());
        Board board = new Board(17);
        this.sendBoardForward(board);
        try {
            this.stopHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/