package bomberman.engine;

import bomberman.component.BoardForward;
import bomberman.network.*;
import bomberman.component.Block;
import bomberman.component.Board;
import bomberman.entitie.Bomb;
import bomberman.entitie.Fire;
import bomberman.entitie.Player;
import bomberman.entitie.basic.Floor;
import bomberman.entitie.basic.Wall;
import bomberman.entitie.box.Bonus;
import bomberman.entitie.box.Case;
import bomberman.observers.BombObserver;
import bomberman.observers.KeyboardObserver;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

/*
TO DO
checkWin/checkDefeat/gameEnd -> zakonczenie gry
*/

public class ServerEngine implements KeyboardObserver, BombObserver {
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private Server server;
    private Board board;
    private BoardForward boardForward;
    private int numberOfPlayers;
    private Player[] players;
    private volatile Thread[] bombThreads;
    private int bombCount;
    private int killedPlayers;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public ServerEngine(int portRx, int portTx, int numberOfPlayers, int fieldSize) throws IOException {
        this.board = new Board(fieldSize);
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[this.numberOfPlayers];
        this.bombThreads = new Thread[200];        // 200 is maximum number of threads
        this.bombCount = 0;
        this.killedPlayers = 0;

        if (numberOfPlayers == 1) {
            players[0] = new Player(1, 1);
            board.getTable()[1][1] = players[0];
        }else if (numberOfPlayers == 2){
            players[0] = new Player(1, 1);
            players[1] = new Player(this.board.getTableLength()-2, this.board.getTableLength()-2);
            board.getTable()[1][1] = players[0];
            board.getTable()[this.board.getTableLength()-2][this.board.getTableLength()-2] = players[1];
        }else if(numberOfPlayers == 3){
            players[0] = new Player(1, 1);
            players[1] = new Player(this.board.getTableLength()-2, 1);
            players[2] = new Player(this.board.getTableLength()-2, this.board.getTableLength()-2);
            board.getTable()[1][1] = players[0];
            board.getTable()[this.board.getTableLength()-2][1] = players[1];
            board.getTable()[this.board.getTableLength()-2][this.board.getTableLength()-2] = players[2];
        }else if(numberOfPlayers == 4){
            players[0] = new Player(1, 1);
            players[1] = new Player(this.board.getTableLength()-2, 1);
            players[2] = new Player(1, this.board.getTableLength()-2);
            players[3] = new Player(this.board.getTableLength()-2, this.board.getTableLength()-2);
            board.getTable()[1][1] = players[0];
            board.getTable()[this.board.getTableLength()-2][1] = players[1];
            board.getTable()[1][this.board.getTableLength()-2] = players[2];
            board.getTable()[this.board.getTableLength()-2][this.board.getTableLength()-2] = players[3];
        }

        this.insertCases();
        this.raffleBonus();
        this.boardForward = new BoardForward(this.board);
        this.server = new Server(portRx, portTx, numberOfPlayers, this.boardForward, this); //
        this.server.broadcastBoardUpdate();
        this.refreshSounds();
    }

    /********************************************************************
     *                     Game Initialization                          *
     *******************************************************************/

    public void insertCases(){              // Cases randomization, by default cases covers 25% of the game field
        Random random = new Random();
        int row, column;
        for (int i=0; i<(board.getTableLength()*board.getTableLength())/4; ++i){
            row = 0;
            column = 0;
            while (!(this.board.getTable()[row][column] instanceof Floor && row*column != 2 && row*column != (board.getTableLength()-2)*(board.getTableLength()-3))){
                row = random.nextInt(board.getTableLength()-1);
                column = random.nextInt(board.getTableLength()-1);
            }
            this.board.getTable()[row][column] = new Case();
        }
    }

