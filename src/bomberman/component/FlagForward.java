package bomberman.component;

import java.io.Serializable;

public class FlagForward implements Serializable {
    private boolean playBonus;
    private boolean playBoom;
    private boolean playKilledPlayer;
    private boolean playMove;
    private boolean []  stillAlive;
    private boolean []  winner;


    public FlagForward(){
        this.playBonus = Boolean.FALSE;
        this.playBoom = Boolean.FALSE;
        this.playKilledPlayer = Boolean.FALSE;
        this.playMove = Boolean.FALSE;

        this.stillAlive = new boolean[4];
        for (int i = 0; i<3; ++i){
            this.stillAlive[i] = Boolean.TRUE;
        }

        this.winner = new boolean[4];
        for (int i = 0; i<3; ++i){
            this.winner[i] = Boolean.FALSE;
        }
    }

    public boolean isPlayBonus() {
        return playBonus;
    }

    public void setPlayBonus(boolean playBonus) {
        this.playBonus = playBonus;
    }

    public boolean isPlayBoom() {
        return playBoom;
    }

    public void setPlayBoom(boolean playBoom) {
        this.playBoom = playBoom;
    }

    public boolean isPlaykilledPlayer() {
        return playKilledPlayer;
    }

    public void setPlaykilledPlayer(boolean playkilledPlayer) {
        this.playKilledPlayer = playkilledPlayer;
    }

    public boolean isPlayMove() {
        return playMove;
    }

    public void setPlayMove(boolean playMove) {
        this.playMove = playMove;
    }

    public boolean getStillAlive(int id) {
        return stillAlive[id];
    }

    public void setStillAlive(int id, boolean bool) {
        this.stillAlive[id] = bool;
    }

    public boolean getWinner(int id) {
        return winner[id];
    }

    public void setWinner(int id, boolean bool) {
        this.winner[id] = bool;
    }
}