import java.io.IOException;
import java.net.*;

public class UDPMulticastSender {

    String groupAddress;
    InetAddress group;
    int port;

    public UDPMulticastSender(String groupAddress, int port) throws IOException {
        this.groupAddress = groupAddress;
        this.port = port;
        this.group = InetAddress.getByName(this.groupAddress);
    }

    public void sendMessage(String message) throws IOException{
        /****************************************************************************************
         *  1) Utwórz i zainicjalizuj nowy socket, na który będziesz wysyłać dane               *
         *  2) Utwórz i zainicjalizuj pakiet z wiadomością podaną jako argument funkcji         *
         *     sendMessage, który zostanie wysłany                                              *
         *  3) Wyślij utworzony pakiet za pomocą metody send                                    *
         *  4) Zakończ połączenie za pomocą metody close                                        *
         ****************************************************************************************/

        //...
        //...
        //...
        //...

    }



}
