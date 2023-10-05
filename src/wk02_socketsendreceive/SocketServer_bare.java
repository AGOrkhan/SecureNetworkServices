package wk02_socketsendreceive;

import java.io.*;
import java.net.*;

public class SocketServer_bare {
    public static void main( String[] args ) throws IOException {
         
    	int portNumber = 17777;

    	ServerSocket ss = new ServerSocket( portNumber );
    	Socket s = ss.accept();
        BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        if (s.isBound()){
            System.out.println( "Incoming connection:"+ s.getInetAddress()+":"+s.getPort());
        }
        while (s.isConnected()) {
            System.out.println( "recv: " + in.readLine() );
            String message = userInput.readLine();
            if (message.contains("exit")){
                s.close();
            }
        }
        }
    }
