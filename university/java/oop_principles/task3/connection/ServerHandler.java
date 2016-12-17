package task3.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Daniel Mizzi 491296M
 */
public class ServerHandler {
    
    /**
     * The port to listen on
     */
    private final int port;
    
    /**
     * Whether to keep the connection alive
     */
    private final boolean keepAlive;
    
    /**
     * The ServerSocket object
     */
    private final ServerSocket server;
    
    /**
     * Initializes a new ServerHandler instance on the default port 4567
     * 
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public ServerHandler() throws IOException {
	this(4567);
    }
    
    /**
     * Initializes a new ServerHandler instance with the given port
     * 
     * @param port	    the port to listen to the connection on
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public ServerHandler(final int port) throws IOException {
	this(port, true);
    }
    
    /**
     * Initializes a new ServerHandler instance with the given configuration 
     * 
     * @param port	    the port to listen to the connection on
     * @param keepAlive	    true to keep the connection alive; otherwise false
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public ServerHandler(final int port, final boolean keepAlive) throws IOException {
	this.port = port;
	this.keepAlive = keepAlive;
	
	this.server = new ServerSocket(port);
    }
    
    /**
     * Gets the port that the connection is being listened on
     * 
     * @return	the port that the connection is being listened on
     */
    public int getPort() {
	return port;
    }
    
    /**
     * Gets whether the connection is being kept alive
     * 
     * @return	true if the connection is being kept alive; otherwise false
     */
    public boolean isKeepAlive() {
	return keepAlive;
    }
    
    /**
     * Attempts to listen to the connection
     * 
     * @return		    the Socket if connection was successful
     * @throws IOException  thrown in the case of Input/Output errors
     */
    public Socket listenConnection() throws IOException {
	final Socket socket = server.accept();
	socket.setKeepAlive(keepAlive);
	return socket;
    }
}