import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.zip.GZIPInputStream;

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
            this.clientSocket = new Socket(this.inetAddress, this.port);
            this.objectInputStream = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }

    public String receiveMessage(){
        try {
            /**********************************************************************************
             * 1) Pobierz skompresowane dane od Servera.                                      *
             *    PODPOWIEDŹ: Dane są przesyłane, jako tablica: byte []                       *
             **********************************************************************************/


            /**********************************************************************************
             * 2) Dokonaj dekompresji danych pobranych z Servera w poprzednim punkcie:        *
             *        a) Zdefiniuj tablicę byte [], o długości conajmniej 1100 elementów.     *
             *           Tablica ta posłuży jako buffor, do którego wpisane zostaną           *
             *           zdekompresowane dane.                                                *
             *                                                                                *
             *        b) Zdefiniuj obiekt klasy GZIPInputStream, który posłuży do dekompresji.*
             *           Przekaż  w odpowiedni sposób dane z pkt 1) do konstruktora klasy.    *
             *           Zalecane jest utworzenie obiektu pośredniego, który pozwoli          *
             *           na przekazanie tablicy byte [] do konstruktora GZIPInputStream       *
             *          (Patrz wykład)                                                        *
             *                                                                                *
             *        c) Na obiekcie utworzonym w poprzednim podpunkcie,                      *
             *           wywołaj odpowiednią metodę, która dokona dekompresji danych          *
             *           przekazanych w konstruktorze oraz zapisze wynik do tablicy,          *
             *           zdefiniowanej w podpunkcie a).                                       *
             *                                                                                *
             *        d) Pamiętaj o zamknięciu strumienia zddefiniowanego w pkt b),           *
             *           po poprawnym dokonaniu dekompresji.                                  *
             **********************************************************************************/





            /**********************************************************************************
             * 3) Odczytaj dane zdekompresowane w punkcie 2):                                 *
             *        a) Utwórz obiekt klasy ObjectInputStream. Do jego konstruktora przekaż  *
             *        tablicę byte [], zawierającą zdekompresowane dane (patrz pkt 2-a).      *
             *        Analogicznie jak w punkcie 2-b, wykorzystaj obiekt pośredni,            *
             *        za pomocą którego przekażesz tablicę byte [] do konstruktora            *
             *        ObjectInputStream.                                                      *
             *                                                                                *
             *        b) Na obiekcie utworzonym  w podpunkcie a) wywołaj metodę,              *
             *        która odczyta ciąg znaków String zakodowanych w systemie UTF-8.         *
             *        Wynik powyższej operacji przekaż bezpośrednio do instrukcji return.     *
             **********************************************************************************/




        }catch (NullPointerException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stopConnection(){
        try {
            this.clientSocket.close();
            this.objectInputStream.close();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}