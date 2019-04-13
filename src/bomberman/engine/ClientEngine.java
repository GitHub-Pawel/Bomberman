package bomberman.engine;

import bomberman.component.BoardForward;
import bomberman.gui.ClientGUI;
import bomberman.network.*;
import bomberman.inputOutput.Keyboard;
import bomberman.observers.*;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class ClientEngine implements KeyboardObserver, BoardForwardObserver {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private Keyboard keyboard;
    private ClientGUI frame;    //
    private BoardForward boardForward; //
    private Client client;
    private Thread clientThread;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientEngine(String serverAddress, int portTx, int portRx) throws IOException {
        this.keyboard = new Keyboard();
        this.keyboard.setSecondId((byte) 0);
        this.client = new Client(serverAddress, portTx, portRx);
        this.client.startConnection();
        this.boardForward = this.client.receiveBoard();
        this.client.subscribe(this);
        this.clientThread = new Thread(client);
        this.clientThread.start();
        this.frame = new ClientGUI(boardForward);  //
        this.frame.addKeyListener(this.keyboard.getKeyboardID());
        this.keyboard.subscribe(this);

    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    @Override
    public void moveUp(byte id) { this.client.sendKeyEvent(KeyEvent.VK_UP); }
    @Override
    public void moveDown(byte id) {
        this.client.sendKeyEvent(KeyEvent.VK_DOWN);
    }
    @Override
    public void moveLeft(byte id) {
        this.client.sendKeyEvent(KeyEvent.VK_LEFT);
    }
    @Override
    public void moveRight(byte id) {
        this.client.sendKeyEvent(KeyEvent.VK_RIGHT);
    }
    @Override
    public void plantBomb(byte id) {
        this.client.sendKeyEvent(KeyEvent.VK_SPACE);
    }

    @Override
    public void boardUpdate(BoardForward boardForward) {
        this.boardForward = boardForward;
        try {
            frame.screenReload(this.boardForward);
        }catch (NullPointerException e){
            try {
                this.client.stopConnection();
            } catch (IOException e2) {
                e.printStackTrace();
            }
        }
    }
}
