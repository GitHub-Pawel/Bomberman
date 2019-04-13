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
    //private final Socket client;
    private final Socket clientRx;
    private final Socket clientTx;
    private final int id;
    //private Board refereceToBoard;
    private BoardForward refToBoardForward; //
    private KeyboardObserver keyboardObserver;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientHandler(Socket socketRx, Socket socketTx, int id, BoardForward boardForward, ServerEngine serverEngineSubscribe){    //
        this.clientRx = socketRx;
        this.clientTx = socketTx;
        this.id = id;
        //this.refereceToBoard = board;
        this.refToBoardForward = boardForward; //
        this.keyboardObserver = serverEngineSubscribe;

        try {
            this.objectInputStream = new ObjectInputStream(this.clientRx.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(this.clientTx.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public int receiveKeyEvent(){
        int keyEvent = -1;
        try{
            keyEvent = (int) this.objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return keyEvent;
    }

    public void sendBoardForward(){
        try{
            this.objectOutputStream.reset();
            this.objectOutputStream.writeObject(this.refToBoardForward); //
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void stopHandler() throws IOException {
        //this.client.close();
        this.clientRx.close();
        this.clientTx.close();
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
                Thread.sleep(0);               //refresh 100 times per second
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