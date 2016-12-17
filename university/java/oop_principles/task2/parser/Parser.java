package task2.parser;

import task2.exceptions.files.ParserCorrectAnswerNotFoundException;
import task2.exceptions.files.ParserException;
import task2.exceptions.files.ParserFileFormatException;
import task2.exceptions.files.ParserIncorrectQuestionConstructionException;
import task2.exceptions.files.ParserInvalidAnswersException;
import task2.exceptions.files.ParserUnknownTagException;
import task2.exceptions.files.ParserUnorderedAnswersException;
import task2.exceptions.files.ParserUnsupportedTypeException;
import task2.questions.MultiAnswerQuestion;
import task2.questions.Question;
import task2.questions.SingleAnswerQuestion;
import task2.questions.TruthQuestion;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import java.util.ArrayList;
import task2.Launcher;

/**
 * @author Daniel Mizzi 491296M
 */
public class Parser {
    
    /**
     * Gets the list of questions found by parsing the File with the given name
     * 
     * @param fileName		the name of the File to load Questions from
     * @return			the list of Questions found in the File
     * @throws ParserException	thrown if a parsing error occurs
     */
    public static ArrayList<Question> loadQuestions(final String fileName) throws ParserException {
	final ArrayList<String> lines = readFile(fileName);
	final ArrayList<Question> questions = new ArrayList<>();
	
	if (lines.size() > 0) {
	    final ArrayList<String> currentQuestionSource = new ArrayList<>();
	    
	    for (int i = 0; i < lines.size(); i ++) {
		final String line = lines.get(i);
		// check file format
		// if incorrect format, throw exception and quit program
		// regex expression: one of the accepted tags, followed by 1 space, some non white-space character, and anything else is accepted
		if (!line.matches("^(QT|Q|A\\d+|CA)\\s{1}\\S{1}(.*)$")) {
		    if (!line.matches("^(QT|Q|A\\d+|CA)\\s{1}(.*)$")) {
			throw new ParserUnknownTagException(line.contains(" ") ? line.split(" ")[0] : line);
		    }
		    throw new ParserFileFormatException(line);
		}
		boolean lastLine;
		if ((lastLine = (i == (lines.size() - 1))) || line.startsWith("QT ")) {
		    if (currentQuestionSource.size() > 0) {
			if (lastLine) {
			    currentQuestionSource.add(line);
			}
			questions.add(generateQuestion(currentQuestionSource));
			currentQuestionSource.clear();
		    }		    
		}
		currentQuestionSource.add(line);
	    }
	}
	return questions;
    }
    
    /**
     * Generates a Question object with the given source lines
     * 
     * @param source		the source to generate the Question from
     * @return			a Question object generated using the given source lines
     * @throws ParserException	thrown if a parsing error occurs
     */
    private static Question generateQuestion(final ArrayList<String> source) throws ParserException {
	Question toGenerate;
	
	final ParsedLine questionType = getTagLine(source, "QT");
	// this is never null as otherwise it would have already thrown a ParserUnknownTagException
	
	final String questionSource = questionType.getSource();
	switch (questionSource) {
	    case "TruthQuestion":
		toGenerate = new TruthQuestion();
	    break;
		
	    case "SingleAnswerQuestion":
		toGenerate = new SingleAnswerQuestion();
	    break;
		
	    case "MultiAnswerQuestion":
		toGenerate = new MultiAnswerQuestion();
	    break;
		
	    default:
		throw new ParserUnsupportedTypeException(questionSource);
	}
	toGenerate.setQuestionType(questionType);
	
	final ParsedLine question = getTagLine(source, "Q");
	toGenerate.setQuestion(question);
	
	final ArrayList<ParsedLine> answers = getTagLines(source, "A\\d+");
	toGenerate.setPossibleAnswers(answers);
	
	// answers not indexed in order, i.e A1, A0, A23, instead should be A0, A1, A2
	for (int i = 0; i < answers.size(); i ++) {
	    final String tag = answers.get(i).getTag();
	    if (!("A" + i).equals(tag)) {
		throw new ParserUnorderedAnswersException("Found '" + tag + "', expected '" + ("A" + i) + "'");
	    }
	}
	
	final ParsedLine correctAnswer = getTagLine(source, "CA");
	// split answers (since we can have multiple answers) and add to map
	final String[] correctAnswers = correctAnswer.getSource().split(" ");
	
	// depending on type, check structure and if we have too many correct answers, throw exception
	if (correctAnswers.length > toGenerate.getMaximumCorrectAnswers()) {
	    throw new ParserIncorrectQuestionConstructionException(questionType.getSource());
	}
	toGenerate.setCorrectAnswers(correctAnswers);
	
	// a correct answer which is not listed in the set of possible answers (a possible answer may be a correct answer, but a correct answer is always a possible answer)
	for (final String answer : correctAnswers) {
	    if (!toGenerate.getPossibleAnswers().containsKey(answer)) {
		throw new ParserCorrectAnswerNotFoundException("Answer '" + answer + "' not found within the list of possible answers");
	    }
	}
	
	// other possible parser errors to check for: 
	
	// TruthQuestion must ALWAYS have two answers which are either True or False
	if ("TruthQuestion".equals(questionSource)) {
	    // use ParserIncorrectQuestionConstructionException instead ??
	    if (answers.size() != 2) {
		throw new ParserInvalidAnswersException("Invalid amount of answers for question type 'TruthQuestion'");
	    }
	    for (final ParsedLine answer : answers) {
		if (!answer.getSource().toLowerCase().matches("(true|false)")) {
		    throw new ParserInvalidAnswersException("Invalid answer '" + answer.getSource() + "' for question type 'TruthQuestion' (only True or False accepted)");
		}
	    }
	}
	return toGenerate;
    }
    
