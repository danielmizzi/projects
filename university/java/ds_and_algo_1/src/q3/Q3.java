package q3;

import java.util.Scanner;

/**
 *
 * @author Daniel Mizzi
 */
public class Q3 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
	System.out.println("Input a number to check whether it is prime: ");
	final int num = scanner.nextInt();
	System.out.println("Prime: " + isPrime(num));
    }
    
    private static boolean isPrime(int num) {
	int[] array = new int[num + 1];
	for (int i = 0; i < array.length; i ++) {
	    array[i] = i;
	}
	for (int i = 2; i < array.length; i++) {
	    if (array[i] == -1) {
		continue;
	    }
	    for (int o = 2; (o * i) < array.length; o++) {
		array[o * i] = -1;
	    }
	}
	for (final int i : array) {
	    if (i == num) {
		return true;
	    }
	}
	return false;
    }
}