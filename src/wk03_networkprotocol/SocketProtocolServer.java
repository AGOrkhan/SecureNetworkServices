package wk03_networkprotocol;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.prefs.PreferenceChangeEvent;

public class SocketProtocolServer {
    public static void main( String[] args ) throws IOException {
         
    	int portNumber = 17777;
    	
    	ServerSocket ss = new ServerSocket( portNumber );
    	Socket s = ss.accept();
        PrintWriter out = new PrintWriter( s.getOutputStream(), true );
        BufferedReader in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
        boolean User = Boolean.FALSE;

        while (s.isConnected()) {
            String Received = in.readLine();
            if (Received.contains("exit")) {
                s.close();
            }
            if (Received.contains("HELLO") && (!User)) {
                User = Boolean.TRUE;
                String[] Names = Received.split(" ");
                String Name = Names[1];
                out.println("1");
                System.out.println("Connected:" + s.getInetAddress() + ":" + s.getPort() + " User: " + Name);

            } else {
                if (User){
                    System.out.println("Recieved: " + Received);
                }else{
                    out.println("NOTRECOGNIZED");
                }
            }
        }
    }
}