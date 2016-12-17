/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree;

import java.awt.EventQueue;
import redblacktree.console.Console;
import redblacktree.type.Color;

/**
 *
 * @author Daniel
 */
public class Main {
    
    private static Console console = null;
    private static boolean running = true;
    
    private static boolean waiting = false;
    
    private static RedBlackTree tree = null;
 
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
                        if (line.matches("^initialise\\((.*)\\)$")) {
                            if (!line.matches("^initialise\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (tree != null) {
                                    System.out.println("RBT already initialised, please use empty to unload the current tree!\n");
                                } else {
                                    final int value = Integer.parseInt(line.substring(11, line.length() - 1));
                                    tree = new RedBlackTree(value, Color.BLACK);
                                    
                                    System.out.println("RBT initialised...\n");
                                    
                                    tree.print();
                                    
                                    System.out.println();
                                }
                            }
                        } else if (line.matches("^insert\\((.*)\\)$")) {
                            if (!line.matches("^insert\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!");
                            } else {
                                if (tree == null) {
                                    System.out.println("Please initialise a tree before inserting!");
                                } else {
                                    System.out.println("Previous RBT: ");
                                    
                                    tree.print();
                                    
                                    System.out.println();
                                    
                                    final int value = Integer.parseInt(line.substring(7, line.length() - 1));
                                    
                                    System.out.println("Inserting: " + value + "...\n");
                                    
                                    tree.insert(value);
                                    
                                    RedBlackTree newParent = tree;
                                    while (newParent != null) {
                                        RedBlackTree next = newParent.getParent();
                                        if (next != null) {
                                            newParent = next;
                                        } else {
                                            break;
                                        }
                                    }
                                    
                                    tree = newParent;
                                    
                                    tree.print();
                                    
                                    System.out.println();
                                }
                            }
                        } else if (line.matches("^find\\((.*)\\)$")) {
                            if (!line.matches("^find\\(\\d+\\)$")) {
                                System.out.println("Please enter a proper parameter!\n");
                            } else {
                                if (tree == null) {
                                    System.out.println("Please initialise a tree before searching!\n");
                                } else {
                                    final int value = Integer.parseInt(line.substring(5, line.length() - 1));
                                    
                                    boolean found = tree.find(value);
                                    
                                    System.out.println("Searching for value: " + value + "... " + (found ? "found!" : "not found!") + "\n");
                                    
                                }
                            }
                        } else if (line.equals("count()")) {
                            if (tree == null) {
                                System.out.println("Please initialise a tree before counting!\n");
                            } else {
                                System.out.println("RBT count: " + tree.count() + "\n");
                            }
                        } else if (line.equals("height()")) {
                            if (tree == null) {
                                System.out.println("Please initialise a tree before getting the height!\n");
                            } else {
                                System.out.println("RBT height: " + tree.height() + "\n");
                            }
                        } else if (line.equals("empty()")) {
                            if (tree == null) {
                                System.out.println("The RBT is already empty!\n");
                            } else {
                                tree = null;
                                System.out.println("The RBT has been emptied!\n");
                            }
                        } else if (line.equals("print()")) {
                            if (tree == null) {
                                System.out.println("EMPTY TREE\n");
                            } else {
                                tree.print();
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
