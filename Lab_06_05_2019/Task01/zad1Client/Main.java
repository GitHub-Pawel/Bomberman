import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static InetAddress stringToAddress(String address){
        try {
            return InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        /*************************************************************************************
         *  5) Stwórz nowy obiekt klasy Client. Pamietaj o argumentach,                      *
         *     ktore musisz przekazac do konstruktora klasy, aby obiekt                      *
         *     zostal stowrzony poprawnie. PODPOWIEDŹ: Skorzystaj z metody stringToAddress.  *
         *************************************************************************************/


        /*************************************************************************************
         *  6) Skorzystaj z odpowiedniej metody klasy Client, w celu nawiazania polaczenia.  *
         *************************************************************************************/


        /*************************************************************************************
         *  7) Wyswietl otrzymana od serwera wiadomosc.                                      *
         *************************************************************************************/


        /*************************************************************************************
         *  8) Skorzystaj z odpowiedniej metody klasy Client, w celu zakonczenia polaczenia. *
         *************************************************************************************/


    }
}
