package task2.parser;

/**
 * @author Daniel Mizzi 491296M
 */
public class ParsedLine {
    
    /**
     * The tag obtained from the full source line (e.g. 'QT')
     */
    private final String tag;
    
    /**
     * The remaining part of the full source line (e.g. 'TruthQuestion')
     */
    private final String source;
    
    /**
     * Initializes a ParsedLine object with the given tag and source
     * 
     * @param tag	the tag obtained from the full line
     * @param source	the source obtained from the full line
     */
    public ParsedLine(final String tag, final String source) {
	this.tag = tag;
	this.source = source;
    }
    
    /**
     * Gets the tag 
     * 
     * @return	the tag  
     */
    public String getTag() {
	return tag;
    }
    
    /**
     * Gets the remaining source
     * 
     * @return	the source
     */
    public String getSource() {
	return source;
    }
}