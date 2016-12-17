package task2.questions;

import task2.exceptions.answers.AnswerException;
import task2.exceptions.answers.AnswerFormatException;
import task2.exceptions.answers.AnswerOutOfBoundsException;
import task2.exceptions.answers.IncorrectNumberOfAnswersException;
import task2.parser.ParsedLine;
import task2.questions.grading.GradingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Daniel Mizzi 491296M
 */
public abstract class Question {
    
    /**
     * The map of possible answers for this Question
     */
    private final LinkedHashMap<String, String> answers = new LinkedHashMap<>();
    
    /**
     * The list of correct answers for this Question
     */
    private final ArrayList<String> correctAnswers = new ArrayList<>();
    
    /**
     * The question type (TruthQuestion | SingleAnswerQuestion | MultiAnswerQuestion)
     */
    private ParsedLine questionType;
    
    /**
     * The question itself
     */
    private ParsedLine question;
    
    /**
     * Sets the question type
     * 
     * @param questionType  the question type to set
     */
    public void setQuestionType(final ParsedLine questionType) {
	this.questionType = questionType;
    }
    
    /**
     * Gets the question type
     * 
     * @return	the question type
     */
    public ParsedLine getQuestionType() {
	return questionType;
    }
    
    /**
     * Sets the question
     * 
     * @param question	the question to set
     */
    public void setQuestion(final ParsedLine question) {
	this.question = question;
    }
    
    /**
     * Gets the question
     * 
     * @return	the question
     */
    public ParsedLine getQuestion() {
	return question;
    }
    
    /**
     * Sets the possible answers for this question
     * 
     * @param parsedLines   the possible answers to add
     */
    public void setPossibleAnswers(final ArrayList<ParsedLine> parsedLines) {
	parsedLines.stream().forEach((line) -> {
	    answers.put(line.getTag(), line.getSource());
	});
    }
    
    /**
     * Gets the possible answers for this question
     * 
     * @return	the possible answers for this question
     */
    public HashMap<String, String> getPossibleAnswers() {
	return answers;
    }
    
    /**
     * Sets the correct answers for this question
     * 
     * @param correctAnswers	the correct answers for this question
     */
    public void setCorrectAnswers(final String... correctAnswers) {
	for (final String answer : correctAnswers) {
	    this.correctAnswers.add(answer);
	}
    }
    
    /**
     * Gets the correct answers for this question
     * 
     * @return	the correct answers for this question
     */
    public ArrayList<String> getCorrectAnswers() {
	return correctAnswers;
    }
    
    /**
     * Gets the maximum amount of correct answers that this Question allows
     * 
     * @return	the maximum amount of correct answers that this Question allows
     */
    public int getMaximumCorrectAnswers() {
	return 1;
    }
    
    /**
     * Checks if the format is suitable for this particular Question instance
     * 
     * @param rawAnswer		the answer submitted for this Question
     * @throws AnswerException	thrown if an error occurs when parsing the answer
     */
    public final void checkFormat(final String rawAnswer) throws AnswerException {
	// throws AnswerFormatException
	// throws AnswerOutOfBoundsException
	if (!rawAnswer.matches("^\\d+(?:\\s{1}\\d+)*$")) {
	    throw new AnswerFormatException("Incorrect answer format; make you sure input a single number, or multiple numbers each separated by a single comma");
	}
	final String[] answersSplit = rawAnswer.split(" ");
	final int answerAmount = getPossibleAnswers().size();
	for (final String answer : answersSplit) {
	    int answerIndex = 0;
	    
	    try {
		answerIndex = Integer.parseInt(answer);
	    } catch (final NumberFormatException exception) {
	    }
	    
	    if (answerIndex < 1 || answerIndex > answerAmount) {
		throw new AnswerOutOfBoundsException("Please select an answer between " + 1 + " and " + answerAmount + " (both inclusive)");
	    }
	}
    }
    
    /**
     * Gets the grade using the given GradingSystem for the provided answers
     * 
     * @param gradingSystem the GradingSystem to use
     * @param answers	    the provided answers
     * @return		    the grade obtained from the GradingSystem provided
     */
    public abstract int getGrade(final GradingSystem gradingSystem, final String... answers);
    
    /**
     * Checks whether the input is correct
     * 
     * @param answers				    the answers to check the format of
     * @throws IncorrectNumberOfAnswersException    thrown if an incorrect amount of answers has been provided
     */
    public abstract void checkInput(final String... answers) throws IncorrectNumberOfAnswersException;
    
    /**
     * Gets the Question in a presentable view for the end-user
     * 
     * @return	the Question in a presentable view
     */
    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder();
	sb.append("--- PRINTING QUESTION ---\n");
	sb.append("Question type: ");
	sb.append(questionType.getSource());
	sb.append("\n");
	sb.append("Question: ");
	sb.append(question.getSource());
	sb.append("\n");
	sb.append("Possible answers:\n");
	answers.keySet().stream().map((key) -> {
	    sb.append(key);
	    return key;
	}).forEach((key) -> {
	    sb.append(" - ");
	    sb.append(answers.get(key));
	    sb.append("\n");
	});
	sb.append("Correct answers: ");
	for (final String line : correctAnswers) {
	    sb.append(line);
	    sb.append(", ");
	}
	return sb.toString();
    }
}