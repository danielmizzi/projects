package krr;

/**
 * @author Daniel Mizzi (491296M)
 */
public class Connection {
    
    /**
     * The sub concept name
     */
    private final String subConcept;
    
    /**
     * The super concept name 
     */
    private final String superConcept;
    
    /**
     * The connector object (IS-A / IS-NOT-A)
     */
    private final Connector connector;
    
    /**
     * Constructor for this Connection object
     * 
     * @param subConcept    the sub concept name
     * @param superConcept  the super concept name
     * @param connector     the connector object
     */
    public Connection(final String subConcept, final String superConcept, final Connector connector) {
        this.subConcept = subConcept;
        this.superConcept = superConcept;
        this.connector = connector;
    }
    
    /**
     * Gets the sub concept name
     * 
     * @return The sub concept name
     */
    public String getSubConcept() {
        return subConcept;
    }
    
    /**
     * Gets the super concept name
     * 
     * @return The super concept name
     */
    public String getSuperConcept() {
        return superConcept;
    }
    
    /**
     * Gets the Connector object
     * 
     * @return The Connector object
     */
    public Connector getConnector() {
        return connector;
    }
    
    /**
     * Checks whether the given object is equal to this instance
     * 
     * @param obj The object to check for equality
     * @return true if they are equal; otherwise false
     */
    @Override
    public boolean equals(final Object obj) {
        final Connection other = (Connection) obj;
        return other != null && other.getSuperConcept().equals(superConcept) && other.getSubConcept().equals(subConcept);
    }
    
    /**
     * Converts this Connection object into a readable string
     * 
     * @return A readable string explaining this Connection object
     */
    @Override
    public String toString() {
        return getSubConcept() + " " + getConnector().getName() + " " + getSuperConcept();
    }
}