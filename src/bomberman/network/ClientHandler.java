package bomberman.network;

import bomberman.component.Board;
import bomberman.component.BoardForward;
import bomberman.engine.ServerEngine;
import bomberman.observers.ClientDisconnectedObserver;
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
    private final Socket clientRx;
    private final Socket clientTx;
    private final int id;
    private BoardForward refToBoardForward; //
    private KeyboardObserver keyboardObserver;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ClientDisconnectedObserver clientDisconnectedObserver;
    private boolean continueThread;

    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientHandler(Socket socketRx, Socket socketTx, int id, BoardForward boardForward, ServerEngine serverEngineSubscribe){    //
        this.clientRx = socketRx;
        this.clientTx = socketTx;
        this.id = id;
        this.continueThread = true;
        this.refToBoardForward = boardForward; //
        this.keyboardObserver = serverEngineSubscribe;

        try {
            this.objectInputStream = new ObjectInputStream(this.clientRx.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(this.clientTx.getOutputStream());

            this.objectOutputStream.writeInt(id);  //First message to client is his Id
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

            this.clientDisconnectedObserver.exitConnection();
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
        this.continueThread = false;
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
     *                Client Disconnected Observer                      *
     ********************************************************************/


    public void subscribe(ClientDisconnectedObserver o){
        clientDisconnectedObserver = o;
    }

    public void unsubscribe(ClientDisconnectedObserver o){
        if (clientDisconnectedObserver == o){
            clientDisconnectedObserver = null;
        }
    }

    /********************************************************************
     *                       Runnable method                            *
     ********************************************************************/


    @Override
    public void run() {
        while (this.continueThread){

            this.update(this.receiveKeyEvent());
        }
    }

    public void setContinueThread(boolean continueThread) {
        this.continueThread = continueThread;
    }
}