package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserUnsupportedTypeException extends ParserException {

    /**
     * Initializes a ParserUnsupportedTypeException with the given error message
     * ParserUnsupportedTypeException is thrown when an unknown question type is encountered
     * 
     * @param error the error message to display
     */
    public ParserUnsupportedTypeException(String error) {
	super(error);
    }

    /**
     * Gets a detailed and formatted message for the user
     * 
     * @return	a detailed and formatted message
     */
    @Override
    public String getMessage() {
	return "[ParserUnsupportedTypeException] The following type is not valid/supported: '" + getError() + "'";
    }
}
