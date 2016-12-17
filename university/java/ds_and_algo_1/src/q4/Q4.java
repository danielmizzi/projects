package q4;

/**
 *
 * @author Daniel Mizzi
 */
import java.util.Random;

public class Q4 {

    public static void main(String[] args) {
	shellSort();
    }
    
    private static void shellSort() {
	final int[] array = new int[7];
	final Random r = new Random();
	System.out.println("Unsorted array:");
	for (int i = 0; i < array.length; i++) {
	    array[i] = r.nextInt();
	    System.out.println(array[i]);
	}
	System.out.println();
	boolean flag;
	int j = array.length / 2;
	do {
	    flag = false;
	    for (int i = 0; i < array.length - j; i++) {
		if (array[i] > array[i + j]) {
		    int temp = array[i];
		    array[i] = array[i + j];
		    array[i + j] = temp;
		    flag = true;
		}
	    }
	    if (j > 1) {
		j /= 2;
		flag = true;
	    }
	} while (flag || j != 1);
	System.out.println("Sorted array:");
	for (int i = 0; i < array.length; i ++) {
	    System.out.println(array[i]);
	}
    }
}