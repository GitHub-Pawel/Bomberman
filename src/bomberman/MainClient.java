package bomberman;

import bomberman.engine.ClientEngine;

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException {
        ClientEngine clientEngine = new ClientEngine("localhost", 65432, 65433);
    }
}
