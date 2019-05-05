public class Main {
    public static void main(String[] args) {
        /*************************************************************************************
         *  5) Stwórz nowy obiekt klasy Server. Pamietaj o argumentach,                      *
         *     ktore musisz przekazac do konstruktora klasy, aby obiekt                      *
         *     zostal stowrzony poprawnie:                                                   *
         *              Server server = ...                                                  *
         *************************************************************************************/

        String message = "Grats, you are Client NO.: ";
        int clientCount = 1;

        while(true) {
            /*************************************************************************************
             *  6) Skorzystaj z odpowiedniej metody klasy Server, w celu nawiazania polaczenia.  *
             *************************************************************************************/

            if (server.getClientSocket().isConnected()) {
                /*************************************************************************************
                 *  7) Wywołaj odpowiednią metodę na obiekcie server, w celu wysłania wiadomości     *
                 *     do Clienta. Niech treścią wiadomości będzie złożenie obiektu message oraz     *
                 *     zmiennej clientCount (treść taka sama, jak w poprzednim zadaniu).             *
                 *************************************************************************************/


                System.out.println("Connected with Cliet NO.: " + clientCount + ", address: " + server.getClientSocket().getInetAddress());
                clientCount++;
            }

            /*************************************************************************************
             *  8) Skorzystaj z odpowiedniej metody klasy Server, w celu zakonczenia polaczenia. *
             *************************************************************************************/

        }
    }
}
