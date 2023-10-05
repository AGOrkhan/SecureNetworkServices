package wk02_socketsendreceive;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient_bare {
    public static void main( String[] args ) throws IOException {
         
    	int portNumber = 17777;
        Scanner Temp = new Scanner(System.in);
        System.out.println("Enter IP adress:");
        String hostName = Temp.nextLine();
    	
        Socket s = new Socket( hostName, portNumber );
        
        PrintWriter out = new PrintWriter( s.getOutputStream(), true );
        BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

        BufferedReader userInput = new BufferedReader( new InputStreamReader(System.in) );

        String message = "";
        while ( true ) {
        	message = userInput.readLine();	//reads keyboard until user hits a newline
            out.println( message );
            if (message.contains("exit")){
                s.close();
                System.exit(0);
            }
        }
    }
}




