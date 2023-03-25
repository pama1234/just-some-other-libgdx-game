package pama1234.gdx.game.neat;

import pama1234.gdx.game.neat.util.Genome;
import pama1234.gdx.game.neat.util.Population;

public class FitnessEvaluator{
  // Evaluate the fitness of a given population
  public float evaluate(Population population) {
    float totalFitness=0;
    // Iterate through each individual in the population
    for(Genome genome:population.genomes) {
      // Calculate the fitness of the individual
      float fitness=genome.getFitness();
      // Add the fitness to the total fitness
      totalFitness+=fitness;
    }
    // Return the average fitness of the population
    return totalFitness/population.genomes.size();
  }
}