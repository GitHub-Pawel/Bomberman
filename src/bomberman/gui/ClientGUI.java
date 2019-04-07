package bomberman.gui;

import bomberman.component.Board;
import bomberman.component.BoardForward;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {

    private BoardForward boardForward;
    private JPanel rootPanel;
    private JLabel field[][];


    /********************************************************************
     *                         Constructor                              *
     * @param boardForward                                                    *
     ********************************************************************/

    public ClientGUI(BoardForward boardForward){
        this.boardForward = boardForward;
        field = new JLabel[this.boardForward.getTableLength()][this.boardForward.getTableLength()];       // The game field consists of singular labels
        rootPanel = new JPanel();

        rootPanel.setLayout(new GridLayout(this.boardForward.getTableLength(), this.boardForward.getTableLength()));


        for (int i = 0; i < this.boardForward.getTableLength(); i++) {          // Game field setting based on board state
            for (int j = 0; j < this.boardForward.getTableLength(); j++) {
                field[i][j] = new JLabel();
                try {
                    field[i][j].setIcon(this.boardForward.getTable()[i][j].getImage());
                } catch (NullPointerException e){
                }
                rootPanel.add(field[i][j]);
            }
        }

        this.setContentPane(this.rootPanel);
        this.setTitle("Bomberman Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    /********************************************************************
     *                         Screen reloading                         *
     ********************************************************************/

    public void screenReload(){
        this.screenReload(this.boardForward);      // Refreshing the game field in depend of board state
    }

    public void screenReload(BoardForward boardForward){         // Refreshing the game field in depend of board state
        for (int i = 0; i < boardForward.getTableLength(); i++) {
            for (int j = 0; j < boardForward.getTableLength(); j++) {
                try {
                    field[i][j].setIcon(boardForward.getTable()[i][j].getImage());
                } catch (NullPointerException e){
                }
            }
        }
    }


}
