package bomberman;

import bomberman.engine.ServerEngine;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        ServerEngine serverEngine = new ServerEngine(65432, 65433, 2, 15);
    }
}
