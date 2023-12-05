package TicTacToeChat;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.time.chrono.IsoChronology;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class TicServer {

    private static final int PORT = 17777;
    private static final char[] board = new char[9];
    private static boolean PlayerTurn = true;

    public static void main(String[] args) {
        Arrays.fill(board, '-');

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for a connection...");

            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                String clientInput;
                while (!clientSocket.isClosed()) {
                    clientInput = in.readLine();
                    if (clientInput.equalsIgnoreCase("exit")) {
                        clientSocket.close();
                        break;
                    }

                    processMove(clientInput, out);

                    if (checkWin()) {
                        out.println("Game Over: You lose!");
                        break;
                    } else if (isBoardFull()) {
                        out.println("Game Over: Draw!");
                        break;
                    }
                    while (!PlayerTurn){
                        int randomNum = ThreadLocalRandom.current().nextInt(0, 9 + 1);
                        if (board[randomNum] == '-'){
                            board[randomNum] = 'O';
                            PlayerTurn = true;
                            break;
                        }
                    }
                    out.println(getBoardString());
                    for (int i = 0; i < 3; i++) {
                        String part = getBoardString().substring(i * 3, (i + 1) * 3);
                        System.out.println(part);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server on port " + PORT + ": " + e.getMessage());
        }
    }

    private static void processMove(String move, PrintWriter out) {
        // Process the move from client
        // Assuming the client sends moves in "row col" format
        while (PlayerTurn) {
            try {
                int index = Integer.parseInt(move);

                if (board[index] == '-') {
                    board[index] = 'X';
                    PlayerTurn = false;
                } else {
                    out.println("Invalid move, try again.");
                }
            } catch (Exception e) {
                out.println("Invalid input. Please write in 1 -9 format");
            }
        }
    }

    private static String getBoardString() {
        StringBuilder sb = new StringBuilder();
        for (char c : board) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static boolean checkWin() {
        // Implement win checking logic
        // Return true if there's a win condition
        return false;
    }

    private static boolean isBoardFull() {
        for (char c : board) {
            if (c == '-') {
                return false;
            }
        }
        return true;
    }
}
