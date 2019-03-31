package bomberman.network;

import bomberman.component.Board;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class ClientHandler implements Runnable{
    /********************************************************************
     *                         Properties                               *
     ********************************************************************/
    private final Socket client;
    private final int id;


    /********************************************************************
     *                         Constructor                              *
     ********************************************************************/
    public ClientHandler(Socket socket, int id){
        this.client = socket;
        this.id = id;
    }


    /********************************************************************
     *                            Methods                               *
     ********************************************************************/
    public int receiveKeyEvent(){
        int keyEvent = -1;
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(this.client.getInputStream());
            keyEvent = (int) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return keyEvent;
    }

    public void sendBoard(Board board){
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        /*
        while (true){               //TO DO: Create disconnection system
            try {
                Thread.sleep(1000, 33);       //refresh 30 times per second
            } catch (InterruptedException e1) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            this.stopHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        System.out.println(this.receiveKeyEvent());
        Board board = new Board(17);
        this.sendBoard(board);
        try {
            this.stopHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopHandler() throws IOException {
        this.client.close();
    }
}
