/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiplist;

/**
 *
 * @author Daniel
 */
public class Node {
    
    private final int level;
    private final int value;
    
    private int index;
    
    private Node next = null;
    private Node previous = null;
    private Node upper = null;
    private Node lower = null;
    
    Node(final int level, final int value) {
	this.level = level;
	this.value = value;
    }
    
    public int getLevel() {
	return level;
    }
    
    public int getValue() {
	return value;
    }
    
    public void setIndex(final int index) {
	this.index = index;
    }
    
    public int getIndex() {
	return index;
    }
    
    public void setUpperNode(final Node upper) {
	this.upper = upper;
    }
    
    public Node getUpperNode() {
	return upper;
    }
    
    public void setLowerNode(final Node lower) {
	this.lower = lower;
    }
    
    public Node getLowerNode() {
	return lower;
    }
    
    public void setNextNode(final Node next) {
	this.next = next;
    }
    
    public Node getNextNode() {
	return next;
    }
    
    public void setPreviousNode(final Node previous) {
	this.previous = previous;
    }
    
    public Node getPreviousNode() {
	return previous;
    }
    
    public void print() {
        System.out.println("Node info... value: " + getValue() + ", index: " + getIndex() + ", level: " + getLevel());
    }
}
