package q9;

/**
 *
 * @author Daniel Mizzi
 */
import java.util.Scanner;

public class Q9 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
	System.out.println("Enter a string to check whether it is palindrome:");
	final String input = scanner.nextLine();
	System.out.println("Is palindrome: " + isPalindrome(input));
    }
    
    private static boolean isPalindrome(String string) {
	char[] curr = string.replaceAll("[^a-zA-Z]", "").toLowerCase().toCharArray();
	for (int i = 0; i < curr.length / 2; i ++) {
	    if (curr[i] != curr[curr.length - 1 - i]) {
		return false;
	    }
	}
	return true;
    }
}