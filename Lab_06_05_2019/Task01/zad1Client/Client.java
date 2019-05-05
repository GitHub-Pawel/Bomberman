/**********************************************************************************
 * 1) Zaimportuj pakiety, niezbędne do wykonania zadania.						  *
 **********************************************************************************/

public class Client {

    private int port;
    private Socket clientSocket;
    private InetAddress inetAddress;
    private ObjectInputStream objectInputStream;

    public Client(InetAddress inetAddress, int port){
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public void startConnection(){
        try {
            /*******************************************************************************
            * 2) Dokończ tworzenie poniższych obiektów:                                    *
            *       this.clientSocket = new ...                                            *
            *       this.objectInputStream = new ...                                       *
            *******************************************************************************/

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }

    public String receiveMessage(){
        try {
            /*******************************************************************************
             * 3) Na obiekcie this.objectInputStream wywołaj odpowiednią metode,           *
             *    dzięki której zwrócisz wiadomość od Servera:                             *
             *        return this.objectInputStream.[Odpowiednia metoda]()                 *
             *******************************************************************************/

        }catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stopConnection(){
        try {
            /*******************************************************************************
             * 4) Wywolaj metode [obiekt].close() na odpowiednich obiektach,               *
             *    wchodzacych w sklad klasy Client w taki sposob,                          *
             *    aby poprawnie zakoczyc polaczenie                                        *
             *******************************************************************************/

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}