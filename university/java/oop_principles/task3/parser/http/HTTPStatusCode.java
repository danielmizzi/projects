package task3.parser.http;

/**
 * @author Daniel Mizzi 491296M
 */
public enum HTTPStatusCode {
    
    OK(200),
    BAD_REQUEST(400), // to add
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    LENGTH_REQUIRED(411), // to add
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER_ERROR(500),
    HTTP_VERSION_NOT_SUPPORTED(505); // to add
    
    /**
     * The status code
     */
    private final int code;
    
    /**
     * Initializes a status code with the given number
     * 
     * @param code  the code shown on the page
     */
    HTTPStatusCode(final int code) {
	this.code = code;
    }
    
    /**
     * Gets the code for this status
     * 
     * @return	the code for this status
     */
    public int getCode() {
	return code;
    }
    
    /**
     * Gets the name in the proper format required by the program
     * 
     * @return	the name in the proper format required by the program
     */
    public String getName() {
	final String[] split = name().split("_");
	if (ordinal() == 1) {
	    return split[0];
	}
	for (String s : split) {
	    s = s.substring(0, 1) + s.substring(1, s.length()).toLowerCase();
	}
	return String.join(" ", split);
    }
}