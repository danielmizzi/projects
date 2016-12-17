package task2.exceptions.answers;

/**
 * @author Daniel Mizzi 491296M
 */
public class AnswerOutOfBoundsException extends AnswerException {
    
    /**
     * Initializes an AnswerOutOfBoundsException with the given error message
     * AnswerOutOfBoundsException is thrown when the answer provided is neither of the possible answers
     * 
     * @param error the error message to display
     */
    public AnswerOutOfBoundsException(String error) {
	super(error);
    }
}