package task3.parser.http;

/**
 * @author Daniel Mizzi 491296M
 */
public enum HeaderAttributeTag {
    
    METHOD,
    HOST,
    CONNECTION,
    ACCEPT,
    UPGRADE_INSECURE_REQUESTS,
    USER_AGENT,
    ACCEPT_ENCODING,
    ACCEPT_LANGUAGE;
    
    /**
     * Gets the header name for this enum
     * 
     * @return	the header name for this enum
     */
    public String getHeaderName() {
	return getHeaderName(null);
    }
    
    /**
     * Gets the header name for this enum
     * 
     * @param method	the HTTPRequestMethod being used; null is passed if none
     * @return		the formatted header name for this enum
     */
    public String getHeaderName(final HTTPRequestMethod method) {
	if (ordinal() == 0 && method != null) {
	    return method.name();
	}
	final String[] split = name().split("_");
	for (String s : split) {
	    s = s.substring(0, 1) + s.substring(1, s.length()).toLowerCase();
	}
	return String.join("-", split);
    }
}