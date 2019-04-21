package bomberman.network;

import bomberman.engine.ServerEngine;

import java.io.IOException;

public class ServerRunner implements Runnable {

    private int inputPort;
    private int outputPort;
    private int numberOfPlayers;
    private int fieldSize;

    public ServerRunner(int inputPort, int outputPort, int numberOfPlayers, int fieldSize) {
        this.inputPort = inputPort;
        this.outputPort = outputPort;
        this.numberOfPlayers = numberOfPlayers;
        this.fieldSize = fieldSize;
    }

    @Override
    public void run() {
        try {
            ServerEngine serverEngine = new ServerEngine(inputPort, outputPort, numberOfPlayers, fieldSize);
        } catch (IOException e1) {}
    }
}
