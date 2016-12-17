package task2.questions.grading;

/**
 * @author Daniel Mizzi 491296M
 */
public class NegativeMarkingScheme implements GradingSystem {

    /**
     * Gets the grade for the given answers while applying the Negative Marking Scheme
     * If all answers are correct, 1 point is awarded
     * Otherwise, 1 point is deducted
     * 
     * @param expectedCorrectAnswers	the amount of expected correct answers in some question
     * @param correctAnswers		the amount of correct answers in some question
     * @return				the grade for the given answers
     */
    @Override
    public int grade(final int expectedCorrectAnswers, final int correctAnswers) {
	return correctAnswers == expectedCorrectAnswers ? 1 : -1;
    }  
}