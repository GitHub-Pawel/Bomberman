import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPMulticastReceiver implements Runnable {

    String groupAddress;
    int port;

    InetAddress group;
    MulticastSocket multicastSocket;

    String message;

    MessageObserver observer;

    public UDPMulticastReceiver(String groupAddress, int port) throws IOException {
        this.groupAddress = groupAddress;
        this.port = port;
        this.group = InetAddress.getByName(this.groupAddress);
        /****************************************************************************************
         *  1) Zainicjalizuj socket multicastowy (this.multicastSocket = ...),                  *
         *     działający na porcie podanym w konstruktorze                                     *
         *  2) Dołącz do grupy multicastowej przy pomocy zainicjalizowanego socketu (wywołaj    *
         *     odpowiednią metodę)                                                              *
         ****************************************************************************************/

        //...
        //...

    }

    public void receiveMessage() throws IOException {
        /****************************************************************************************
         *  3) Utwórz i zainicjalizuj tablicę typu byte (o rozmiarze 1024), potrzebną do        *
         *     przechowywania otrzymanej wiadomości                                             *
         ****************************************************************************************/

        //...


        while(true) {
            System.out.println("Waiting for multicast message...");
            /******************************************************************************************
             *  4) Utwórz i zainicjalizuj pakiet, który jako argumenty przyjmie utworzoną tablicę     *
             *     i jej długość                                                                      *
             *  5) Odbierz pakiet, wywołyjąc metodę receive na utworzonym sockecie multicastowym      *
             *  6) Przekonwertuj otrzymany pakiet na Stringa (utwórz obiekt typu String o nazwie msg, *
             *     w konstruktorze podaj odpowiednie argumenty - użyj metod: getData, getOffset,      *
             *     getLength)                                                                         *
             ******************************************************************************************/

            //...
            //...
            //...

            System.out.println("Message received: " + msg);
            this.message = msg;
            observer.showMessage();
        }
    }

    public void leave() throws IOException {
        this.multicastSocket.leaveGroup(this.group);
        this.multicastSocket.close();
    }

    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException e) {}
    }

    public void subscribe(MessageObserver o){
        observer = o;
    }
    public void unsubscribe(MessageObserver o){
        if (observer == o){
            observer = null;
        }
    }

    public String getMessage() {
        return message;
    }
}
