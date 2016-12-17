package task2.questions;

import task2.questions.grading.GradingSystem;

/**
 * @author Daniel Mizzi 491296M
 */
public class MultiAnswerQuestion extends Question {

    /**
     * Gets the grade using the given GradingSystem for the provided answers
     * 
     * @param gradingSystem the GradingSystem to use
     * @param answers	    the provided answers
     * @return		    the grade obtained from the GradingSystem provided
     */
    @Override
    public int getGrade(final GradingSystem gradingSystem, final String... answers) {
	int correctAnswers = 0;
	for (final String answer : answers) {
	    final String key = "A".concat(Integer.toString(Integer.parseInt(answer) - 1));
	    correctAnswers += getCorrectAnswers().contains(key) ? 1 : 0;
	}
	return gradingSystem.grade(getCorrectAnswers().size(), correctAnswers);
    }
    
    /**
     * Checks whether the input is correct
     * 
     * @param answers	the answers to check the format of
     */
    @Override
    public void checkInput(final String... answers) {
	// this method was overridden to remove the exception throw
    }
    
    /**
     * 
     * 
     * @return 
     */
    @Override
    public int getMaximumCorrectAnswers() {
	return Integer.MAX_VALUE;
    }
}