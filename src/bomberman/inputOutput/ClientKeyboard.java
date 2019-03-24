package bomberman.inputOutput;

import bomberman.exception.WrongKeyException;
import bomberman.observers.ClientKeyboardObserver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientKeyboard implements KeyListener {

    private boolean[] keys = new boolean[KeyEvent.VK_X + 1];    // KeyEvent.VK_X has the highest value from all of keys
    private ClientKeyboardObserver clientKeyboardObserver;
    private ClientKeyboard clientKeyboardID;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public ClientKeyboard(){
        clientKeyboardID = this;
    }

    /********************************************************************
     *                  Methods of reading the keys                     *
     ********************************************************************/

    public void checkForException(KeyEvent er) throws WrongKeyException {       // When using other keys than established
        if (!(er.getKeyCode() == KeyEvent.VK_UP || er.getKeyCode() == KeyEvent.VK_W ||
                er.getKeyCode() == KeyEvent.VK_DOWN || er.getKeyCode() == KeyEvent.VK_S ||
                er.getKeyCode() == KeyEvent.VK_LEFT || er.getKeyCode() == KeyEvent.VK_A ||
                er.getKeyCode() == KeyEvent.VK_RIGHT || er.getKeyCode() == KeyEvent.VK_D ||
                er.getKeyCode() == KeyEvent.VK_ENTER || er.getKeyCode() == KeyEvent.VK_SPACE))
            throw new WrongKeyException();
    }


    @Override
    public void keyPressed(KeyEvent e) {        // When the key goes down
        try {
            checkForException(e);
            if (keys[e.getKeyCode()] == false) {
                keys[e.getKeyCode()] = true;
                this.update(e.getKeyCode());
            }
        } catch (WrongKeyException wke){}
    }

    @Override
    public void keyReleased(KeyEvent e) {       // When the key comes up
        try {
            checkForException(e);
            keys[e.getKeyCode()] = false;
        } catch (WrongKeyException wke){}
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // When the unicode character represented by this key
        // is sent by the keyboard to system input.
    }

    public void subscribe(ClientKeyboardObserver o){
        clientKeyboardObserver = o;
    }

    public void unsubscribe(ClientKeyboardObserver o){
        if (clientKeyboardObserver == o){
            clientKeyboardObserver = null;
        }
    }

    public void update(int key) {       // Execution the methods assigned to keys
        switch (key){
            case KeyEvent.VK_W:
                clientKeyboardObserver.moveUp();
                break;
            case KeyEvent.VK_S:
                clientKeyboardObserver.moveDown();
                break;
            case KeyEvent.VK_A:
                clientKeyboardObserver.moveLeft();
                break;
            case KeyEvent.VK_D:
                clientKeyboardObserver.moveRight();
                break;
            case KeyEvent.VK_SPACE:
                clientKeyboardObserver.plantBomb();
                break;
            case KeyEvent.VK_UP:
                clientKeyboardObserver.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                clientKeyboardObserver.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                clientKeyboardObserver.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                clientKeyboardObserver.moveRight();
                break;
            case KeyEvent.VK_ENTER:
                clientKeyboardObserver.plantBomb();
                break;
        }
    }


}


