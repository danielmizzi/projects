package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserUnorderedAnswersException extends ParserException {

    /**
     * Initializes a ParserUnorderedAnswersException with the given error message
     * ParserUnorderedAnswersException is thrown when the answers in the source file are not in proper order
     * 
     * @param error the error message to display
     */
    public ParserUnorderedAnswersException(String error) {
	super(error);
    }
}