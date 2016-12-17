package q2;

import java.util.ArrayList;
import java.util.Scanner;

public class Q2 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
	System.out.println("Enter a string to convert to RPN: ");
	final String expression = scanner.nextLine();
	System.out.println("RPN expression: " + toRPN(expression));
    }
    
    private static int toRPN(String string) {
	final Q2.Stack stack = new Q2.Stack();
	final ArrayList<String> tokens = getTokens(string);
	stack.print();
	int result = -1;
	while (tokens.size() > 0) {
	    final String top = tokens.get(0);
	    if (!isOperator(top)) {
		stack.push(top);
	    } else {
		int secondNum = Integer.parseInt(stack.pop());
		int firstNum = Integer.parseInt(stack.pop());
		switch (top) {
		    case "+":
			result = firstNum + secondNum;
		    break;
		    case "-":
			result = firstNum - secondNum;
		    break;
		    case "x":
			result = firstNum * secondNum;
		    break;
		    case "/":
			result = firstNum / secondNum;
		    break;
		    default:
		    return -1;
		}
		stack.push(Integer.toString(result));
	    }
	    tokens.remove(0);
	    stack.print();
	}
	if(stack.getSize() > 1) {
	    System.out.println("[ERROR] Stack result not empty ~ possibly incorrect input expression");
	}
	return result;
    }    
    
    private static ArrayList<String> getTokens(String string) {
	final ArrayList<String> strings = new ArrayList<>();
	final StringBuilder sb = new StringBuilder();
	for (final char c : string.toCharArray()) {
	    if (!(c == '+' || c == '/' || c == 'x' || c == '-' || c == ' ')) { // keep adding to value if it's a number
		sb.append(c);
	    } else { // character is an operator
		if (sb.length() > 0) {
		    strings.add(sb.toString()); // flush current number to list
		}
		if (c != ' ') {
		    strings.add(Character.toString(c)); // add operator to tokens 
		}
		sb.delete(0, sb.length()); // flush builder
	    }
	}
	if (sb.length() > 0) {
	    strings.add(sb.toString());
	}
	return strings;
    }
    
    private static boolean isOperator(String string) {
	return string.equals("+") || string.equals("/") || string.equals("x") || string.equals("-");
    }
    
    private static class Stack {
	
	private final ArrayList<String> stack = new ArrayList<>();
	private int cycles = 1;
	
	Stack() {}
	
	public void push(final String string) {
	    stack.add(0, string);
	}
	
	public boolean isEmpty() {
	    return stack.isEmpty();
	}
	
	public int getSize() {
	    return stack.size();
	}
	
	public String pop() {
	    return stack.remove(0);
	}
	
	public void print() {
	    System.out.print("[STACK : cycle " + cycles + "] [top (left) to bottom (right)]: ");
	    if (stack.isEmpty()) {
		System.out.print("empty");
	    } else {
		for (int i = 0; i < stack.size(); i++) {
		    System.out.print(stack.get(i) + (i >= (stack.size() - 1) ? "" : ", "));
		}
	    }
	    System.out.println();
	    cycles++;
	}
    }
}