/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skiplist;

import java.util.Random;

public class SkipList {
    
    private final Random random;
    
    private final Node lowestHead;
    private final Node lowestTail;
    
    private Node highestHead;
    private Node highestTail;
    
    private int count = 0;
    
    SkipList() { 
	this.random = new Random();
	
	this.lowestHead = new Node(1, Integer.MIN_VALUE);
	this.lowestTail = new Node(1, Integer.MAX_VALUE);
	
	this.lowestHead.setNextNode(lowestTail);
	this.lowestHead.setIndex(-1);
	
	this.lowestTail.setPreviousNode(lowestHead);
	
	this.highestHead = lowestHead;
	this.highestTail = lowestTail;
    }
    
    public void insert(final int value) {
	boolean generate = true;
	int currBase = 2;
	
	Node currentHead = lowestHead;
	Node currentTail = lowestTail;
	Node previouslyInserted = null; 
	
	Node baseInserted = null;
	
	do {
	    
	    Node next = currentHead;
	    
	    while ((next = next.getNextNode()) != null) {
		
		if (next.getValue() > value) {
		    // insert before
		    final Node toInsert = new Node(currentHead.getLevel(), value);
		    
		    if (baseInserted == null) {
			baseInserted = toInsert;
		    }
		    
		    final Node previous = next.getPreviousNode();
		    
		    previous.setNextNode(toInsert);
		    toInsert.setNextNode(next);
		    toInsert.setPreviousNode(previous);
		    next.setPreviousNode(toInsert);
		    
		    if (toInsert.getLevel() == 1) {
			toInsert.setIndex(previous.getIndex() + 1);
		    }
		    
		    if (previouslyInserted != null) {
			previouslyInserted.setUpperNode(toInsert);
			toInsert.setLowerNode(previouslyInserted);
			toInsert.setIndex(previouslyInserted.getIndex());
		    }
		    previouslyInserted = toInsert;
		    break;
		}
		// when inserting, update previous node, next node pointers, and upper node pointers 
	    }
	    
	    

	    if (random.nextInt(101) <= (100 / currBase)) {
		currBase *= 2;

		Node nextHead = null;
		Node nextTail = null;

		if (currentHead.getUpperNode() == null) { // need to add new level
		    nextHead = new Node(currentHead.getLevel() + 1, Integer.MIN_VALUE);
                    nextHead.setIndex(-1);
		    currentHead.setUpperNode(nextHead);
		    nextHead.setLowerNode(currentHead);
		}
		if (currentTail.getUpperNode() == null) { // need to add new level
		    nextTail = new Node(currentTail.getLevel() + 1, Integer.MAX_VALUE);
		    currentTail.setUpperNode(nextTail);
		    nextTail.setLowerNode(currentTail);
		}

		if (nextHead != null && nextTail != null) {
		    nextHead.setNextNode(nextTail);
		    nextTail.setPreviousNode(nextHead);
		    highestHead = nextHead;
		    highestTail = nextTail;
		}

		currentHead = currentHead.getUpperNode();
		currentTail = currentTail.getUpperNode();
	    } else {
		generate = false;
	    }
	    
	} while (generate);
	
	if (baseInserted != null) {
	    Node newNext = baseInserted.getNextNode();
	    while (newNext != null && newNext != currentTail) {
	        newNext.setIndex(newNext.getIndex() + 1);
		
		Node newUpper = newNext.getUpperNode();
		while (newUpper != null) {
		    newUpper.setIndex(newNext.getIndex());
		    newUpper = newUpper.getUpperNode();
		}
		
		newNext = newNext.getNextNode();
	    }
	}
        count ++;
    }
    
    public int find(final int value) {
	Node currentHead = lowestHead;
	
	while (currentHead != null) {
	    Node next = currentHead.getUpperNode();
	    if (next != null) {
		currentHead = next;
	    } else {
		break;
	    }
	}
	
	// now at the top
	
	Node next = currentHead;
	while (next != null && (next = next.getNextNode()) != null) {
	    if (next.getValue() > value) {
		if (next.getLevel() == 1) {
		    break;
		}
		next = next.getPreviousNode().getLowerNode();
	    } else if (next.getValue() == value) {
		return next.getIndex();
	    }
	}
	return -1;
    }
    
