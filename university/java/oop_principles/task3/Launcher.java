package task3;

import task3.connection.ServerHandler;
import task3.connection.SocketReader;
import task3.connection.SocketWriter;
import task3.parser.Parser;
import task3.parser.http.HTTPRequestHeader;
import task3.parser.http.HTTPRequestMethod;
import task3.parser.http.HTTPStatusCode;

import java.io.File;
import java.io.IOException;

import java.net.Socket;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import task3.parser.exceptions.ParserException;

/**
 * @author Daniel Mizzi 491296M
 */
public class Launcher {
    
    /**
     * The main launcher
     * 
     * @param args  the command line arguments
     */
    public static void main(final String[] args) {
	try {
	    System.out.println("Initializing server handler...");
	    
	    final ServerHandler server = new ServerHandler(4567, true); // initialize server on port 4567
	    
	    System.out.println("Initialized server handler on port " + server.getPort() + " at 127.0.0.1 !");
	    System.out.println("... awaiting connection!");
	    
	    Socket client;
	    while ((client = server.listenConnection()) != null) { // keep connection
		
		System.out.println("Initialized connection...");
		
		// use SocketReader to read the data
		final SocketReader socketReader = new SocketReader(client); 
		
		System.out.println("Initialized socket reader...");
		
		// retrieve the data in the list
		final ArrayList<String> data = socketReader.readData();
		
		// default status code set to OK
		HTTPStatusCode resultCode = HTTPStatusCode.OK;
		
		HTTPRequestHeader header;
		
		// in the case of a parser exception, exit program
		try {
		    header = Parser.parseRequest(data);
		} catch (final ParserException exception) {
		    System.out.println(exception.getMessage());
		    return;
		}
		
		ArrayList<String> fileContents = null;
		if (HTTPRequestMethod.GET != header.getRequestMethod()) {
		    // only GET is allowed
		    // if something other than GET is found,
		    // set the status code to METHOD_NOT_ALLOWED
		    resultCode = HTTPStatusCode.METHOD_NOT_ALLOWED;
		} else {
		    final File file = new File(Parser.getStorageDirectory() + header.getFilePath());
		    final String fileName = file.getName();
		    if (fileName.endsWith(".html") || fileName.endsWith(".txt")) {
			// ensure the file name is within the supported range
			// this design allows for easy extension expansion
			// an even better implementation to allow for better managament is to have a list of supported extensions
			// and compare the extension to the list
			// such a list could be created on start, to allow for easy extension additions
			
			// in the case of failing to read the file,
			// throw a INTERNAL_SERVER_ERROR
			if (file.exists()) {
			    try { 
				fileContents = Parser.readFile(file);
			    } catch (final Exception exception) {
				System.out.println(exception.getMessage());
				fileContents = null;
				resultCode = HTTPStatusCode.INTERNAL_SERVER_ERROR;
			    }
			} else {
			    // throw a NOT_FOUND error if file was not found
			    resultCode = HTTPStatusCode.NOT_FOUND;
			}
		    } else {
			// if the file type is not supported,
			// we throw an UNSUPPORTED_MEDIA_TYPE error
			resultCode = HTTPStatusCode.UNSUPPORTED_MEDIA_TYPE;
		    }
		}
		
		System.out.println("Initialized socket writer...");
		
		// open a SocketWriter instance to allow for writing
		final SocketWriter socketWriter = new SocketWriter(client);
		
		// write the response
		final ArrayList<String> response = new ArrayList<>();
		response.add(header.getHTTPVersion() + " " + resultCode.getCode() + " " + resultCode.getName() + "\r\n");
		response.add("Content-Type: text/" + header.getContentType() + "; charset=UTF-8\r\n");
		response.add("Date: " + header.getDate() + "\r\n");
		response.add("Server: WWW-DM\r\n");
		response.add("\r\n");
		
		System.out.println("\nHeader content:");
		response.stream().forEach((s) -> {
		    System.out.print(s);
		});
		System.out.println();
		
		if (fileContents != null) {
		    response.addAll(fileContents);
		}
		
		if (resultCode == HTTPStatusCode.OK) {
		    System.out.println("Writing data to socket...!");
		} else {
		    System.out.println("Something bizarre has happened... result status code: " + resultCode);
		}
		
		socketWriter.writeData(response);
		
		// quit the socket writers/readers
		socketWriter.quit();
		socketReader.quit();
		
		// close the client only after the sockets are closed
		client.close();
		break;
	    }
	} catch (IOException ex) {
	    Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
}