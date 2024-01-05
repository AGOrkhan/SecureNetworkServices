package TicTacToeChat;

import java.io.*;
import java.net.*;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;

public class TicClient {

    private static final int PORT = 17777;
    private static final String hostName = "127.0.0.1";

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\upper\\IdeaProjects\\SecureNetworkServices\\src\\TicTacToeChat\\clienttruststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "khan321");

        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

        try (SSLSocket socket = (SSLSocket) ssf.createSocket(hostName, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to SSL server at " + hostName + ":" + PORT);

            String fromServer;

            while (true) {
                fromServer = in.readLine();
                if (fromServer == null) {
                    break;
                }

                if (fromServer.contains(",")) {
                    printBoard(fromServer);
                } else {
                    System.out.println(fromServer);
                }

                if (fromServer.equalsIgnoreCase("You win!") ||
                        fromServer.equalsIgnoreCase("Server wins!") ||
                        fromServer.equalsIgnoreCase("It's a tie!")) {
                    break;
                }

                System.out.print("Enter your move (1-9): ");
                String fromUser = userInput.readLine();
                out.println(fromUser);

                if (fromUser.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    private static void printBoard(String boardString) {
        String[] cells = boardString.split(",");
        for (int i = 0; i < cells.length; i++) {
            System.out.print(cells[i]);
            if ((i + 1) % 3 == 0) {
                System.out.println();
            } else {
                System.out.print(",");
            }
        }
    }
}