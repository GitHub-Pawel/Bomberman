public class Main {
    public static void main(String[] args) {
        Server server = new Server(54321);
        String message = "Grats, you are Client NO.: ";
        int clientCount = 1;

        while(true) {
            server.startConnection();

            if (server.getClientSocket().isConnected()) {
                server.sendMessageUTF(message + clientCount);

                System.out.println("Connected wuth Cliet NO.: " + clientCount + ", address: " + server.getClientSocket().getInetAddress());
                clientCount++;
            }

            server.stopConnection();
        }
    }
}
