package wk02_socketsendreceive;

import java.io.*;
import java.net.*;

public class SocketClient_bare {
    public static void main( String[] args ) throws IOException {
         
    	int portNumber = 17777;
    	String hostName = "127.0.0.1";
    	
        Socket s = new Socket( hostName, portNumber );
        
        PrintWriter out = new PrintWriter( s.getOutputStream(), true );
        BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

        BufferedReader userInput = new BufferedReader( new InputStreamReader(System.in) );

        String message = "";
        while ( true ) {
        	message = userInput.readLine();	//reads keyboard until user hits a newline
            out.println( message );
        }
    }
}




