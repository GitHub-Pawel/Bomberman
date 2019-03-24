package bomberman.network;

import bomberman.component.Board;
import bomberman.entitie.Player;
import bomberman.entitie.basic.Floor;
import bomberman.entitie.box.Case;

import java.util.Random;

public class ServerEngine {
    private Server server;
    private Board board;
    private int numberOfPlayers;
    private Player[] players;



    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public ServerEngine(int port, int numberOfPlayers, int fieldSize) {
        this.server = new Server(port);
        this.board = new Board(fieldSize);
        this.numberOfPlayers = numberOfPlayers;
        this.players = new Player[this.numberOfPlayers];

        if (numberOfPlayers == 2){
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

        insertCases();
        raffleBonus();

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



}
