package ics2207.optimisers;

import ics2207.board.Board;
import static ics2207.optimisers.solution.Algorithm.GENETIC_ALGORITHM;
import ics2207.optimisers.solution.Solution;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    
    private final Random random = new Random();
    
    public Solution getSolution(final Board board, final double percentageStop, final double crossoverProbability, final double mutateProbability) {
	final long start = System.currentTimeMillis();
	int totalIterations = 0;
	
	final int[][] queens = board.getQueens();
	
	String population = Calculations.convertArrayToPopulation(queens);
	
	ArrayList<Double> fitness = new ArrayList<>();
	
	for (final int[] pos : queens) {
	    fitness.add(Calculations.evaluateFitness(board.getSize(), new int[]{pos[0], pos[1]}, population));
	}
	
	double average = Calculations.getAverageFitness(fitness);
	
	final ChromosomeHolder holder = new ChromosomeHolder();
	holder.add(new Chromosome(population, average));
	
	while (average < percentageStop) {
	    totalIterations++;
	    
	    final int size = holder.getSize();
	    
	    if (size >= 2) { // perform crossover
		holder.update();
		
		String tempPop = "";
		
		final String pop1 = holder.getChromosomes()[0].getPopulation();
		final String pop2 = holder.getChromosomes()[1].getPopulation();
		
		for (int i = 0; i < pop1.length(); i += 2) {
		    if (random.nextInt(101) <= crossoverProbability) { // do crossover
			tempPop += pop2.toCharArray()[i];
			tempPop += pop2.toCharArray()[i + 1];
		    } else { // keep original
			tempPop += pop1.toCharArray()[i];
			tempPop += pop1.toCharArray()[i + 1];
		    }
		}
		population = tempPop;
	    }
	    
	    // perform mutation on current population then add mutated population
	    
	    String temp = "";
	    
	    for (int i = 0; i < population.length(); i += 2) {
		if (random.nextInt(101) <= mutateProbability) { // do mutation
		    int randomNumber = random.nextInt(board.getSize());
		    if (randomNumber < 10) {
			temp += "0";
		    }
		    temp += randomNumber;
		} else {
		    temp += population.toCharArray()[i];
		    temp += population.toCharArray()[i + 1];
		}
	    }
	    
	    population = temp;
	    
	    final int[][] newPopArray = Calculations.convertPopulationToArray(population);
	    
	    fitness.clear();
	    for (final int[] pop : newPopArray) {
		fitness.add(Calculations.evaluateFitness(board.getSize(), new int[]{pop[0], pop[1]}, population));
	    }
	    
	    average = Calculations.getAverageFitness(fitness);
	    
	    holder.add(new Chromosome(population, average));
	}
	return new Solution(GENETIC_ALGORITHM, System.currentTimeMillis() - start, totalIterations, average);
    }
    
    public class ChromosomeHolder {
	
	private final ArrayList<Chromosome> chromosomes;
	
	ChromosomeHolder() {
	    this.chromosomes = new ArrayList<>();
	}
	
	public void add(final Chromosome chromosome) {
	    chromosomes.add(chromosome);
	}
	
	public Chromosome[] getChromosomes() {
	    return chromosomes.toArray(new Chromosome[chromosomes.size()]);
	}
	
	public void update() {	    
	    while (chromosomes.size() > 2) {
		double lowestFitness = Double.MAX_VALUE;
		int worstIndex = -1;
		
		for (int i = 0; i < chromosomes.size(); i ++) {
		    final Chromosome chromosome = chromosomes.get(i);
		    if (chromosome.getFitness() < lowestFitness) {
			lowestFitness = chromosome.getFitness();
			worstIndex = i;
		    }
		}
		
		if (worstIndex != -1) {
		    chromosomes.remove(worstIndex);
		}
	    }
	}
	
	public int getSize() {
	    return chromosomes.size();
	}
    }
    
    public class Chromosome {
	 
	    private final String population;
	    private final double fitness;
	    
	    Chromosome(final String population, final double fitness) {
		this.population = population;
		this.fitness = fitness;
	    }
	    
	    public String getPopulation() {
		return population;
	    }
	    
	    public double getFitness() {
		return fitness;
	    }
	}
}