package pama1234.gdx.game.neat.util;

import pama1234.gdx.game.neat.Genome;
import pama1234.gdx.game.neat.Population;

public class FitnessEvaluator{
  // Evaluate the fitness of a given population
  public double evaluate(Population population) {
    double totalFitness=0;
    // Iterate through each individual in the population
    for(Genome genome:population.genomes) {
      // Calculate the fitness of the individual
      double fitness=genome.getFitness();
      // Add the fitness to the total fitness
      totalFitness+=fitness;
    }
    // Return the average fitness of the population
    return totalFitness/population.genomes.size();
  }
}