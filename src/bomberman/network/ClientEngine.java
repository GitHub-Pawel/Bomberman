package bomberman.network;

import bomberman.component.Board;
import bomberman.gui.BombermanGUI;
import bomberman.inputOutput.Keyboard;
import bomberman.observers.*;

public class ClientEngine implements KeyboardObserver {
    private Keyboard keyboard;
    private BombermanGUI frame;
    private Board board;
    private Client client;
    private byte clientId;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public ClientEngine(String serverAddress, int port){
        this.keyboard = new Keyboard();
        this.client = new Client(serverAddress, port);
        //this.board = //pobranie tablicy z serwera//
        //this.clientId = //pobranie id przydzielonego przez serwer (0-3)//
        this.frame = new BombermanGUI(board);
        this.keyboard.subscribe(this);

    }

    @Override
    public void moveUp() {
        //wyslij wiadomosc moveUp na serwer
        //zwraca id klienta zeby serwer mogl odroznic
    }
    @Override
    public void moveDown() {

    }
    @Override
    public void moveLeft() {

    }
    @Override
    public void moveRight() {

    }
    @Override
    public void plantBomb() {
    }

    @Override
    public void moveUp(byte id) {}
    @Override
    public void moveDown(byte id) {}
    @Override
    public void moveLeft(byte id) {}
    @Override
    public void moveRight(byte id) {}
    @Override
    public void plantBomb(byte id) {}

}
