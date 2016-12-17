/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ics2207;

import ics2207.board.Board;
import ics2207.console.Console;
import ics2207.optimisers.CreepingRandomSearch;
import ics2207.optimisers.GeneticAlgorithm;
import ics2207.optimisers.SimulatedAnnealing;
import ics2207.optimisers.solution.Solution;

import java.util.Random;

/**
 *
 * @author user
 */
public class Main {

    private static Console console = null;
    private static boolean running = true;
    
    private static boolean waiting = false;
    
    private static boolean executed = false;
    
    private static int size = -1;
    
    private static double startTemp = -1;
    private static double stoppingTemp = -1;
    private static double tempRate = -1;
    
    private static double fitnessPercentage = -1;
    private static int iterations = -1;
    
    private static double percentageStop = -1;
    private static double crossoverProbability = -1;
    private static double mutateProbability = -1;
    
    public static void main(String[] args) {
	long lastRun = System.currentTimeMillis();
	while (running) {
	    if (console == null) {
                if (!waiting) {
                    console = new Console();
                    console.setVisible(true);
                    console.setAlwaysOnTop(false);
                    console.requestFocus();
                    console.setFocus();
                    waiting = true;
                }
            } else {
                if (console.disposed) {
                    break;
                }
		if (System.currentTimeMillis() - lastRun >= 100) {
		    final String line = console.getLine();
		    
		    // default 20000, 50, 0.998

		    // default 50, 9000

		    // default 90, 50, 50
		    
		    if (line != null) {
			if (size == -1) { // expecting size
			    try {
				final int inputtedSize = Integer.parseInt(line);
				if (inputtedSize > 0 && inputtedSize <= 99) {
				    size = inputtedSize;
				    System.out.println(" " + size);
				    
				    System.out.println("\nPlease input the following Simulated Annealing parameters:");
				    
				    System.out.print("Please input the start temperature:");
				} else {
				    System.out.print("\nPlease input a value between 0 and 99...");
				}
			    } catch (Exception e) {
				System.out.println("\nError occured.. please try again");
			    }
			} else if (startTemp == -1) {
			    try {
				final double inputtedTemp = Double.parseDouble(line);
				startTemp = inputtedTemp;
				System.out.println(" " + startTemp);
				
				System.out.print("Please input the stopping temperature:");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (stoppingTemp == -1) {
			    try {
				final double inputtedTemp = Double.parseDouble(line);
				stoppingTemp = inputtedTemp;
				System.out.println(" " + stoppingTemp);
				
				System.out.print("Please input the temperature rate: ");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (tempRate == -1) {
			    try {
				final double inputtedTemp = Double.parseDouble(line);
				tempRate = inputtedTemp;
				System.out.println(" " + tempRate);
				
				System.out.println("\nPlease input the following Creeping Random Search parameters:");
				
				System.out.print("Please input the amount of iterations (input below -1 to instead use fitness percentage method): ");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (iterations == -1) {
			    try {
				final int inputtedIterations = Integer.parseInt(line);
				iterations = inputtedIterations;
				System.out.println(" " + iterations);
				
				if (iterations < -1) { // using fitness percentage method, ask for fitness
				    System.out.print("Please input the fitness percentage to stop at: ");
				} else {
				    fitnessPercentage = 0;
				    System.out.println("\nPlease input the following Genetic Algorithm parameters:");
				    System.out.print("Please input the percentage to stop at: ");
				}
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (fitnessPercentage == -1) {
			    try {
				final double inputtedPerc = Double.parseDouble(line);
				fitnessPercentage = inputtedPerc;
				System.out.println(" " + fitnessPercentage);
				
				System.out.println("\nPlease input the following Genetic Algorithm parameters:");
				System.out.print("Please input the percentage to stop at: ");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (percentageStop == -1) {
			    try {
				final double percStop = Double.parseDouble(line);
				percentageStop = percStop;
				System.out.println(" " + percentageStop);
				
				System.out.print("Please input the crossover probability: ");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (crossoverProbability == -1) {
			    try {
				final double crossover = Double.parseDouble(line);
				crossoverProbability = crossover;
				System.out.println(" " + crossoverProbability);
				
				System.out.print("Please input the mutate probability: ");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else if (mutateProbability == -1) {
			    try {
				final double mutateProb = Double.parseDouble(line);
				mutateProbability = mutateProb;
				System.out.println(" " + mutateProbability);
				
				System.out.println("Please press enter to get the results... ");
			    } catch (Exception e) { 
				System.out.print("\nError occured.. please try again");
			    }
			} else {
			    if (line.equals("quit")) {
				running = false;
				break;
			    }
			    if (!executed) {
				final Board board = generateRandomBoard(10);
				
				System.out.println();
				
				// default 20000, 50, 0.998
				final SimulatedAnnealing sa = new SimulatedAnnealing();
				final Solution saSolution = sa.getSolution(board, startTemp, stoppingTemp, tempRate);
				System.out.println(saSolution);

				// default 50, 9000
				final CreepingRandomSearch crs = new CreepingRandomSearch();
				final Solution crsSolution = crs.getSolution(board, fitnessPercentage, iterations);
				System.out.println(crsSolution);

				// default 90, 50, 50
				final GeneticAlgorithm ga = new GeneticAlgorithm();
				final Solution gaSolution = ga.getSolution(board, percentageStop, crossoverProbability, mutateProbability);
				System.out.println(gaSolution);
				
				executed = true;
			    }
			}
		    }
		    lastRun = System.currentTimeMillis();
		}
	    }
	}
    }
 
    private static Board generateRandomBoard(final int size) {
	final Board board = new Board(size);
	
	final Random random = new Random();
	
	final int[][] positions = new int[size][1];
	
	for (int i = 0; i < size; i ++) { // rows
	    positions[i] = new int[]{i, random.nextInt(size)};
	}
	
	board.setQueens(positions);
	
	return board;
    }
    
}
