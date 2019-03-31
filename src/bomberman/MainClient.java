package bomberman;

import bomberman.engine.ClientEngine;

public class MainClient {
    public static void main(String[] args) {
        ClientEngine clientEngine = new ClientEngine("localhost", 65432);
    }
}
