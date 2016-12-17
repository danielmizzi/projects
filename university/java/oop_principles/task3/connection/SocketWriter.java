package task3.connection;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

import java.util.ArrayList;

/**
 * @author Daniel Mizzi 491296M
 */
public class SocketWriter {
   
    /**
     * The Socket instance to write to
     */
    private final Socket socket;
    
    /**
     * The currently active PrintWriter instance
     */
    private PrintWriter pw;
    
    /**
     * Initializes a new SocketWriter instance for the given Socket
     * 
     * @param socket	the Socket to write to
     */
    public SocketWriter(final Socket socket) {
	this.socket = socket;
    }
    
    /**
     * Attempts to write data to the given Socket
     * 
     * @param lines	    an ArrayList of data to write to the given Socket
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public void writeData(final ArrayList<String> lines) throws IOException {
	pw = new PrintWriter(socket.getOutputStream());
	
	lines.stream().forEach((line) -> {
	    pw.print(line);
	});
    }
    
    /**
     * Closes all the open streams
     * 
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public void quit() throws IOException {
	if (pw != null) {
	    pw.flush();
	    pw.close();
	}
    }
}