package TicTacToeChat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicClient {

    private static final int PORT = 17777;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter IP address:");
            String hostName = scanner.nextLine();

            System.out.println("Connecting to " + hostName);

            try (Socket socket = new Socket(hostName, PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

                // socket.setSoTimeout(2000);
                String message;

                while (!socket.isClosed()) {
                    System.out.println("Enter your move (1 -9) or type 'exit' to quit:");
                    message = userInput.readLine();
                    out.println(message);

                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }

                    String response;
                    try {
                        while ((response = in.readLine()) != null) {
                            System.out.println("Board state from server: " + response);
                        }
                    } catch (SocketTimeoutException e) {
                        System.out.println("No response from server, awaiting next move.");
                    }
                }
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
