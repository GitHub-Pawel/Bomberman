package bomberman.engine;

import bomberman.network.*;
import bomberman.component.Board;
import bomberman.gui.BombermanGUI;
import bomberman.inputOutput.Keyboard;
import bomberman.observers.*;

import java.awt.event.KeyEvent;

public class ClientEngine implements KeyboardObserver {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private Keyboard keyboard;
    private BombermanGUI frame;
    private Board board;
    private Client client;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientEngine(String serverAddress, int port){
        this.keyboard = new Keyboard();
        this.keyboard.setSecondId((byte) 0);
        this.client = new Client(serverAddress, port);
        this.client.startConnection();
        this.board = this.client.receiveBoard();
        //this.board = //pobranie tablicy z serwera//
        //this.clientId = //pobranie id przydzielonego przez serwer (0-3)//
        this.frame = new BombermanGUI(board);
        this.frame.addKeyListener(this.keyboard.getKeyboardID());
        this.keyboard.subscribe(this);

    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    @Override
    public void moveUp(byte id) {
        //wyslij wiadomosc moveUp na serwer
        //zwraca id klienta zeby serwer mogl odroznic
        this.client.sendKeyEvent(KeyEvent.VK_UP);
    }
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
}
