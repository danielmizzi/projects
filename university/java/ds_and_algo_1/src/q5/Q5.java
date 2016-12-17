package q5;

import java.util.Scanner;

/**
 *
 * @author Daniel Mizzi
 */
public class Q5 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
	System.out.println("Enter the number to approximate to (integer):");
	final int num = scanner.nextInt();
	System.out.println("Enter the number of iterations (integer):");
	final int iterations = scanner.nextInt();
	System.out.println("Enter the first guess (double):");
	final double guess = scanner.nextDouble();
	System.out.println("Newton Approximate: " + newtonApproximate(num, iterations, guess));
    }
    
    private  static double newtonApproximate(int num, int iterations, double guess) {
	for (int i = 0; i < iterations; i ++) {
	    guess = guess - ((Math.pow(guess, 2) - num) / (2 * guess));
	}
	return guess;
    }
}