    public void raffleBonus(){                            // Bonuses randomization, by default bonuses are hidden in 25% of cases
        Random random = new Random();
        int row, column, noOfBonus;
        for (int i=0; i<(board.getTableLength()*board.getTableLength())/16; ++i) {
            row = 0;
            column = 0;
            while (!(this.board.getTable()[row][column] instanceof Case)) {
                row = random.nextInt(board.getTableLength() - 1);
                column = random.nextInt(board.getTableLength() - 1);
            }
            noOfBonus = random.nextInt(2);
            ((Case) this.board.getTable()[row][column]).setBonus(noOfBonus);
        }
    }

    /********************************************************************
     *                  Update Player Position                          *
     ********************************************************************/

    public void changePlayerPosition (byte id, int y_startup, int x_startup, int y_changed, int x_changed) {
        this.board.getTable()[y_changed][x_changed] = players[id];
        this.boardForward.setBoardForwardField(y_changed, x_changed, this.board.getTable()[y_changed][x_changed]);
        players[id].setRow(y_changed);
        players[id].setColumn(x_changed);

        if (this.board.getTable()[y_startup][x_startup] instanceof Player) {
            this.board.getTable()[y_startup][x_startup] = board.getFloor();
            this.boardForward.setBoardForwardField(y_startup, x_startup, this.board.getTable()[y_startup][x_startup]);
                    /* If firstly player drops bomb and next preses something else
                    then we mustn't change this Bomb into the Floor */
        } else {
            try {
                if (y_changed != players[id].lastoneBomb.getRow() || x_changed != players[id].lastoneBomb.getColumn()) {
                    players[id].lastoneBomb.setImage(new ImageIcon("images\\bomb.jpg"));                        // Player changes position after planting bomb
                    this.boardForward.setBoardForwardField(players[id].lastoneBomb.getRow(), players[id].lastoneBomb.getColumn(), new ImageIcon("images\\bomb.jpg"));
                }
            } catch (NullPointerException e) {
            }
        }


        this.server.broadcastBoardUpdate();
        this.refreshSounds();
    }

    public void refreshSounds(){
        this.boardForward.getFlagForward().setPlayMove(false);
        this.boardForward.getFlagForward().setPlayBonus(false);
        this.boardForward.getFlagForward().setPlayBoom(false);
        this.boardForward.getFlagForward().setPlaykilledPlayer(false);
    }

