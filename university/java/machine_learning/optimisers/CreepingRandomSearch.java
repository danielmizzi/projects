package ics2207.optimisers;

import ics2207.board.Board;
import static ics2207.optimisers.solution.Algorithm.CREEPING_RANDOM_SEARCH;
import ics2207.optimisers.solution.Solution;
import java.util.Random;

public class CreepingRandomSearch {
    
    private static final Random random = new Random();
    
    // set iterations to 0 or below to loop until 100% fitness
    public Solution getSolution(final Board board, double fitnessPercentage, int iterations) {
	final long startTime = System.currentTimeMillis();
	int totalIterations = 0;
	
	boolean reachingFitness = iterations <= 0;
	double currentFitness = -1;
	
	int[][] initialPositions = board.getQueens();
	
	while (reachingFitness ? currentFitness < fitnessPercentage : iterations-- > 0) {
	    totalIterations++;
	    
	    final int[] randomQueen = initialPositions[random.nextInt(board.getSize())];
	    final double randomFitness = Calculations.evaluateTotalFitness(board.getSize(), initialPositions);
	    if (currentFitness == -1) {
		currentFitness = randomFitness;
	    }
	    
	    final int[][] moveSet = Calculations.getMoveSet(board.getSize(), new int[]{randomQueen[0], randomQueen[1]}, initialPositions);
	    
	    if (moveSet.length == 0) {
		break;
	    }
	    
	    final int[] randomMove = moveSet[random.nextInt(moveSet.length)];
	    
	    int[][] tempCopy = initialPositions;
	    for (final int[] i : tempCopy) {
		if (i[0] == randomQueen[0] && i[1] == randomQueen[1]) {
		    i[0] = randomMove[0];
		    i[1] = randomMove[1];
		    break;
		}
	    }
	    
	    final double newFitness = Calculations.evaluateTotalFitness(board.getSize(), tempCopy);
	    
	    if (newFitness > currentFitness) {
		initialPositions = tempCopy;
		currentFitness = newFitness;
	    }
	}
	return new Solution(CREEPING_RANDOM_SEARCH, System.currentTimeMillis() - startTime, totalIterations, currentFitness);
    }
    
}