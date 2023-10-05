package wk02_socketsendreceive;

import java.io.*;
import java.net.*;

public class SocketServer_bare {
    public static void main( String[] args ) throws IOException {
         
    	int portNumber = 17777;
    	
    	ServerSocket ss = new ServerSocket( portNumber );
    	Socket s = ss.accept();
        BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

        while ( true ) {
            System.out.println( "recv: " + in.readLine() );
        }
    }
}