    public void move(byte id, int add2Row, int add2Column){
        final int y_startup = players[id].getRow();
        final int x_startup = players[id].getColumn();
        final int y_changed = y_startup + add2Row;
        final int x_changed = x_startup + add2Column;


        if (players[id].isStillAlive() == true) {
            if (this.board.getTable()[y_changed][x_changed] instanceof Floor) {
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);               // Player moves into floor

                //Sound.play("sounds\\move.wav");
                this.boardForward.getFlagForward().setPlayMove(true);

            } else if (this.board.getTable()[y_changed][x_changed] instanceof Bonus) {
                if (((Bonus) this.board.getTable()[y_changed][x_changed]).isAddBomb()) {
                    players[id].setLimitOfBombs((byte) (players[id].getLimitOfBombs() + 1));        // Collecting addBomb bonus
                } else if (((Bonus) this.board.getTable()[y_changed][x_changed]).isIncreaseFire()) {
                    players[id].setPower(players[id].getPower() + 1);                               // Collecting increaseFire bonus
                }
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);               // Player moves into bonus
                //Sound.play("sounds\\bonus.wav");
                this.boardForward.getFlagForward().setPlayBonus(true);

            } else if (this.board.getTable()[y_changed][x_changed] instanceof Fire) {
                players[id].setStillAlive(false);                                               // Killing player when moves into fire
                changePlayerPosition(id, y_startup, x_startup, y_changed, x_changed);
                this.checkWinner();
            }
        }
    }

    public void moveUp(byte id){
        move(id, -1, 0);
    }

    public void moveDown(byte id){
        move(id, 1, 0);
    }

    public void moveLeft(byte id){
        move(id, 0, -1);
    }

    public void moveRight(byte id){
        move(id, 0 ,1);
    }


    /********************************************************************
     *                  Plant The Bomb                                  *
     ********************************************************************/

    public void plantBomb(byte id){
        if(players[id].isStillAlive() == true && players[id].getPlantedBombs() < players[id].getLimitOfBombs()) {
            players[id].setPlantedBombs((byte)(players[id].getPlantedBombs() + 1));   // Increasing the number of actually planted bombs by player
            Bomb newBomb = new Bomb(players[id].getRow(), players[id].getColumn());   // Call for new instance of Bomb class
            newBomb.setIdOfBomber(id);
            newBomb.setPower(players[id].getPower());
            players[id].lastoneBomb = newBomb;
            newBomb.subscribe(this);                    // Connecting a new thread of bomb with main thread

            bombThreads[bombCount] = new Thread(newBomb);   // Definition of the new thread
            bombThreads[bombCount].start();                 // Starting the new thread
            this.bombCount++;

            this.board.getTable()[players[id].getRow()][players[id].getColumn()] = newBomb;
            this.boardForward.setBoardForwardField(players[id].getRow(), players[id].getColumn(), newBomb);

            this.server.broadcastBoardUpdate();
            this.refreshSounds();
            //frame.screenReload();
            //taka metoda jest niepotrzebna, bo kazdy watek do oblsugi poszczegolnych klientow
            //ma referencje do obiektu board utworzonego w klsie ServerEngine,
            //wiec bedzie ja rozsylac cyklicznie niezaleznie od ServerEngine (np 100 na sekunde)
        }
    }

    public void setBurned(int row, int column) {                              // Setting fire
        if (!(this.board.getTable()[row][column] instanceof Wall)) {  // Can not set fire to wall and final gate
            if (this.board.getTable()[row][column] instanceof Case) {
                ((Case) this.board.getTable()[row][column]).setDestroyed(true);            // Setting fire to cases
            }else if (this.board.getTable()[row][column] instanceof Player) {
                if (((Player) this.board.getTable()[row][column]).isStillAlive()) {           // U P D A T E
                    ((Player) this.board.getTable()[row][column]).setStillAlive(false);       // Setting fire to player and killing
                    for (int i=0; i<numberOfPlayers; ++i){
                        if (players[i].getRow() == row && players[i].getColumn() == column){
                            playerDefeat(i);
                            break;
                        }
                    }
                    this.checkWinner();
                }
            }else{
                this.board.getTable()[row][column] = new Fire();
            }
            this.boardForward.setBoardForwardField(row, column, this.board.getTable()[row][column]);
        }

        for (int i=0; i < this.numberOfPlayers; ++i){
            if (players[i].getRow() == row && players[i].getColumn() == column) {
                if (players[i].isStillAlive()) {    // U P D A T E
                    players[i].setStillAlive(false);
                    this.playerDefeat(i);
                    this.boardForward.getFlagForward().setPlaykilledPlayer(true);
                    this.checkWinner();
                    this.board.getTable()[row][column].setImage(new ImageIcon("images\\burnedPlayer.jpg")); // When player had planted the bomb and did not escaped
                    this.boardForward.setBoardForwardField(row, column, new ImageIcon("images\\burnedPlayer.jpg"));
                }
            }
        }
    }

    public void playerDefeat(int id){
        this.boardForward.getFlagForward().setPlaykilledPlayer(true);
        this.boardForward.getFlagForward().setStillAlive(this.players[id].getRealId(), false);
        this.killedPlayers++;
        this.server.stopThreads(id);
    }

    private void checkWinner() {
        if (this.killedPlayers == this.numberOfPlayers - 1){
            int iD = 0;
            while (this.players[iD].isStillAlive() == false) {
                iD++;
            }
            this.players[iD].setWinner(true);
            this.boardForward.getFlagForward().setWinner(this.players[iD].getRealId(), true);
        }
    }

    @Override
    public void burn(Bomb bomb){
        int column = bomb.getColumn();
        int row = bomb.getRow();
        int power = bomb.getPower();

        setBurned(row, column);        // Burning the block which bomb was planted on

        // Exploding the bomb in four directions

        for(int i = 1; i <= power; i++) {                               // Bomb exploding range depends on the power
            if (column + i > board.getTableLength() - 1) break;         // Not to cross the boundaries of the board
            Block tmp = board.getTable()[row][column + i];              // Reference to the block
            setBurned(row, column + i);                          // Burning the neighboring block
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;  // Not to cross burned object and burn the next
        }
        for(int i = 1; i <= power; i++) {                               // Second direction
            if (column - i < 0) break;
            Block tmp = board.getTable()[row][column - i];
            setBurned(row, column - i);
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;
        }
        for(int i = 1; i <= power; i++) {                               // Third direction
            if (row + i > board.getTableLength() - 1) break;
            Block tmp = board.getTable()[row + i][column];
            setBurned(row + i, column);
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;
        }
        for(int i = 1; i <= power; i++) {                               // Fourth direction
            if (row - i < 0) break;
            Block tmp = board.getTable()[row - i][column];
            setBurned(row - i, column);
            if(!(tmp instanceof Floor || tmp instanceof Bonus)) break;
        }

        this.boardForward.getFlagForward().setPlayBoom(true);
        this.server.broadcastBoardUpdate();
        this.refreshSounds();
    }


    public void setFaded(int row, int column){                                                  // The fire disappears after explosion
        if (this.board.getTable()[row][column] instanceof Fire){
            this.board.getTable()[row][column] = this.board.getFloor();                         // Deleting the fire
        } else if (this.board.getTable()[row][column] instanceof Case){
            Bonus tmpBonus = ((Case) this.board.getTable()[row][column]).getBonus();
            if (tmpBonus.isSet() == true) {                                                     // Checking if case contains a bonus
                this.board.getTable()[row][column] = tmpBonus;                                  // Setting the hidden bonus
            } else {                                                                   // Setting final gate
                this.board.getTable()[row][column] = this.board.getFloor();            // Deleting the burned case
            }
        }else if (this.board.getTable()[row][column] instanceof Player){
            this.board.getTable()[row][column] = this.board.getFloor();                // Killing the player
        }
        this.boardForward.setBoardForwardField(row, column, this.board.getTable()[row][column]);
        //gameEnd();
    }

    @Override
    public void fade(Bomb bomb) {
        int column = bomb.getColumn();
        int row = bomb.getRow();
        int power = bomb.getPower();
        players[bomb.getIdOfBomber()].setPlantedBombs((byte) (players[bomb.getIdOfBomber()].getPlantedBombs() - 1));   // Decreasing the number of actually planted bombs by player

        setFaded(row, column);      // Removing the fire on the block where the bomb was planted on

        // Removing fire in four directions

        for(int i = 1; i <= power; i++) {                           // Fire range depends on the bomb power
            if (column + i > board.getTableLength() - 1) break;     // Not to cross the boundaries of the board
            Block tmp = board.getTable()[row][column + i];          // Reference to the block
            setFaded(row, column + i);                       // Removing the fire
            if(!(tmp instanceof Fire || tmp instanceof Floor)) break;   // U P D A T E         // Not to cross burned object and remove the next
        }
        for(int i = 1; i <= power; i++) {
            if (column - i < 0) break;
            Block tmp = board.getTable()[row][column - i];
            setFaded(row,column - i);
            if(!(tmp instanceof Fire || tmp instanceof Floor)) break;   // U P D A T E
        }
        for(int i = 1; i <= power; i++) {
            if (row + i > board.getTableLength() - 1) break;
            Block tmp = board.getTable()[row + i][column];
            setFaded(row + i, column);
            if(!(tmp instanceof Fire || tmp instanceof Floor)) break;   // U P D A T E
        }
        for(int i = 1; i <= power; i++) {
            if (row - i < 0) break;
            Block tmp = board.getTable()[row - i][column];
            setFaded(row - i, column);
            if(!(tmp instanceof Fire || tmp instanceof Floor)) break;   // U P D A T E
        }

        this.server.broadcastBoardUpdate();
        this.refreshSounds();
    }

    public Player getPlayers(int i) {
        return players[i];
    }
}
