package ChatSocket;

import java.io.*;
import java.net.*;

public class ChatSocketClient {

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
                System.out.println("Enter a message or type 'exit' to quit:");
                message = userInput.readLine();
                out.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                response = in.readLine();
                System.out.println(response);

            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}

