package ics2207.optimisers;

import java.util.ArrayList;

public class Calculations {
    
    public static double evaluateTotalFitness(final int size, final int[][] positions) {
	double currentFitness = 0;
	
	for (final int[] start : positions) {
	    currentFitness += evaluateFitness(size, start, positions);
	}
	
	return currentFitness / (double) positions.length;
    }
    
    public static double evaluateFitness(final int size, final int[] start, final String population) {
	return evaluateFitness(size, start, convertPopulationToArray(population));
    }
    
    // fitness needs to be a percentage
    // so first get collisions / maximum collisions
    // then revert it
    // so if u have 2 / 64 collisions
    // we do (1 - (2 / 64)) * 100
    public static double evaluateFitness(final int size, final int[] start, final int[][] positions) {
	int collisions = 0;
	
	for (int y = start[1] - 1; y >= 0; y --) { // left
	    if (contains(positions, new int[]{start[0], y})) {
		collisions ++;
	    }
	} 
	
	for (int y = start[1] + 1; y < size; y ++) { // right
	    if (contains(positions, new int[]{start[0], y})) {
		collisions ++;
	    }
	}
	
	for (int x = start[0] + 1; x < size; x ++) { // down
	    if (contains(positions, new int[]{x, start[1]})) {
		collisions ++;
	    }
	}
	
	for (int x = start[0] - 1; x >= 0; x --) { // up
	    if (contains(positions, new int[]{x, start[1]})) {
		collisions ++;
	    }
	}
	
	for (int x = start[0] - 1, y = start[1] + 1; x >= 0 && y >= 0 && x < size && y < size; x--, y++) { // go right-up
	    if (contains(positions, new int[]{x, y})) {
		collisions ++;
	    }
	}
	
	for (int x = start[0] + 1, y = start[1] + 1; x >= 0 && y >= 0 && x < size && y < size; x++, y++) { // go right-down
	    if (contains(positions, new int[]{x, y})) {
		collisions ++;
	    }
	}
	
	for (int x = start[0] - 1, y = start[1] - 1; x >= 0 && y >= 0 && x < size && y < size; x--, y--) { // go left-up
	    if (contains(positions, new int[]{x, y})) {
		collisions ++;
	    }
	}
	
	for (int x = start[0] + 1, y = start[1] - 1; x >= 0 && y >= 0 && x < size && y < size; x++, y--) { // go left-down
	    if (contains(positions, new int[]{x, y})) {
		collisions ++;
	    }
	}
	return (1 - ((double) collisions / (double) (size - 1))) * 100;
    }  
    
    public static int[][] getMoveSet(final int size, final int[] start, final int[][] positions) {
	final ArrayList<Integer[]> moveSet = new ArrayList<>();
	
	for (int y = start[1] - 1; y >= 0; y --) { // left
	    if (!contains(positions, new int[]{start[0], y})) {
		moveSet.add(new Integer[]{start[0], y});
	    }
	} 
	
	for (int y = start[1] + 1; y < size; y ++) { // right
	    if (!contains(positions, new int[]{start[0], y})) {
		moveSet.add(new Integer[]{start[0], y});
	    }
	}
	
	for (int x = start[0] + 1; x < size; x ++) { // down
	    if (!contains(positions, new int[]{x, start[1]})) {
		moveSet.add(new Integer[]{x, start[1]});
	    }
	}
	
	for (int x = start[0] - 1; x >= 0; x --) { // up
	    if (!contains(positions, new int[]{x, start[1]})) {
		moveSet.add(new Integer[]{x, start[1]});
	    }
	}
	
	for (int x = start[0] - 1, y = start[1] + 1; x >= 0 && y >= 0 && x < size && y < size; x--, y++) { // go right-up
	    if (!contains(positions, new int[]{x, y})) {
		moveSet.add(new Integer[]{x, y});
	    }
	}
	
	for (int x = start[0] + 1, y = start[1] + 1; x >= 0 && y >= 0 && x < size && y < size; x++, y++) { // go right-down
	    if (!contains(positions, new int[]{x, y})) {
		moveSet.add(new Integer[]{x, y});
	    }
	}
	
	for (int x = start[0] - 1, y = start[1] - 1; x >= 0 && y >= 0 && x < size && y < size; x--, y--) { // go left-up
	    if (!contains(positions, new int[]{x, y})) {
		moveSet.add(new Integer[]{x, y});
	    }
	}
	
	for (int x = start[0] + 1, y = start[1] - 1; x >= 0 && y >= 0 && x < size && y < size; x++, y--) { // go left-down
	    if (!contains(positions, new int[]{x, y})) {
		moveSet.add(new Integer[]{x, y});
	    }
	}
	
	int[][] set = new int[moveSet.size()][1];
	
	for (int i = 0; i < moveSet.size(); i ++) {
	    set[i] = new int[]{moveSet.get(i)[0], moveSet.get(i)[1]};
	}
	return set;
    }
    
    public static double getAverageFitness(final ArrayList<Double> fitnesses) {
	double curr = 0;
	for (final double fitness : fitnesses) {
	    curr += fitness;
	}
	return curr / (double) fitnesses.size();
    }
    
    public static String convertArrayToPopulation(final int[][] array) {
	String population = "";
	for (final int[] pos : array) {
	    String string = Integer.toString(pos[1]);
	    if (pos[1] < 10) {
		string = "0" + string;
	    }
	    population = population.concat(string);
	}
	return population;
    }
    
    // 0, 2, 4, 6, 8, 10
    // 0, 1, 2, 3, 4, 5
    public static int[][] convertPopulationToArray(final String population) {
	final ArrayList<Integer[]> list = new ArrayList<>();
	for (int i = 0; i < population.length(); i += 2) {
	    final String toParse = Character.toString(population.toCharArray()[i]) + Character.toString(population.toCharArray()[i + 1]);
	    if (toParse.startsWith("0")) {
		list.add(new Integer[]{i == 0 ? 0 : i / 2, Integer.parseInt(Character.toString(toParse.toCharArray()[1]))});
	    } else {
		list.add(new Integer[]{i == 0 ? 0 : i / 2, Integer.parseInt(toParse)});
	    }
	}
	final int[][] queens = new int[list.size()][1];
	
	for (int i = 0; i < list.size(); i ++) {
	    queens[i] = new int[]{list.get(i)[0], list.get(i)[1]};
	}
	return queens;
    }
    
    public static boolean contains(final int[][] positions, final int[] sub) {
	for (final int[] pos : positions) {
	    if (pos[0] == sub[0] && pos[1] == sub[1]) {
		return true;
	    }
	}
	return false;
    }
}