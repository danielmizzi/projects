package q7;

import java.util.Scanner;

/**
 *
 * @author Daniel Mizzi
 */
public class Q7 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
	System.out.println("Enter an array of numbers (separated by commas):");
	final String[] input = scanner.nextLine().replaceAll(" ", "").split(",");
	final int[] array = new int[input.length];
	for (int i = 0; i < array.length; i ++) {
	    array[i] = Integer.parseInt(input[i]);
	}
	System.out.println("Largest number: " + getLargestNum(array));
    }
    
    private static int getLargestNum(int[] numbers) {
	return getLargestNum(0, Integer.MIN_VALUE, numbers);
    }
    
    private static int getLargestNum(int n, int curr, int[] numbers) {
	if (n >= numbers.length) {
	    return curr;
	}
	if (numbers[n] > curr) {
	    curr = numbers[n];
	}
	return getLargestNum(n + 1, curr, numbers);
    }
}