package krr;

import java.util.LinkedList;

/**
 * @author Daniel Mizzi (491296M)
 */
public class Path {
    
    /**
     * The list of connections within this Path instance
     */
    private final LinkedList<Connection> connections;
    
    /**
     * Default Path constructor
     */
    public Path() {
        this.connections = new LinkedList<>();
    }
    
    /**
     * Constructs a Path with the given Connections
     * 
     * @param connections   the Connections to include in this Path instance
     */
    public Path(final Connection... connections) {
        this.connections = new LinkedList<>();
        
        for (final Connection connection : connections) {
            this.connections.add(connection);
        }
    }
    
    /**
     * Adds a Connection to this Path instance
     * 
     * @param connection    the Connection to add
     */
    public void addConnection(final Connection connection) {
        connections.add(connection);
    }
 
    /**
     * Gets all the Connections in this Path instance
     * 
     * @return  the Connections in this Path instance as a LinkedList
     */
    public LinkedList<Connection> getConnections() {
        return connections;
    }
    
    /**
     * Gets the size of this Path
     * 
     * @return  the size of this Path
     */
    public int size() {
        return connections.size();
    }
    
    /**
     * Gets all the nodes within this Path instance
     * 
     * @return  all the nodes within this Path instance as a String array
     */
    public String[] getNodes() {
        return toString().replaceAll("IS-A ", "").replaceAll("IS-NOT-A ", "").split(" ");
    }
    
    /**
     * Checks whether this Path instance contains the given node
     * 
     * @param node  the node to search for
     * @return      true if this Path instance contains the given node; otherwise false
     */
    public boolean containsNode(final String node) {
        final String[] nodes = getNodes();
        for (final String s : nodes) {
            if (s.equals(node)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the Connector connecting the given node from behind
     * 
     * @param node  the node to get the Connector for
     * @return      the Connector connecting the given node from behind
     */
    public Connector getPreConnector(final String node) {
        for (final Connection connection : connections) {
            if (connection.getSuperConcept().equals(node)) {
                return connection.getConnector();
            }
        }
        return null;
    }
    
    /**
     * Gets the first sub-concept from this Path instance
     * 
     * @return  the first sub-concept from this Path instance
     */
    public String getFirstSubConcept() {
        return getFirst().getSubConcept();
    }
    
    /**
     * Gets the first super-concept from this Path instance
     * 
     * @return  the first super-concept from this Path instance
     */
    public String getFirstSuperConcept() {
        return getFirst().getSuperConcept();
    }
    
    /**
     * Gets the last sub-concept from this Path instance
     * 
     * @return  the last sub-concept from this Path instance
     */
    public String getLastSubConcept() {
        return getLast().getSubConcept();
    }
    
    /**
     * Gets the last super-concept from this Path instance
     * 
     * @return  the last super-concept from this Path instance
     */
    public String getLastSuperConcept() {
        return getLast().getSuperConcept();
    }
    
    /**
     * Gets the last Connector from this Path instance
     * 
     * @return  the last Connector from this Path instance
     */
    public Connector getLastConnector() {
        return getLast().getConnector();
    }
    
    /**
     * Removes the first Connection from this Path instance
     */
    public void removeFirst() {
        connections.removeFirst();
    }
    
    /**
     * Removes the last Connection from this Path instance
     */
    public void removeLast() {
        connections.removeLast();
    }
    
    /**
     * Gets the first Connection in this Path instance
     * 
     * @return  the first Connection in this Path instance 
     */
    private Connection getFirst() {
        return connections.get(0);
    }
    
    /**
     * Gets the last Connection in this Path instance
     * 
     * @return  the last Connection in this Path instance
     */
    private Connection getLast() {
        return connections.get(connections.size() - 1);
    }
    
    /**
     * Gets the index of the given node within this Path instance
     * 
     * @param node  the node to search for
     * @return      the index of the given node
     */
    public int indexOf(final String node) {
        final String[] nodes = getNodes();
        for (int i = 0; i < nodes.length; i ++) {
            if (nodes[i].equals(node)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Generates a new Path while excluding up until the given amount of nodes
     * 
     * @param beginIndices  the amount of nodes to exclude from the beginning
     * @return              a new Path while excluding up until the given amount of nodes
     */
    public Path trim(final int beginIndices) {
        final Path copy = copy();
        for (int i = 0; i < beginIndices; i ++) {
            copy.removeFirst();
        }
        return copy;
    }
    
    /**
     * Generates a new Path while excluding nodes from the end up until the given node is found
     * 
     * @param stopNode  the node at which we stop removing
     * @return          a new Path with excluded nodes up until the given node
     */
    public Path postTrim(final String stopNode) {
        final Path copy = copy();
        final int size = size();
        for (int i = size - 1; i >= 0; i --) {
            if (copy.getLastSuperConcept().equals(stopNode)) {
                break;
            }
            copy.removeLast();
        }
        return copy;
    }
    
    /**
     * Generates a new Path instance consisting of the same Connections as this Path instance
     * 
     * @return  a new Path instance consisting of the same Connections
     */
    public Path copy() {
        return new Path(connections.toArray(new Connection[connections.size()]));
    }
    
    /**
     * Checks whether the given Path is a sub-path of this Path instance
     * 
     * @param sub   the Path to check
     * @return      true if the given Path is a sub-path; otherwise false
     */
    public boolean isSubPath(final Path sub) {
        final String[] subNodes = sub.getNodes();
        final String[] origNodes = getNodes();
        if (subNodes.length > 0) {
            int initialIndex = indexOf(subNodes[0]);
            if (initialIndex != -1) {
                for (int i = 0; i < subNodes.length; i ++) {
                    if (!origNodes[initialIndex + i].equals(subNodes[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Converts this Path instance to a readable String
     * 
     * @return  a readable String explaining this Path instance
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        final int size = connections.size();
        
        if (size > 0) {
            final Connection first = connections.get(0);
            sb.append(first.getSubConcept());
            sb.append(" ");
            sb.append(first.getConnector().getName());
            sb.append(" ");
            sb.append(first.getSuperConcept());
            
            if (size > 1) {
                for (int i = 1; i < size; i ++) {
                    sb.append(" ");
                    
                    final Connection next = connections.get(i);
                    
                    sb.append(next.getConnector().getName());
                    sb.append(" ");
                    sb.append(next.getSuperConcept());
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * Checks whether this Path instance is equivalent to the given Object
     * 
     * @param other the Object to compare to
     * @return      true if this Path instance is equivalent to the given Object; otherwise false 
     */
    @Override
    public boolean equals(final Object other) {
        final Path otherPath = (Path) other;
        if (otherPath != null) {
            final int size = size();
            final int otherSize = otherPath.size();
            if (size == otherSize) {
                final LinkedList<Connection> otherConnections = otherPath.getConnections();
                for (int i = 0; i < size; i ++) {
                    if (!otherConnections.get(i).equals(connections.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}