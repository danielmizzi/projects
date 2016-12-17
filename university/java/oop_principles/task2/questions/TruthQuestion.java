package task2.questions;

import task2.exceptions.answers.IncorrectNumberOfAnswersException;
import task2.questions.grading.GradingSystem;

/**
 * @author Daniel Mizzi 491296M
 */
public class TruthQuestion extends Question {
    
    /**
     * Gets the grade using the given GradingSystem for the provided answers
     * 
     * @param gradingSystem the GradingSystem to use
     * @param answers	    the provided answers
     * @return		    the grade obtained from the GradingSystem provided
     */
    @Override
    public int getGrade(final GradingSystem gradingSystem, String... answers) {
	final String answer = answers[0];
	final String key = "A".concat(Integer.toString(Integer.parseInt(answer) - 1));
	return gradingSystem.grade(1, getCorrectAnswers().contains(key) ? 1 : 0);
    }    
    
    /**
     * Checks whether the input is correct
     * 
     * @param answers				    the answers to check the format of
     * @throws IncorrectNumberOfAnswersException    thrown if an incorrect amount of answers has been provided
     */
    @Override
    public void checkInput(String... answers) throws IncorrectNumberOfAnswersException {
	// although this regex pattern could be improved to support multiple exceptions at the same time
	// it will be avoided in this assignment to showcase multiple exceptions at various levels
	// e.g. at the top will be some FormatException, and at a level below there might be some AnswerTooBigException
	if (answers.length > 1) {
	    // throw incorrect number of answers exception
	    throw new IncorrectNumberOfAnswersException("This question takes at most one answer, please try again!");
	}
    }
}