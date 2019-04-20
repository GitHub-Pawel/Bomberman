package bomberman.engine;

import bomberman.component.BoardForward;
import bomberman.component.FlagForward;
import bomberman.gui.ClientGUI;
import bomberman.gui.GameEndGUI;
import bomberman.inputOutput.Sound;
import bomberman.network.*;
import bomberman.inputOutput.Keyboard;
import bomberman.observers.*;

import javax.swing.*;
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
    private GameEndGUI gameEndGUI;
    private ImageIcon gameEndImage;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientEngine(String serverAddress, int portTx, int portRx){
        this.keyboard = new Keyboard();
        this.keyboard.setSecondId((byte) 0);
        this.client = new Client(serverAddress, portTx, portRx);
        this.client.startConnection();
        this.boardForward = this.client.receiveBoard();
        this.client.subscribe(this);
        this.clientThread = new Thread(client);
        this.clientThread.start();
        this.frame = new ClientGUI(boardForward);
        this.frame.addKeyListener(this.keyboard.getKeyboardID());
        this.keyboard.subscribe(this);
        this.gameEndGUI = null;
        this.gameEndImage = null;
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
            this.playSound();
            this.checkDefeat();
            this.checkWinner();
        }catch (NullPointerException e){
            try {
                this.client.stopConnection();
            } catch (IOException e2) {
                e.printStackTrace();
            }
        }
    }

    public void playSound(){
        if (this.boardForward.getFlagForward().isPlayBonus()){
            Sound.play("sounds\\bonus.wav");
        }

        if (this.boardForward.getFlagForward().isPlayBoom()){
            Sound.play("sounds\\boom.wav");
        }

        if (this.boardForward.getFlagForward().isPlaykilledPlayer()){
            Sound.play("sounds\\killedPlayer.wav");
        }

        if (this.boardForward.getFlagForward().isPlayMove()){
            Sound.play("sounds\\move.wav");
        }
    }

    public void checkDefeat(){
        if (this.boardForward.getFlagForward().getStillAlive(this.client.getId()) == false){
            Sound.play("sounds\\gameover.wav");
            this.gameEndImage = new ImageIcon("menuImages\\youLoseLabel.jpg");
            this.gameEndGUI = new GameEndGUI(this.gameEndImage);
        }
    }

    public void checkWinner(){
        if (this.boardForward.getFlagForward().getWinner(this.client.getId())){
            Sound.play("sounds\\win.wav");
            this.gameEndImage = new ImageIcon("menuImages\\youWinLabel.jpg");
            this.gameEndGUI = new GameEndGUI(this.gameEndImage);
        }
    }
}
