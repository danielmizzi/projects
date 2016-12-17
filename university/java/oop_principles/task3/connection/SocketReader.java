package task3.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Daniel Mizzi 491296M
 */
public class SocketReader {
    
    /**
     * The Socket instance to read from
     */
    private final Socket socket;
    
    /**
     * The currently active InputStream
     */
    private InputStream is;
    
    /**
     * The currently active InputStreamReader
     */
    private InputStreamReader isr;
    
    /**
     * The currently active BufferedReader
     */
    private BufferedReader in;
    
    /**
     * Initializes a new SocketReader instance for the given Socket
     * 
     * @param socket	the Socket to read from
     */
    public SocketReader(final Socket socket) {
	this.socket = socket;
    }
    
    /**
     * Attempts to read data from the given Socket
     * 
     * @return		    an ArrayList of the data read from the given Socket
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public ArrayList<String> readData() throws IOException {
	final ArrayList<String> lines = new ArrayList<>();
	
	is = socket.getInputStream();
	isr = new InputStreamReader(is);
	in = new BufferedReader(isr);
	
	String input;
	while (!"".equals(input = in.readLine())) {
	    lines.add(input);
	}
	return lines;
    }
    
    /**
     * Closes all the open streams
     * 
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public void quit() throws IOException {
	if (is != null) {
	    is.close();
	}
	if (isr != null) {
	    isr.close();
	}
	if (in != null) {
	    in.close();
	}
    }
}