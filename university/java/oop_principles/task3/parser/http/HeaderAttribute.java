package task3.parser.http;

/**
 * @author Daniel Mizzi 491296M
 */
public class HeaderAttribute {
   
    /**
     * The tag attached to this HeaderAttribute
     */
    private final HeaderAttributeTag tag;
    
    /**
     * The source of this HeaderAttribute
     */
    private final String source;
    
    /**
     * Initializes a new HeaderAttribute with the given tag and source
     * 
     * @param tag	the tag of this HeaderAttribute
     * @param source	the source of this HeaderAttribute
     */
    public HeaderAttribute(final HeaderAttributeTag tag, final String source) {
	this.tag = tag;
	this.source = source;
    }
    
    /**
     * Gets the tag of this HeaderAttribute
     * 
     * @return	the tag of this HeaderAttribute
     */
    public HeaderAttributeTag getTag() {
	return tag;
    }
    
    /**
     * Gets the source of this HeaderAttribute
     * 
     * @return	the source of this HeaderAttribute
     */
    public String getSource() {
	return source;
    }
}