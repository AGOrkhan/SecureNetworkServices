package ChatSocket;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatSocketServer {

    private static final int PORT = 17777;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for connections...");

            ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust the thread pool size as needed

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                // Create a new thread to handle the client
                Runnable clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            System.err.println("Error starting server on port " + PORT + ": " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true );
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String clientInput;
                while (!clientSocket.isClosed()) {
                    clientInput = in.readLine();
                    if (clientInput.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                        clientSocket.close();
                        break;
                    }

                    System.out.println("Received from client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ": " + clientInput);

                    // Send a response to the client
                    out.println("Server received from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ": " + clientInput);
                }
            } catch (IOException e) {
                System.err.println("Error handling client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + ": " + e.getMessage());
            }
        }
    }
}
