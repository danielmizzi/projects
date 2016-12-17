package task2.exceptions.files;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParserIncorrectQuestionConstructionException extends ParserException {

    /**
     * Initializes a ParserIncorrectQuestionConstructionException with the given error message
     * ParserIncorrectQuestionConstructionException is thrown when the question is not properly constructed in the source file
     * 
     * @param error the error message to display
     */
    public ParserIncorrectQuestionConstructionException(String error) {
	super(error);
    }
    
    /**
     * Gets a detailed and formatted message for the user
     * 
     * @return	a detailed and formatted message
     */
    @Override
    public String getMessage() {
	return"[ParserIncorrectQuestionConstructionException] Too many correct answers for the question type '" + getError() + "'";
    }
}