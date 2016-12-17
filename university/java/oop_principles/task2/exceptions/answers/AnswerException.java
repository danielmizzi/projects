package task2.exceptions.answers;

/**
 * @author Daniel Mizzi 491296M
 */
public class AnswerException extends Exception {
    
    /**
     * The error which this AnswerException will display
     */
    private final String error;
    
    /**
     * Initializes an AnswerException with the given error message
     * AnswerException is thrown when an issue related to the answer inputted arises
     * 
     * @param error the error message to display
     */
    public AnswerException(final String error) {
	this.error = error;
    }
    
    /**
     * The error message attached to this AnswerException
     * 
     * @return	the error message
     */
    public String getError() {
	return error;
    }
    
    /**
     * Gets the error message in a properly formatted manner
     * 
     * @return	the error message in a properly formatted manner
     */
    @Override
    public String getMessage() {
	return "[" + this.getClass().getSimpleName() + "] " + error;
    }
}