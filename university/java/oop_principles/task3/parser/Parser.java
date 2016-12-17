package task3.parser;

import task3.parser.http.HTTPRequestHeader;
import task3.parser.http.HTTPRequestMethod;
import task3.parser.http.HeaderAttribute;
import task3.parser.http.HeaderAttributeTag;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import java.util.ArrayList;
import task3.Launcher;
import task3.parser.exceptions.ParserException;

/**
 * @author Daniel Mizzi 491296M
 */
public class Parser {
    
    /**
     * Parses the request and constructs an HTTPRequestHeader object
     * 
     * @param lines		the source to parse 
     * @return			an HTTPRequestHeader object
     * @throws ParserException	thrown if an error occurs during parsing
     */
    public static HTTPRequestHeader parseRequest(final ArrayList<String> lines) throws ParserException {
	final ArrayList<HeaderAttribute> attributes = new ArrayList<>();
	
	final String requestMethodSource = getLineWithTag("^(GET|POST)\\s{1}\\S+\\s{1}\\S+$", lines);
	HTTPRequestMethod methodType = null;
	if (requestMethodSource == null) {
	    throw new ParserException("Failed to parse HTTP Request Method");
	} else {
	    final int indexOfWhitespace = requestMethodSource.indexOf(" ");
	    final String method = requestMethodSource.substring(0, indexOfWhitespace);
	    final String source = requestMethodSource.substring(indexOfWhitespace + 1, requestMethodSource.length());
	    methodType = "GET".equals(method) ? HTTPRequestMethod.GET : HTTPRequestMethod.POST;
	    attributes.add(new HeaderAttribute(HeaderAttributeTag.METHOD, source));
	}
	
	
	final String[] tags = {"Host", "Connection", "Accept", "Upgrade-Insecure-Requests", "User-Agent", "Accept-Encoding", "Accept-Language"};
	final HeaderAttributeTag[] tagValues = HeaderAttributeTag.values();
	for (int i = 0; i < tags.length; i ++) {
	    final String source = getLineWithTag("^" + tags[i] + ":\\s{1}\\S+(.*)$", lines);
	    if (source == null) {
		throw new ParserException("Failed to parse " + tags[i]);
	    } else {
		final HeaderAttribute attribute = new HeaderAttribute(tagValues[i + 1], source.substring(source.indexOf(":") + 1));
		attributes.add(attribute);
	    }
	}
 	return new HTTPRequestHeader(methodType, attributes.toArray(new HeaderAttribute[attributes.size()]));
    }
    
    /**
     * Gets the line with the given tag pattern
     * 
     * @param tagRegex	the regular expression to match
     * @param lines	the lines to search through
     * @return		the line with the given tag pattern
     */
    private static String getLineWithTag(final String tagRegex, final ArrayList<String> lines) {
	for (final String s : lines) {
	    if (s.matches(tagRegex)) {
		return s;
	    }
	}
	return null;
    }
    
    /**
     * Gets the directory where examples are stored
     * 
     * @return	the directory where examples are stored
     */
    public static String getStorageDirectory() {        
	try {
            File temp = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String absolutePath = temp.getAbsolutePath();
            return absolutePath + File.separator + ".." + File.separator + "files" + File.separator;
        } catch (URISyntaxException uri) {
            uri.printStackTrace();
        }
        return "";
    }
    
    /**
     * Gets the contents of the given File
     * 
     * @param file	    the File to read from
     * @return		    the contents of the given File in an ArrayList
     * @throws Exception    thrown if any exception occurred during parsing
     */
    public static ArrayList<String> readFile(final File file) throws Exception {
	final ArrayList<String> lines = new ArrayList<>();
	
	final BufferedInputStream bis;
	final BufferedReader d;
	try (FileInputStream fis = new FileInputStream(file)) {
	    bis = new BufferedInputStream(fis);
	    d = new BufferedReader(new InputStreamReader(bis));
	    String nextLine;
	    while ((nextLine = d.readLine()) != null) {
		lines.add(nextLine + "\n");
	    }
	    if (lines.size() > 0) {
		final String last = lines.get(lines.size() - 1).substring(0, lines.get(lines.size() - 1).length() - 1); // remove last new line
		lines.remove(lines.size() - 1);
		lines.add(last);
	    }
	}
	bis.close();
	d.close();
	return lines;
    }
}