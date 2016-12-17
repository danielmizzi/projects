package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserUnknownTagException extends ParserException {

    /**
     * Initializes a ParserUnknownTagException with the given error message
     * ParserUnknownTagException is thrown when an unknown tag is encountered
     * 
     * @param error the error message to display
     */
    public ParserUnknownTagException(String error) {
	super(error);
    }
    
    /**
     * Gets a detailed and formatted message for the user
     * 
     * @return	a detailed and formatted message
     */
    @Override
    public String getMessage() {
	return "[ParserUnknownTagException] The following tag could not be parsed: '" + getError() + "'";
    }
}