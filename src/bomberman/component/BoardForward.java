package bomberman.component;

import bomberman.entitie.basic.Floor;
import bomberman.entitie.basic.Wall;

import javax.swing.*;
import java.io.Serializable;

public class BoardForward implements Serializable {

    private Block[][] table;
    private int tableLength;
    private FlagForward flagForward;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/

    public BoardForward(Board board) {
        this.tableLength = board.getTableLength();
        this.table = new Block[this.tableLength][this.tableLength];

        for (int row = 0; row < tableLength; ++row) {
            for (int column = 0; column < tableLength; ++column) {
                this.table[row][column] = new Block(row, column);
                this.table[row][column].setImage(board.getTable()[row][column].getImage());
            }
        }
        this.flagForward = new FlagForward();
    }

    /********************************************************************
     *                      Getters & Setters                           *
     ********************************************************************/

    public void setBoardForwardField(int row, int column, Block block){
        this.table[row][column].setImage(block.getImage());
    }

    public void setBoardForwardField(int row, int column, ImageIcon image){
        this.table[row][column].setImage(image);
    }

    public Block[][] getTable() { return table; }

    public int getTableLength() {
        return tableLength;
    }

    public FlagForward getFlagForward() {
       return flagForward;
    }
}
