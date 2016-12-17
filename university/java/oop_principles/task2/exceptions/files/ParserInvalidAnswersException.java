package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserInvalidAnswersException extends ParserException {

    /**
     * Initializes a ParserInvalidAnswersException with the given error message
     * ParserInvalidAnswersException is thrown when the answers found for some question type are not correct
     * 
     * @param error the error message to display
     */
    public ParserInvalidAnswersException(String error) {
	super(error);
    }
}