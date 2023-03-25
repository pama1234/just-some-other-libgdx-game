package pama1234.gdx.game.neat.util;

import java.util.List;
import java.util.stream.IntStream;

public abstract class Selector{
  /**
   * Select a genome from the population by applying a selection method.
   *
   * @param population the population
   * @return the network
   */
  public abstract Genome select(List<Genome> population);
  /**
   * Fitness-Proportionate-Selector.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Fitness_proportionate_selection">Wikipedia
   *      article</a>
   */
  public final static class FitnessProportionate extends Selector{
    @Override
    public Genome select(final List<Genome> population) {
      final double minimalFitness=population.stream().mapToDouble(genome->genome.score).min().orElseThrow();
      final double totalFitness=population.stream().mapToDouble(genome->genome.score).sum();
      final double random=Utils.randDouble(totalFitness+minimalFitness*population.size());
      double value=0;
      for(final Genome genome:population) {
        value+=genome.score+minimalFitness;
        if(random<value) {
          return genome;
        }
      }
      return Utils.pickRandom(population);
    }
  }
  /**
   * Power-Selector.
   */
  public final static class Power extends Selector{
    /**
     * The power value.
     * <p>
     * Minimum: 0
     * <p>
     * Lower value: more random Higher value: more fitter genomes gets selected
     */
    private final int power;
    /**
     * Creates a new Power instance.
     */
    public Power() {
      this(4);
    }
    /**
     * Creates a new Power instance.
     *
     * @param power the power
     */
    public Power(final int power) {
      this.power=Math.max(0,power);
    }
    @Override
    public Genome select(final List<Genome> population) {
      if(population.get(0).score<population.get(1).score) {
        population.sort((o1,o2)->Double.compare(o2.score,o1.score));
      }
      return population.get((int)Math.floor(Math.pow(Utils.randDouble(),this.power)*population.size()));
    }
  }
  /**
   * Tournament-Selector.
   *
   * @see <a href="https://en.wikipedia.org/wiki/Tournament_selection">Wikipedia article</a>
   */
  public final static class Tournament extends Selector{
    /**
     * The group size of one tournament. Minimum: 0 Maximum: population size
     */
    private final int size;
    /**
     * Creates a new Tournament-Selector instance.
     *
     * @param size the size
     */
    public Tournament(final int size) {
      this.size=size;
    }
    @Override
    public Genome select(final List<Genome> population) {
      return IntStream.range(0,Math.min(population.size(),this.size))
        .mapToObj(i->Utils.pickRandom(population))
        .max(Comparator.comparingDouble(o->o.score))
        .orElse(Utils.pickRandom(population));
    }
  }
}