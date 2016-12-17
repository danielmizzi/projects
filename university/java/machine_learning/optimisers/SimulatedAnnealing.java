package ics2207.optimisers;

import ics2207.board.Board;
import static ics2207.optimisers.solution.Algorithm.SIMULATED_ANNEALING;
import ics2207.optimisers.solution.Solution;
import java.util.Random;

public class SimulatedAnnealing {
    
    private static final Random random = new Random();
    
    public Solution getSolution(final Board board, double startTemp, final double stoppingTemp, final double tempRate) {
	final long startTime = System.currentTimeMillis();
	int totalIterations = 0;
	
	int[][] initialPositions = board.getQueens();
	
	double currentFitness = -1;
	
	while (startTemp > stoppingTemp) {
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
	    
	    if (newFitness > randomFitness || acceptProposal(randomFitness, newFitness, startTemp)) { // apply move
		initialPositions = tempCopy;
		currentFitness = newFitness;
	    }
	    
	    startTemp *= tempRate;
	}
	return new Solution(SIMULATED_ANNEALING, System.currentTimeMillis() - startTime, totalIterations, currentFitness);
    }
    
    private boolean acceptProposal(final double current, final double proposal, final double temperature) {
	if (current < proposal) {
	    return true;
	}
	
	if (temperature == 0) {
	    return false;
	}
	return random.nextDouble() < Math.exp(-(current - proposal) / temperature);
    }
}