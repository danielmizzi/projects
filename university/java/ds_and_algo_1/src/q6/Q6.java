package q6;

/**
 *
 * @author Daniel Mizzi
 */
import java.util.Random;

public class Q6 {
    
    public static void main(String[] args) {
	multiplyMatrices();
    }
    
    private static double[][] multiplyMatrices() {
	final Random r = new Random();
	double[][] first = new double[16][16];
	double[][] second = new double[16][16];
	for (int i = 0; i < 16; i ++) {
	    for (int j = 0; j < 16; j ++) {
		first[i][j] = r.nextDouble();
		second[i][j] = r.nextDouble();
	    }
	}
	System.out.println("PRINTING FIRST RANDOMISED MATRIX:");
	for (int i = 0; i < 16; i ++) {
	    for (int j = 0; j < 16; j ++) {
		System.out.printf("%.2f \t", first[i][j]);
	    }
	    System.out.println();
	}
	System.out.println("\nPRINTING SECOND RANDOMISED MATRIX:");
	for (int i = 0; i < 16; i ++) {
	    for (int j = 0; j < 16; j ++) {
		System.out.printf("%.2f \t", second[i][j]);
	    }
	    System.out.println();
	}
	System.out.println("\nPRINTING RESULT MATRIX (multiplication):");
	double[][] newMatrix = new double[16][16];
	for (int i = 0; i < 16; i ++) {
	    for (int j = 0; j < 16; j ++) {
		for (int k = 0; k < 16; k ++) {
		    newMatrix[i][j] += first[i][k] * second[k][j];
		}
		System.out.printf("%.2f \t", newMatrix[i][j]);
	    }
	    System.out.println();
	}
	return newMatrix;
    }
}