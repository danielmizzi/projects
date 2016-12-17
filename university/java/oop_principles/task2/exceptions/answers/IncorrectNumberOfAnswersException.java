package task2.exceptions.answers;

/**
 * @author Daniel Mizzi 491296M
 */
public class IncorrectNumberOfAnswersException extends AnswerException {

    /**
     * Initializes an IncorrectNumberOfAnswersException with the given error message
     * IncorrectNumberOfAnswersException is thrown when the amount of answers provided are not enough or too many
     * 
     * @param error the error message to display
     */
    public IncorrectNumberOfAnswersException(String error) {
	super(error);
    }
}