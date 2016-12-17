package krr;

/**
 * @author Daniel Mizzi (491296M)
 */
public enum Connector {
    
    IS_A, IS_NOT_A;
    
    /**
     * Gets the Connector name in the required format
     * 
     * @return The Connector name in the required format
     */
    public String getName() {
        return name().replaceAll("_", "-");
    }
}