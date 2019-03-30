package bomberman.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable{
    private final Socket client;
    private final int id;
    private PrintWriter out;
    private BufferedReader in;


    public ClientHandler(Socket socket, int id) throws IOException {
        this.client = socket;
        this.id = id;
        this.out = new PrintWriter(client.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    public void getMessage() throws IOException {
        String greeting = in.readLine();
        System.out.println(greeting);
    }

    public void setMessage() throws IOException {
        out.println("hello client");
    }

    @Override
    public void run() {
        try {
            this.getMessage();
            this.setMessage();
            this.stopHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopHandler() throws IOException {
        this.in.close();
        this.out.close();
        this.client.close();
    }
}
