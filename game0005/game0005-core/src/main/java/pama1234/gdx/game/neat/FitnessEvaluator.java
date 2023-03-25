package pama1234.gdx.game.neat;

import pama1234.gdx.game.neat.util.Evaluator;
import pama1234.gdx.game.neat.util.Genome;
import pama1234.gdx.game.neat.util.Population;

public class FitnessEvaluator extends Evaluator{
  // Evaluate the fitness of a given population
  @Override
  public float evaluate(Population population) {
    float totalFitness=0;
    // Iterate through each individual in the population
    for(Genome genome:population.genomes) {
      // Calculate the fitness of the individual
      float fitness=genome.getScore();
      // Add the fitness to the total fitness
      totalFitness+=fitness;
    }
    // Return the average fitness of the population
    return totalFitness/population.genomes.size();
  }
}