/**********************************************************************************
 * 1) Zaimportuj pakiety, niezbędne do wykonania zadania.						  *
 **********************************************************************************/

public class Server {
    private int port;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ObjectOutputStream objectOutputStream;

    public Server(int port){
        this.port = port;
    }

    public void startConnection(){
        try {
            /**********************************************************************************
             * 2) Dokończ tworzenie poniższych obiektów:                                      *
             *       this.serverSocket = ...                                                  *
             *       this.clientSocket = ...                                                  *
             *       this.objectOutputStream = ...                                            *
             **********************************************************************************/

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }

    public void sendMessageUTF(String message){
        try{
            /**********************************************************************************
             * 3) Na obiekcie this.objectOutputStream wywołaj odpowiednie metody,             *
             *    dzięki którym wyślesz wiadomość do Clienta.                                 *
             *    Pamiętaj o zastosowaniu metody, która inicjuje wysłanie zawartości buffora, *
             *    za pomocą pakietu TCP.                                                      *
             **********************************************************************************/

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection(){
        try {
            if(this.clientSocket.isConnected()){
                /**********************************************************************************
                 * 4) Wywolaj metode [obiekt].close() na odpowiednich obiektach,                  *
                 *    wchodzacych w sklad klasy Server  w taki sposób,                            *
                 *    aby poprawnie zakoczyc polaczenie                                           *
                 **********************************************************************************/

            }
        } catch (NullPointerException | IOException e){
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
