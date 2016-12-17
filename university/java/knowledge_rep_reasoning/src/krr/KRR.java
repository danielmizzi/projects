package krr;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * @author Daniel Mizzi (491296M)
 */
public class KRR {

    /**
     * Stores the list of Connections obtained from the given file
     */
    private static final ArrayList<Connection> connectionsList = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     * 
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(getStorageDirectory());
        
        final String fileName = JOptionPane.showInputDialog("Enter file name (including file extension):");
        final File file = new File(getStorageDirectory() + fileName);
        
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "File not found!");
            return;
        }
        
        final ArrayList<String> input = readFile(file);
        final String[] split = input.toArray(new String[input.size()]);
        
        for (final String s : split) {
            System.out.println(s);
            final String[] splitSpace = s.split(" ");
            final String subConcept = splitSpace[0];
            final String superConcept = splitSpace[2];
            final String connector = splitSpace[1].toUpperCase();
            final Connection connection = new Connection(subConcept, superConcept, connector.equals("IS-A") ? Connector.IS_A : Connector.IS_NOT_A);
            connectionsList.add(connection);
        }
        
        final String query = JOptionPane.showInputDialog("Enter query:");
        
        final Path[] paths = getPaths(query);
        
        final Path shortest = getShortestPath(paths);
        final Path[] inferential = getInferentialPaths(paths);
        
        final StringBuilder sb = new StringBuilder();
        
        System.out.println("Query: " + query + "\n");
        sb.append("Query: ");
        sb.append(query);
        sb.append("\n\n");
        
        System.out.println("Paths:");
        sb.append("Paths:\n");
        if (paths.length == 0) {
            sb.append("- ");
            sb.append("NONE");
            sb.append("\n");
        }
        for (final Path p : paths) {
            System.out.println("- " + p);
            sb.append("- ");
            sb.append(p);
            sb.append("\n");
        }
        
        System.out.println("\nPreferred Path (Shortest Distance):");
        sb.append("\nPreferred Path (Shortest Distance):\n");
        
        System.out.println("- " + shortest);
        sb.append("- ");
        sb.append(shortest);
        sb.append("\n");
        
        System.out.println("\nPreferred Path (Inferential Distance) [set]:");
        sb.append("\nPreferred Path (Inferential Distance) [set]:\n");
        
        if (inferential.length == 0) {
            System.out.println("- NONE");
            sb.append("- NONE");
        } else {
            for (final Path p : inferential) {
                System.out.println("- " + p);
                sb.append("- ");
                sb.append(p);
                sb.append("\n");
            }
        }
        
        JOptionPane.showMessageDialog(null, sb);
        
    }
    
    /**
     * Gets the shortest Paths from the given Path array
     * 
     * @param paths the Path array to cycle through
     * @return      the shortest Path
     */
    private static Path getShortestPath(final Path[] paths) {
        int size = Integer.MAX_VALUE;
        Path curr = null;
        for (final Path p : paths) {
            int pathSize = p.size();
            if (pathSize < size) {
                size = pathSize;
                curr = p;
            }
        }
        return curr;
    }
    
    /**
     * Gets the set of inferential Paths from the given Path array
     * 
     * @param paths the Path array to cycle through
     * @return      the set of inferential Paths from the given Path array
     */
    private static Path[] getInferentialPaths(final Path[] paths) {
        ArrayList<Path> pathsList = new ArrayList<>();
        for (final Path p : paths) {
            if (!isRedundant(p, paths) && !isPreempted(p, paths)) {
                pathsList.add(p);
            }
        }
        return pathsList.toArray(new Path[pathsList.size()]);
    }
    
    /**
     * Gets the set of Paths with the given query from the previously obtained Connections
     * 
     * @param query the query to get the Paths with
     * @return      the set of Paths with the given query
     */
    private static Path[] getPaths(final String query) {
        final ArrayList<Path> toVisit = new ArrayList<>();
        final ArrayList<Path> closed = new ArrayList<>();
        
        final String[] split = query.split(" ");
        
        final String start = split[0];
        final String target = split[2];
        
        final Connection[] toVisitStart = getConnectionsWithSubConcept(start);
        
        for (final Connection c : toVisitStart) {
            toVisit.add(new Path(c));
        }
        
        while (!toVisit.isEmpty()) {
            final int index = toVisit.size() - 1;
            final Path last = toVisit.get(index); // get last path
            
            if (Connector.IS_NOT_A == last.getLastConnector() || last.getLastSuperConcept().equals(target)) { // path stops if last connector is IS_NOT_A
                if (last.getLastSuperConcept().equals(target)) {
                    closed.add(last);
                }
                toVisit.remove(index);
                continue;
            }
            
            final String lastSuperConcept = last.getLastSuperConcept();
            
            final Connection[] furtherConnections = getConnectionsWithSubConcept(lastSuperConcept);

            if (furtherConnections.length > 0) {
                if (furtherConnections.length > 1) {
                    for (int i = 1; i < furtherConnections.length; i ++) {
                        final Path copy = last.copy();
                        copy.addConnection(furtherConnections[i]);
                        toVisit.add(copy);
                    }
                }
                last.addConnection(furtherConnections[0]);
            } else {
                if (last.getLastSuperConcept().equals(target)) {
                    closed.add(last);
                }
                toVisit.remove(index);
            }
        }
        return closed.toArray(new Path[closed.size()]);
    }
    
    /**
     * Checks whether the given Path is redundant
     * 
     * @param path  the Path to check for redundancy
     * @param paths the Path array to compare to
     * @return      true if the given Path is redundant; otherwise false
     */
    private static boolean isRedundant(final Path path, final Path[] paths) {
        for (final Path p : paths) {
            if (p.equals(path)) {
                continue;
            }
            if (p.getLastSuperConcept().equals(path.getLastSuperConcept()) && p.getLastConnector() == path.getLastConnector() && path.size() < p.size()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks whether the given Path is preempted
     * 
     * @param path  the Path to check for preemption
     * @param paths the Path array to compare to
     * @return      true if the given Path is preempted; otherwise false
     */
    private static boolean isPreempted(final Path path, final Path[] paths) {
        // go through all nodes of path
        // find the node which branches out
        // if the node rebranches in in the same path with a different sign, and is shorter
        // then this path is preempted

        final String[] nodes = path.getNodes();
        for (int i = 1; i < nodes.length; i ++) {
            final Connection[] connections = getConnectionsWithSuperConcept(nodes[i]);
            if (connections.length > 1) {
                // get all other paths which pass from nodes[i]
                // find the first common sub concept
                
                final Path[] checkForPreemption = getPathsWithExcept(nodes[i], path, paths);
                
                String toCheck = null;
                
                outer: for (int o = i - 1; o >= 0; o --) {
                    for (final Path p : checkForPreemption) {
                        if (p.containsNode(nodes[o]) && getConnectionsWithSubConcept(nodes[o]).length > 1) {
                            toCheck = nodes[o];
                            break outer;
                        }
                    }
                }
                
                // get all the paths which are not sub paths so we check if they are pre-empting us
                final Path[] newQuery = getPaths(toCheck + " IS-A " + nodes[i]);
                Path subPath = null;
                ArrayList<Path> toCheckList = new ArrayList<>();
                
                if (newQuery.length > 0) {
                    for (final Path p : newQuery) {
                        if (path.isSubPath(p)) {
                            subPath = p;
                        } else {
                            toCheckList.add(p);
                        }
                    }
                    if (subPath != null) {
                        for (final Path p : toCheckList) {
                            if (p.getLastConnector() != subPath.getLastConnector() && p.size() < subPath.size()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Gets all the Paths with the given node, while excluding the given Path
     * 
     * @param node      the node to include in the Paths
     * @param exclude   the Path to exclude from the result
     * @param paths     the Paths in the network
     * @return          the Paths with the given node excluding the given Path
     */
    private static Path[] getPathsWithExcept(final String node, final Path exclude, final Path[] paths) {
        final ArrayList<Path> pathsList = new ArrayList<>();
        for (final Path p : paths) {
            if (!p.equals(exclude) && p.containsNode(node)) {
                pathsList.add(p);
            }
        }
        return pathsList.toArray(new Path[pathsList.size()]);
    }
    
    /**
     * Gets all the Connections with the given sub concept
     * 
     * @param subConcept    the sub concept to accept
     * @return              all the Connections with the given sub concept
     */
    private static Connection[] getConnectionsWithSubConcept(final String subConcept) {
        final ArrayList<Connection> connections = new ArrayList<>();
        
        connectionsList.stream().filter((connection) -> (connection.getSubConcept().equals(subConcept))).forEach((connection) -> {
            connections.add(connection);
        });
        return connections.toArray(new Connection[connections.size()]);
    }
    
    /**
     * Gets all the Connections with the given super concept
     * 
     * @param superConcept  the super concept to accept
     * @return              all the Connections with the given super concept
     */
    private static Connection[] getConnectionsWithSuperConcept(final String superConcept) {
        final ArrayList<Connection> connections = new ArrayList<>();
        
        connectionsList.stream().filter((connection) -> (connection.getSuperConcept().equals(superConcept))).forEach((connection) -> {
            connections.add(connection);
        });
        return connections.toArray(new Connection[connections.size()]);
    }
    
    /**
     * Gets the storage directory
     * 
     * @return  the storage directory
     */
    private static String getStorageDirectory() {
	return System.getProperty("user.dir") + "\\networks\\";
    }
    
    /**
     * Reads the given file into a list of Strings
     * 
     * @param file          the File object to read
     * @return              a list of Strings obtained from the given File
     * @throws Exception
     */
    private static ArrayList<String> readFile(final File file) throws Exception {
	final ArrayList<String> lines = new ArrayList<>();
	
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
	return lines;
    }
}