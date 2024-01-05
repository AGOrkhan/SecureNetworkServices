package TicTacToeChat;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicServer {
    private static final int PORT = 17777;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "");
        System.setProperty("javax.net.ssl.keyStorePassword", "");

        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try (SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(PORT)) {
            System.out.println("SSL Server started. Waiting for connections...");

            ExecutorService executorService = Executors.newFixedThreadPool(10);

            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                Runnable clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            System.err.println("Error starting SSL server on port " + PORT + ": " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final SSLSocket clientSocket;
        private final char[] board = new char[9];
        private final Random rand = new Random();

        public ClientHandler(SSLSocket socket) {
            this.clientSocket = socket;
            for (int i = 0; i < 9; i++) {
                board[i] = '-';
            }
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                sendBoard(out);

                String clientInput;
                while ((clientInput = in.readLine()) != null) {
                    if (clientInput.equalsIgnoreCase("exit")) {
                        break;
                    }

                    int move = parseMove(clientInput);
                    if (move != -1 && isCellAvailable(move)) {
                        board[move - 1] = 'X';
                        if (checkWin('X')) {
                            out.println("You win!");
                            break;
                        } else if (isBoardFull()) {
                            out.println("It's a tie!");
                            break;
                        }

                        makeServerMove();
                        sendBoard(out);

                        if (checkWin('O')) {
                            out.println("Server wins!");
                            break;
                        } else if (isBoardFull()) {
                            out.println("It's a tie!");
                            break;
                        }
                    } else {
                        out.println("Invalid move. Please enter a number from 1 to 9 for an available cell.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        private int parseMove(String input) {
            try {
                int move = Integer.parseInt(input);
                if (move >= 1 && move <= 9) {
                    return move;
                }
            } catch (NumberFormatException ignored) {
            }
            return -1;
        }

        private boolean isCellAvailable(int move) {
            return board[move - 1] == '-';
        }

        private void makeServerMove() {
            int move;
            do {
                move = rand.nextInt(9) + 1;
            } while (!isCellAvailable(move));
            board[move - 1] = 'O';
        }

        private boolean checkWin(char player) {
            // Check rows and columns
            for (int i = 0; i < 3; i++) {
                if ((board[i * 3] == player && board[i * 3 + 1] == player && board[i * 3 + 2] == player) ||
                        (board[i] == player && board[3 + i] == player && board[6 + i] == player)) {
                    return true;
                }
            }
            // Check diagonals
            if ((board[0] == player && board[4] == player && board[8] == player) ||
                    (board[2] == player && board[4] == player && board[6] == player)) {
                return true;
            }
            return false;
        }

        private boolean isBoardFull() {
            for (char cell : board) {
                if (cell == '-') {
                    return false;
                }
            }
            return true;
        }

        private void sendBoard(PrintWriter out) {
            StringBuilder boardString = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                boardString.append(board[i]);
                if (i < 8) boardString.append(",");
            }
            out.println(boardString.toString());
            out.flush();
        }
    }
}
