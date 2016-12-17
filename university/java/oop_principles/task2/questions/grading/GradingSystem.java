package task2.questions.grading;

/**
 * @author Daniel Mizzi 491296M
 */
public interface GradingSystem {
    
    /**
     * Gives a grade depending on the system being used
     * 
     * @param expectedCorrectAnswers	the amount of expected correct answers in some question
     * @param correctAnswers		the amount of correct answers in some question
     * @return				the grade for the given answers
     */
    public int grade(final int expectedCorrectAnswers, final int correctAnswers);
    
}