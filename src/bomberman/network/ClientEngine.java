package bomberman.network;

import bomberman.component.Board;
import bomberman.gui.BombermanGUI;
import bomberman.inputOutput.ClientKeyboard;
import bomberman.observers.*;

public class ClientEngine implements ClientKeyboardObserver {
    private ClientKeyboard clientKeyboard;
    private BombermanGUI frame;
    private Board board;
    private Client client;
    private byte clientId;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public ClientEngine(String serverAddress, int port){
        this.clientKeyboard = new ClientKeyboard();
        this.client = new Client(serverAddress, port);
        //this.board = //pobranie tablicy z serwera//
        //this.clientId = //pobranie id przydzielonego przez serwer (0-3)//
        this.frame = new BombermanGUI(board);
        this.clientKeyboard.subscribe(this);

    }

    @Override
    public void moveUp() {
        //wyslij wiadomosc moveUp na serwer
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


}