    public boolean deleteByValue(final int value) {
	final int index = find(value);
	if (index == -1) {
	    return false;
	}
	return deleteByIndex(index);
    }
    
    public boolean deleteByIndex(final int index) {
	Node toRemove = findNode(index);
	if (toRemove == null) {
	    return false;
	}
        
	// remove at levels below
	Node lower = toRemove;
	while (lower != null) {
	    Node previous = lower.getPreviousNode();
	    Node next = lower.getNextNode();

	    previous.setNextNode(next);
	    next.setPreviousNode(previous);

	    while (next.getValue() != Integer.MAX_VALUE) {
		next.setIndex(next.getIndex() - 1);
		next = next.getNextNode();
	    }

	    if (previous.getValue() == Integer.MIN_VALUE && next.getValue() == Integer.MAX_VALUE) { // remove entire level
                final Node lowerPrevious = previous.getLowerNode();
                final Node lowerNext = next.getLowerNode();
                
                if (lowerPrevious != null) {
                    lowerPrevious.setUpperNode(null);
                }
                
                if (lowerNext != null) {
                    lowerNext.setUpperNode(null);
                }
	    }
	    lower = lower.getLowerNode();
	}
	count --;
	return true;
    }
    
    public Node findNode(final int index) {
	Node currentHead = highestHead;
	
	Node next = currentHead;
	while (next != null) {
	    if (next.getIndex() > index) {
		if (next.getLevel() == 1) {
		    Node prev = next.getPreviousNode();
		    while (prev != null) {
			if (prev.getIndex() == index) {
			    return prev;
			}
		    }
		    return null;    
		} else {
		    next = next.getPreviousNode();
		    if (next != null) {
			next = next.getLowerNode();
		    }
		    continue;
		}
	    } else if (next.getIndex() == index) {
		return next;
	    }
	    next = next.getNextNode();
	}
	return null;
    }
    
    // a variable count is kept instead of looping through base nodes
    public int getCount() {
	return count;
    }
    
    public int findSteps(final int value) {
	Node currentHead = lowestHead;
	int steps = 0;
	
	while (currentHead != null) {
	    Node next = currentHead.getUpperNode();
	    if (next != null) {
		currentHead = next;
	    } else {
		break;
	    }
	}
	
	// now at the top
	
	Node next = currentHead;
	while (next != null && (next = next.getNextNode()) != null) {
	    steps++;
	    if (next.getValue() > value) {
		if (next.getLevel() == 1) {
		    break;
		}
		next = next.getPreviousNode().getLowerNode();
	    } else if (next.getValue() == value) {
		return steps;
	    }
	}
	return -1;
    }
    
    public int getHeight() {
	Node currentHead = lowestHead;
	int height = 1;
	
	while ((currentHead = currentHead.getUpperNode()) != null) {
	    height++;
	}
	return height;
    }
    
    public void empty() {
	Node currentHead = lowestHead;
	Node currentTail = lowestTail;
	
	while (currentHead != null && currentTail != null) {
	    currentHead.setNextNode(currentTail);
	    currentTail.setPreviousNode(currentHead);
	    
	    currentHead = currentHead.getUpperNode();
	    currentTail = currentTail.getUpperNode();
	}
	
	lowestHead.setUpperNode(null);
	lowestTail.setUpperNode(null);
	
	count = 0;
    }
    
    public void print() {
	Node currentHead = lowestHead;
	Node currentTail = lowestTail;
	
	String total = "";
	
	while (currentHead != null) {
	    Node next = currentHead;
	    String current = "";
	    
	    current += ("Level " + currentHead.getLevel() + ": -∞\t<->\t");
	    
	    while ((next = next.getNextNode()) != null && next != currentTail) {
		Node future = next.getNextNode();
		int tabs = 1;
		if (future != null && future != currentTail) {
		    tabs = future.getIndex() - next.getIndex();
		}
		current += (next.getValue() + " [" + next.getIndex() + "]");
		for (int i = 0; i < tabs; i ++) {
		    current += "\t";
		}
		current += "<->\t";
	    }

	    current += "+∞";
	    
	    currentHead = currentHead.getUpperNode();
	    currentTail = currentTail.getUpperNode();
	    
	    total = current + "\n" + total;
	}
	
	System.out.print(total + "\n");
    }
}