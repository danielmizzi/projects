package task2;

import task2.exceptions.answers.AnswerException;
import task2.exceptions.files.ParserException;
import task2.parser.Parser;
import task2.questions.Question;
import task2.questions.grading.GradingSystem;
import task2.questions.grading.NegativeMarkingScheme;
import task2.questions.grading.StraightForwardGrading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Launcher {
    
    /**
     * The Scanner object to be used throughout execution of this program
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * The main method
     * 
     * @param args		the file name to be opened
     * @throws ParserException	the exception thrown once a Parsing error occurs
     */
    public static void main(final String[] args) throws ParserException {
	ArrayList<Question> questions;
	try {
	    String fileName = args.length > 0 ? args[0] : "Example_1.txt";
	    questions = Parser.loadQuestions(fileName);
	} catch (final ParserException exception) {
	    System.out.println(exception.getMessage());
	    return;
	}
	
	GradingSystem gradingSystem = null;
	System.out.println("Please choose the grading system:");
	System.out.println("1. Straight forward grading");
	System.out.println("2. Negative marking scheme");
	do {
	    System.out.print("Input: ");
	    int input = 0;
	    try {
		input = Integer.parseInt(scanner.nextLine());
	    } catch (final NumberFormatException | InputMismatchException exception) {}
	    
	    switch (input) {
		case 1:
		    gradingSystem = new StraightForwardGrading();
		    break;
		    
		case 2:
		    gradingSystem = new NegativeMarkingScheme();
		    break;
		    
		default:
		    System.out.println("\nPlease enter a value between 1 and 2 (both inclusive)...");
	    }
	} while (gradingSystem == null);
	
	System.out.println("\nThis assignment contains the following questions:");
	questions.stream().forEach((question) -> {
	    System.out.println("- " + question.getQuestion().getSource());
	});
	
        System.out.println("\nTo answer a question, simply input a number, and separate by commas in case of multiple answers...\n");
	
	int totalGrade = 0;
	
	while (!questions.isEmpty()) {
	    final Question current = questions.get(0);
	    //current.printQuestionType();
	    System.out.println("Please answer the following question:");
	    System.out.println("Q: " + current.getQuestion().getSource());
	    final HashMap<String, String> possibleAnswers = current.getPossibleAnswers();
	    final Set<String> keys = possibleAnswers.keySet();
	    final Iterator<String> iterator = keys.iterator();
	    int i = 0;
	    while (iterator.hasNext()) {
		final String key = iterator.next();
		System.out.println((i++ + 1) + ". " + possibleAnswers.get(key));
	    }
	    String answer;
	    String[] answers = null;
	    boolean firstIteration = true;
	    do {
		System.out.print((firstIteration ? "" : "\n") + "Your answer: ");
		firstIteration = false;
		
		answer = scanner.nextLine().replaceAll(" ", "").replaceAll(",", " ");
		try {
		    current.checkFormat(answer);
		    
		    // if the format is incorrect, an exception would be thrown and this line would not be reachable
		    // hence we just check the input after the format check
		    answers = answer.split(" ");
		    current.checkInput(answers);
		} catch (AnswerException exception) {
		    System.out.println(exception.getMessage());
		    answers = null;
		    continue;
		}
		
		System.out.println();
		
		// keep iterating until we get a valid answer
		// answers will be null if it's not valid
		// so we put the check answers == null
	    } while (answers == null);
	    totalGrade += current.getGrade(gradingSystem, answers);
	    questions.remove(current);
	}
	
	System.out.println("Final grade: " + totalGrade);
    }
}