    /**
     * Gets the ParsedLine with the given tag
     * 
     * @param source	the source to search through
     * @param tag	the tag to match with
     * @return		the ParsedLine with the given tag
     */
    private static ParsedLine getTagLine(final ArrayList<String> source, final String tag) {
	final ArrayList<ParsedLine> tagLines = getTagLines(source, tag);
	return tagLines.size() > 0 ? tagLines.get(0) : null;
    }
    
    /**
     * Gets all the ParsedLines with the given tag
     * 
     * @param source	the source to search through
     * @param tag	the tag to match with
     * @return		the list of ParsedLines with the given tag
     */
    private static ArrayList<ParsedLine> getTagLines(final ArrayList<String> source, final String tag) {
	final ArrayList<ParsedLine> lines = new ArrayList<>();
	
	source.stream().filter((line) -> (line.matches("\\s*" + tag + "\\s+(.*)"))).forEach((line) -> {
	    lines.add(new ParsedLine(line.split(" ")[0], line.replaceAll("\\s*+" + tag + "\\s+", "")));
	});
	return lines;
    }
    
    /**
     * Searches for a file with the given name and proceeds to return the content line by line
     * 
     * @param fileName		the name of the File
     * @return			an ArrayList consisting of all the lines within the File
     * @throws ParserException	thrown when opening the file or reading content fails
     */
    private static ArrayList<String> readFile(final String fileName) throws ParserException {
	if (!fileName.matches("^\\S+.txt$")) {
	    throw new ParserException(fileName.endsWith(".txt") ? "Invalid file name provided" : "Unsupported file extension, only '.txt' supported!");
	}
	final File file = new File(getStorageDirectory() + fileName);
	
	final ArrayList<String> lines = new ArrayList<>();
	
	try {
	    final BufferedInputStream bis;
	    final BufferedReader d;
	    try (FileInputStream fis = new FileInputStream(file)) {
		bis = new BufferedInputStream(fis);
		d = new BufferedReader(new InputStreamReader(bis));
		String nextLine;
		while ((nextLine = d.readLine()) != null) {
		    lines.add(nextLine);
		}
	    }
	    bis.close();
	    d.close();
	} catch (final IOException exception) {
	    throw new ParserException(exception instanceof FileNotFoundException ? "File '" + fileName + "' not found" : "Input/output operation has failed");
	}
	return lines;
    }
    
    /**
     * Gets the relative path where examples are stored
     * 
     * @return	the relative path as a String where examples are stored
     */
    private static String getStorageDirectory() {        
	try {
            File temp = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String absolutePath = temp.getAbsolutePath();
            return absolutePath + File.separator + ".." + File.separator + "examples" + File.separator;
        } catch (URISyntaxException uri) {
            uri.printStackTrace();
        }
        return "";
    }
}