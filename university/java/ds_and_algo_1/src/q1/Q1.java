/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package q1;

import java.util.Scanner;

public class Q1 {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main (String[] args) {
	System.out.println("Please input a number to convert to Roman notation: ");
	final int input = scanner.nextInt();
	System.out.println("Roman notation: " + toRomanNumeral(input));
    }
    
    private static String toRomanNumeral(int num) {
	if (num < 1 || num > 1024) {
	    System.out.println("[ERROR] Please input a number between 1 and 1024 (both inclusive).");
	    return "n/a";
	}
	final Object[][] numerals = {{"M", 1000}, {"CM", 900}, {"D", 500}, {"CD", 400}, {"C", 100},
	    {"XC", 90}, {"L", 50}, {"XL", 40}, {"X", 10}, {"IX", 9}, {"V", 5}, {"IV", 4}, {"I", 1}};
	final StringBuilder sb = new StringBuilder();
	while (num > 0) {
	    for (int i = 0; i < numerals.length; i ++) {
		if ((int) numerals[i][1] <= num) {
		    sb.append(numerals[i][0]);
		    num -= (int) numerals[i][1];
		    break;
		}
	    }
	}
	return sb.toString();
    }
}