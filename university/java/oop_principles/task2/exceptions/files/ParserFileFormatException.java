package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserFileFormatException extends ParserException {
 
    /**
     * Initializes a ParserFileFormatException with the given error message
     * ParserFileFormatException is thrown when some source line cannot be parsed using the default format
     * 
     * @param error the error message to display
     */
    public ParserFileFormatException(final String error) {
	super(error);
    }
    
    /**
     * Gets a detailed and formatted message for the user
     * 
     * @return	a detailed and formatted message
     */
    @Override
    public String getMessage() {
	return "[ParserFileFormatException] The following line could not be parsed: '" + getError() + "'";
    }
}