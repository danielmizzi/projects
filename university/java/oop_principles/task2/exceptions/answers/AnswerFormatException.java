package task2.exceptions.answers;

/**
 * @author Daniel Mizzi 491296M
 */
public class AnswerFormatException extends AnswerException {

    /**
     * Initializes an AnswerFormatException with the given error message
     * AnswerFormatException is thrown when the answer provided is of the incorrect format
     * 
     * @param error the error message to display
     */
    public AnswerFormatException(String error) {
	super(error);
    }
}