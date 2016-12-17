package ics2207.optimisers.solution;

import static ics2207.optimisers.solution.Algorithm.CREEPING_RANDOM_SEARCH;

public class Solution {
    
    private final Algorithm algorithm;
    
    private final long timeTaken;
    private final int iterations;
    private final double fitnessAchieved;
    
    public Solution(final Algorithm algorithm, final long timeTaken, final int iterations, final double fitnessAchieved) {
	this.algorithm = algorithm;
	this.timeTaken = timeTaken;
	this.iterations = iterations;
	this.fitnessAchieved = fitnessAchieved;
    }
    
    public Algorithm getAlgorithm() {
	return algorithm;
    }
    
    public long getTimeTaken() {
	return timeTaken;
    }
    
    public int getIterations() {
	return iterations;
    }
    
    public double getFitnessAchieved() {
	return fitnessAchieved;
    }
    
    @Override
    public String toString() {
	return "[" + algorithm + "] " + (algorithm != CREEPING_RANDOM_SEARCH ? "\t" : "") + "\ttime: " + timeTaken + "ms \t iterations: " + iterations + (iterations < 1000 ? "\t" : "") + "\tfitness achieved: " + fitnessAchieved + "%";
    }
}