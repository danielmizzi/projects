package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserCorrectAnswerNotFoundException extends ParserException {

    /**
     * Initializes a ParserCorrectAnswerNotFoundException with the given error message
     * ParserCorrectAnswerNotFoundException is thrown when the 'CA' attribute is not found within the question source
     * 
     * @param error the error message to display
     */
    public ParserCorrectAnswerNotFoundException(String error) {
	super(error);
    }
}