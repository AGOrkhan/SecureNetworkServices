package TicTacToeChat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TicClient {

    private static final int PORT = 17777;
    private static final String hostName = "127.0.0.1";

    public static void main(String[] args) {
        System.out.println("Connecting to " + hostName);

        try (Socket socket = new Socket(hostName, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // socket.setSoTimeout(2000);
            String message;
            String response;

            while (!socket.isClosed()) {
                System.out.println("Enter your move (1 -9) or type 'exit' to quit:");
                message = userInput.readLine();
                out.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                response = in.readLine();
                for (int i = 0; i < 3; i++) {
                    String part = response.substring(i * 3, (i + 1) * 3);
                    System.out.println(part);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
