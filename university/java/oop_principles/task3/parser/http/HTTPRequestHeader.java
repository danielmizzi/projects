package task3.parser.http;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Daniel Mizzi 491296M
 */
public class HTTPRequestHeader {
    
    /**
     * The method being used (GET | POST)
     */
    private final HTTPRequestMethod method;
    
    /**
     * The attributes found within this header
     */
    private final HeaderAttribute[] attributes;
    
    /**
     * Initializes a new HTTPRequestHeader instance with the given request method and attributes
     * 
     * @param method	    the method found in the header
     * @param attributes    the attributes in the header
     */
    public HTTPRequestHeader(final HTTPRequestMethod method, final HeaderAttribute... attributes) {
	this.method = method;
	this.attributes = attributes;
    }
    
    /**
     * Gets the request method
     * 
     * @return	the request method
     */
    public HTTPRequestMethod getRequestMethod() {
	return method;
    }
    
    /**
     * Gets the file path being asked for in the header
     * 
     * @return	the file path being asked for in the header
     */
    public String getFilePath() {
	return getAttribute(HeaderAttributeTag.METHOD).getSource().split(" ")[0];
    }
    
    /**
     * Gets the HTTP version as found in the header
     * 
     * @return	the HTTP version as found in the header
     */
    public String getHTTPVersion() {
	return getAttribute(HeaderAttributeTag.METHOD).getSource().split(" ")[1];
    }
    
    /**
     * Gets the content type as found in the header
     * 
     * @return	the content type as found in the header
     */
    public String getContentType() {
	return getFilePath().contains("html") ? "html" : "plain";
    }
    
    /**
     * Gets the date format as given in the assignment
     * 
     * @return	the date format as given in the assignment
     */
    public String getDate() {
	final SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss");
	formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	
	final Date current = new Date();
	return formatter.format(current) + " GMT";
    }
    
    /**
     * Gets the attribute with the given HeaderAttributeTag
     * 
     * @param attributeTag  the HeaderAttributeTag to match
     * @return		    the attribute with the given HeaderAttributeTag
     */
    public HeaderAttribute getAttribute(final HeaderAttributeTag attributeTag) {  
	for (final HeaderAttribute a : attributes) {
	    if (a.getTag() == attributeTag) {
		return a;
	    }
	}
	return null;
    }
    
    /**
     * Gets the attributes for this header
     * 
     * @return	the attributes for this header
     */
    public HeaderAttribute[] getAttributes() {
	return attributes;
    }
}