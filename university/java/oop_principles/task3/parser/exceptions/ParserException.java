package task3.parser.exceptions;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserException extends Exception {
    
    /**
     * The error message to display
     */
    private final String error;
    
    /**
     * Initializes a ParserException with the given error message
     * ParserException is thrown when an issue related to parsing arises
     * 
     * @param error the error message to display
     */
    public ParserException(final String error) {
	this.error = error;
    }
    
    /**
     * Gets the error message in a properly formatted manner
     * 
     * @return	the error message in a properly formatted manner
     */
    @Override
    public String getMessage() {
	return "[ParserExeption] " + error;
    }
}