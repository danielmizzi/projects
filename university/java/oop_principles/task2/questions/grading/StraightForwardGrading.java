package task2.questions.grading;

/**
 * @author Daniel Mizzi 491296M
 */
public class StraightForwardGrading implements GradingSystem {

    /**
     * Gets the grade for the given answers while applying the Straight Forward Grading system
     * If all answers are correct, 1 point is awarded
     * Otherwise, the grade resultant is 0
     * 
     * @param expectedCorrectAnswers	the amount of expected correct answers in some question
     * @param correctAnswers		the amount of correct answers in some question
     * @return				the grade for the given answers
     */
    @Override
    public int grade(final int expectedCorrectAnswers, final int correctAnswers) {
	// award 1pt if answer is fully correct
	// deduct 1pt if answer is not fully correct
	return expectedCorrectAnswers == correctAnswers ? 1 : 0;
    }
}