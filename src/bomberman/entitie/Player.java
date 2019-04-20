package bomberman.entitie;

import bomberman.component.Block;
import bomberman.inputOutput.Sound;

import javax.swing.*;

public class Player extends Block {
    public Bomb lastoneBomb;
    private boolean stillAlive;
    private byte limitOfBombs;
    private byte plantedBombs;
    private int power;
    private boolean winner;
    private int realId;


    /********************************************************************
     *                         Constructor                              *
     * @param startPositionX
     * @param startPositionY                                            *
     ********************************************************************/

    public Player(int startPositionX, int startPositionY){
        super(startPositionX, startPositionY);     // By default player is in the top left corner
        this.setImage(new ImageIcon("images\\player.jpg"));
        this.stillAlive = true;
        this.limitOfBombs = 1;
        this.plantedBombs = 0;
        this.power = 1;
        this.winner = false;
    }

    /********************************************************************
     *                        Getters & Setters                         *
     ********************************************************************/

    public boolean isStillAlive() {
        return stillAlive;
    }
    public void setStillAlive(boolean stillAlive) {
        this.stillAlive = stillAlive;
        if (this.isStillAlive() == true){
            this.setImage(new ImageIcon("images\\player.jpg"));
        } else{
            Sound.play("sounds\\killedPlayer.wav");
            this.setImage(new ImageIcon("images\\burnedPlayer.jpg"));
        }
    }

    public byte getLimitOfBombs() {
        return limitOfBombs;
    }
    public void setLimitOfBombs(byte limitOfBombs) {
        this.limitOfBombs = limitOfBombs;
    }

    public byte getPlantedBombs() {
        return plantedBombs;
    }
    public void setPlantedBombs(byte plantedBombs) {
        this.plantedBombs = plantedBombs;
    }

    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }

    public int getRealId() {
        return realId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
