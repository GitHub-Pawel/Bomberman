package bomberman.component;

import javax.swing.*;
import java.io.Serializable;

public class BoardForward implements Serializable {

    private ImageIcon[][] boardForward;
    private int tableLength;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public BoardForward(Board board) {
        this.tableLength = board.getTableLength();
        this.boardForward = new ImageIcon[tableLength][tableLength];
        for (int row = 0; row < tableLength; ++row) {
            for (int column = 0; column < tableLength; ++column) {
                this.boardForward[row][column] = board.getTable()[row][column].getImage();
            }
        }
    }

    /********************************************************************
     *                      Getters & Setters                           *
     ********************************************************************/

    public void setBoardForwardField(int row, int column, Block block){
        this.boardForward[row][column] = block.getImage();
    }

    public void setBoardForwardField(int row, int column, ImageIcon image){
        this.boardForward[row][column] = image;
    }

    public ImageIcon[][] getTable() {
        return boardForward;
    }

    public int getTableLength() {
        return tableLength;
    }
}
