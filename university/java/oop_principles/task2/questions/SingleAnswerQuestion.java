package task2.questions;

import task2.exceptions.answers.IncorrectNumberOfAnswersException;
import task2.questions.grading.GradingSystem;

/**
 * @author Daniel Mizzi 491296M
 */
public class SingleAnswerQuestion extends Question {

    /**
     * Gets the grade using the given GradingSystem for the provided answers
     * 
     * @param gradingSystem the GradingSystem to use
     * @param answers	    the provided answers
     * @return		    the grade obtained from the GradingSystem provided
     */
    @Override
    public int getGrade(final GradingSystem gradingSystem, final String... answers) {
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
	if (answers.length > 1) {
	    // throw incorrect number of answers exception
	    throw new IncorrectNumberOfAnswersException("This question takes at most one answer, please try again!");
	}
    }
}