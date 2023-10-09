package wk03_networkprotocol;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class SocketProtocolClient {
    public static void main( String[] args ) throws IOException {
         
    	int portNumber = 17777;
        Scanner Temp = new Scanner(System.in);
        System.out.println("Enter IP adress:");
        String hostName = Temp.nextLine();
        System.out.println("Connecting to"+hostName);
        System.out.println("Write 'HELLO' and your username:");
        Socket s = new Socket( hostName, portNumber );
        
        PrintWriter out = new PrintWriter( s.getOutputStream(), true );
        BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

        BufferedReader userInput = new BufferedReader( new InputStreamReader(System.in) );
        s.setSoTimeout(500);
        String message = "";
        String Received = "";

        while (s.isConnected()) {
        	message = userInput.readLine();	//reads keyboard until user hits a newline
            out.println( message );
            try {
                Received = in.readLine();
            } catch (SocketTimeoutException ignored) {
                ;
            }
            if (message.contains("exit")){
                s.close();
                System.exit(0);
            }
            if (Objects.equals(Received, "1")){
                System.out.println('\u2713');
                Received = "";
            }else{
                System.out.println(Received);
            }
        }
    }
}




