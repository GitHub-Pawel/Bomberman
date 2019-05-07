public class Main {
    public static void main(String[] args) {
        Server server = new Server(7000);
        String message =
                "1.  Dzik jest dziki, dzik jest zły\n" +
                "2.  Dzik ma bardzo ostre kły.\n" +
                "3.  Kto spotyka w lesie dzika,\n" +
                "4.  Ten na drzewo zaraz zmyka.\n" +
                "5.  \n" +
                "6.  Dzik jest dziki, dzik jest zły\n" +
                "7.  Dzik ma bardzo ostre kły.\n" +
                "8.  Kto spotyka w lesie dzika,\n" +
                "9.  Ten na drzewo zaraz zmyka.\n" +
                "10. \n" +
                "11. Dzik jest dziki, dzik jest zły\n" +
                "12. Dzik ma bardzo ostre kły.\n" +
                "13. Kto spotyka w lesie dzika,\n" +
                "14. Ten na drzewo zaraz zmyka.\n" +
                "15. \n" +
                "16. Dzik jest dziki, dzik jest zły\n" +
                "17. Dzik ma bardzo ostre kły.\n" +
                "18. Kto spotyka w lesie dzika,\n" +
                "19. Ten na drzewo zaraz zmyka. \n" +

                "20. Dzik jest dziki, dzik jest zły\n" +
                "21. Dzik ma bardzo ostre kły.\n" +
                "22. Kto spotyka w lesie dzika,\n" +
                "23. Ten na drzewo zaraz zmyka.\n" +
                "24. \n" +
                "25. Dzik jest dziki, dzik jest zły\n" +
                "26. Dzik ma bardzo ostre kły.\n" +
                "27. Kto spotyka w lesie dzika,\n" +
                "28. Ten na drzewo zaraz zmyka.\n" +
                "29. \n" +
                "30. Dzik jest dziki, dzik jest zły\n" +
                "31. Dzik ma bardzo ostre kły.\n" +
                "32. Kto spotyka w lesie dzika,\n" +
                "33. Ten na drzewo zaraz zmyka.\n" +
                "33.\n" +
                "34. Dzik jest dziki, dzik jest zły\n" +
                "35. Dzik ma bardzo ostre kły.\n" +
                "36. Kto spotyka w lesie dzika,\n" +
                "37. Ten na drzewo zaraz zmyka. \n";
        int clientCount = 1;

        while(true) {
            server.startConnection();

            if (server.getClientSocket().isConnected()) {
                server.sendMessageUTF(message + "You are client NO.: " + clientCount);

                System.out.println("Connected with Cliet NO.: " + clientCount + ", address: " + server.getClientSocket().getInetAddress() + "\n");
                clientCount++;
            }

            server.stopConnection();
        }
    }
}
