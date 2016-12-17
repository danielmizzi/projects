/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiplist;

import skiplist.console.Console;

/**
 *
 * @author Daniel
 */
public class Main {
    
    private static Console console = null;
    private static boolean running = true;
    
    private static boolean waiting = false;
    
    private static SkipList skipList = null;
 
    public static void main(final String[] args) {
        long lastRun = System.currentTimeMillis();
        while (running) {
            if (console == null) {
                if (!waiting) {
                    console = new Console();
                    console.setVisible(true);
                    console.setAlwaysOnTop(false);
                    console.requestFocus();
                    console.setFocus();
                    waiting = true;
                }
            } else {
                if (console.disposed) {
                    break;
                }
                if (System.currentTimeMillis() - lastRun >= 100) {
                    final String line = console.getLine();
                    
                    if (line != null) {
                        if (line.equals("initialise()")) {
                            if (skipList != null) {
                                System.out.println("A skip list has already been initialised!\n");
                            } else {
                                skipList = new SkipList();
                                System.out.println("Initialised skip list!\n");
                            }
                        } else if (line.matches("^insert\\((.*)\\)$")) {
                            if (!line.matches("^insert\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!");
                            } else {
                                if (skipList == null) {
                                    System.out.println("Please initialise a skipList before inserting!\n");
                                } else {
                                    System.out.println("Previous Skip list: \n");
                                    
                                    skipList.print();
                                    
                                    final int value = Integer.parseInt(line.substring(7, line.length() - 1));
                                    
                                    System.out.println("Inserting: " + value + "...\n");
                                    
                                    skipList.insert(value);
                                   
                                    skipList.print();
                                    
                                }
                            }
                        } else if (line.matches("^find\\((.*)\\)$")) {
                            if (!line.matches("^find\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (skipList == null) {
                                    System.out.println("Please initialise a skip list before searching!\n");
                                } else {
                                    final int value = Integer.parseInt(line.substring(5, line.length() - 1));
                                    
                                    System.out.println("Searching for: " + value + "... index returned: " + skipList.find(value) + "\n");
                                }
                            }
                        } else if (line.matches("^deleteByValue\\((.*)\\)$")) {
                            if (!line.matches("^deleteByValue\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (skipList == null) {
                                    System.out.println("Please initialise a skip list before deleting!\n");
                                } else {
                                    System.out.println("Previous Skip list: \n");
                                    
                                    skipList.print();
                                    
                                    final int value = Integer.parseInt(line.substring(14, line.length() - 1));
                                    
                                    System.out.println("Deleting: " + value + "...\n");
                                    
                                    skipList.deleteByValue(value);
                                   
                                    skipList.print();
                                }
                            }
                        } else if (line.matches("^deleteByIndex\\((.*)\\)$")) {
                            if (!line.matches("^deleteByIndex\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (skipList == null) {
                                    System.out.println("Please initialise a skip list before deleting!\n");
                                } else {
                                    System.out.println("Previous Skip list: \n");
                                    
                                    skipList.print();
                                    
                                    final int value = Integer.parseInt(line.substring(14, line.length() - 1));
                                    
                                    System.out.println("Deleting index: " + value + "...\n");
                                    
                                    skipList.deleteByIndex(value);
                                   
                                    skipList.print();
                                }
                            }
                        } else if (line.matches("^findNode\\((.*)\\)$")) {
                            if (!line.matches("^findNode\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (skipList == null) {
                                    System.out.println("Please initialise a skip list before searching!\n");
                                } else {
                                    final int value = Integer.parseInt(line.substring(9, line.length() - 1));
                                    
                                    System.out.println("Searching for node at index " + value + "...\n");
                                    
                                    final Node node = skipList.findNode(value);
                                    if (node != null) {
                                        System.out.println("Found node...");
                                        node.print();
                                        System.out.println();
                                    } else {
                                        System.out.println("Node was not found!\n");
                                    }
                                }
                            }
                        } else if (line.equals("count()")) {
                            if (skipList == null) {
                                System.out.println("Please initialise a skip list before counting!\n");
                            } else {
                                System.out.println("The amount of items in the skip list are: " + skipList.getCount() + "\n");
                            }
                        } else if (line.matches("^findSteps\\((.*)\\)$")) {
                            if (!line.matches("^findSteps\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (skipList == null) {
                                    System.out.println("Please initialise a skip list before searching!\n");
                                } else {
                                    final int value = Integer.parseInt(line.substring(10, line.length() - 1));
                                    
                                    System.out.println("Searching for the amount of steps to find " + value + "... result: " + skipList.findSteps(value) + "\n");
                                }
                            }
                        } else if (line.equals("height()")) {
                            if (skipList == null) {
                                System.out.println("Please initialise a skip list before getting the height!\n");
                            } else {
                                System.out.println("The skip list height is: " + skipList.getHeight() + "\n");
                            }
                        } else if (line.equals("empty()")) {
                            if (skipList == null) {
                                System.out.println("Please initialise a skip list before emptying!\n");
                            } else {
                                System.out.println("The skip list has been emptied!\n");
                                skipList.empty();
                            }
                        } else if (line.equals("print()")) {
                            if (skipList == null) {
                                System.out.println("Please initialise a skip list before printing!\n");
                            } else {
                                skipList.print();
                            }
                        } else if (line.equals("quit()")) {
                            running = false;
                        } else {
                            System.out.println("Unsupported command: " + line + "\n");
                        }
                    }
                    
                    lastRun = System.currentTimeMillis();
                }
            }
        }
        
        if (console != null) {
            console.dispose();
        }
        System.exit(0);
   }
    
}
