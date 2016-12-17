package q8;

import java.util.Scanner;

/**
 *
 * @author Daniel Mizzi
 */
public class Q8 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
	System.out.println("Enter 1 if you wish to compute sine; 2 to compute cosine; anything else will result in termination of the program.");
	final String input = scanner.nextLine();
	try {
	    int i = Integer.parseInt(input);
	    if (i > 0 && i < 3) {
		System.out.println("Computing " + ((i == 1) ? "sine" : "cosine") + "...");
		System.out.println("Input the degrees value:");
		final double degrees = scanner.nextDouble();
		System.out.println("Input the number of series terms:");
		final int terms = scanner.nextInt();
		double result = i == 1 ? computeSine(degrees, terms) : computeCosine(degrees, terms);
		System.out.println("Result: " + result);
	    }
	} catch (final Exception e) {}
    }
    
    private static double computeSine(double x, int terms) {
	double rad = x * 1./180. * Math.PI;
	double sum = rad;
	for (int i = 1; i < terms; i ++) {
	    sum += ((Math.pow(-1, i) * Math.pow(rad, (2 * i) + 1)) / (factorial((2 * i) + 1)));
	}
	return sum;
    }
    
    private static double computeCosine(double x, int terms) {
	double rad = x * 1./180. * Math.PI;
	double sum = 1;
	for (int i = 1; i < terms; i ++) {
	    sum += ((Math.pow(-1, i) * Math.pow(rad, 2 * i)) / (factorial(2 * i)));
	}
	return sum;	
    }
    
    private static long factorial(long n) {
	if (n == 0) {
	    return 1;
	}
	return n * factorial(n - 1);
    }
}