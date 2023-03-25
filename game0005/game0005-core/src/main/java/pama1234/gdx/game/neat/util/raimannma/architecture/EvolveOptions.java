package pama1234.gdx.game.neat.util.raimannma.architecture;

import java.util.Arrays;
import java.util.Objects;

import pama1234.gdx.game.neat.util.raimannma.methods.Loss;
import pama1234.gdx.game.neat.util.raimannma.methods.Mutation;
import pama1234.gdx.game.neat.util.raimannma.methods.Selection;
import pama1234.util.function.GetFloatWith;

/**
 * The type Evolve options.
 *
 * @author Manuel Raimann
 */
public class EvolveOptions{
  /**
   * Determines how "fit" a network is. A higher value means, that the network is fitter.
   */
  private GetFloatWith<Network> fitnessFunction;
  /**
   * Amount of networks in every population.
   */
  private int populationSize;
  /**
   * Amount of networks that survive a evolution step without mutation (fittest ones get picked
   * first).
   */
  private int elitism;
  /**
   * The probability that one network gets mutated.
   */
  private float mutationRate;
  /**
   * Amount of mutation steps a network does, if it gets mutated.
   */
  private int mutationAmount;
  /**
   * The way networks are selected for breeding the offspring.
   */
  private Selection selection;
  /**
   * For mutating allowed methods.
   */
  private Mutation[] mutations;
  /**
   * The template to start the first population from.
   */
  private Network template;
  /**
   * Maximum amount of nodes in a network.
   */
  private int maxNodes;
  /**
   * Maximum amount of connections in a network.
   */
  private int maxConnections;
  /**
   * Maximum amount of gates in a network.
   */
  private int maxGates;
  /**
   * Should the crossover function consider both parents as equally fit?
   */
  private boolean equal;
  /**
   * Clear the context of all nodes, useful for timeseries predictions.
   */
  private boolean clear;
  /**
   * The target error. Evolution would stop, if this error rate has been reached.
   */
  private float error;
  /**
   * Set a penalty for large networks. Should be a small number.
   */
  private float growth;
  /**
   * How often should the trainingset be tested for every network? Should be 1 for feedforward
   * problems. Useful for time series dataset.
   */
  private int amount;
  /**
   * The loss function used to determine the fitness. Unnecessary if you set your own fitness
   * function.
   */
  private Loss loss;
  /**
   * Amount of evolution iterations.
   */
  private int iterations;
  /**
   * Log after "generation mod log == 0". 1 -> Log after each generation 10 -> Log every 10th
   * generation
   */
  private int log;
  /**
   * Instantiates a new Evolve options.
   */
  public EvolveOptions() {
    this.setError(Float.NaN);
    this.setGrowth(0.0001f);
    this.setLoss(Loss.MSE);
    this.setAmount(1);
    this.setIterations(-1);
    this.setClear(false);
    this.setPopulationSize(100);
    this.setElitism(10);
    this.setMutationRate(0.4f);
    this.setMutationAmount(1);
    this.setSelection(new Selection.FitnessProportionate());
    this.setMutations(Mutation.ALL);
    this.setTemplate(null);
    this.setMaxNodes(Integer.MAX_VALUE);
    this.setMaxConnections(Integer.MAX_VALUE);
    this.setMaxGates(Integer.MAX_VALUE);
  }
  /**
   * Gets fitness function.
   *
   * @return the fitness function
   */
  public GetFloatWith<Network> getFitnessFunction() {
    return this.fitnessFunction;
  }
  /**
   * Sets fitness function.
   *
   * @param fitnessFunction the fitness function
   * @return itself to function as builder class
   */
  public EvolveOptions setFitnessFunction(final GetFloatWith<Network> fitnessFunction) {
    this.fitnessFunction=fitnessFunction;
    return this;
  }
  /**
   * Gets population size.
   *
   * @return the population size
   */
  public int getPopulationSize() {
    return this.populationSize;
  }
  /**
   * Sets population size.
   *
   * @param populationSize the population size
   * @return itself to function as builder class
   */
  public EvolveOptions setPopulationSize(final int populationSize) {
    this.populationSize=populationSize;
    return this;
  }
  /**
   * Gets elitism.
   *
   * @return the elitism
   */
  public int getElitism() {
    return this.elitism;
  }
  /**
   * Sets elitism.
   *
   * @param elitism the elitism
   * @return itself to function as builder class
   */
  public EvolveOptions setElitism(final int elitism) {
    this.elitism=elitism;
    return this;
  }
  /**
   * Gets mutation rate.
   *
   * @return the mutation rate
   */
  public float getMutationRate() {
    return this.mutationRate;
  }
  /**
   * Sets mutation rate.
   *
   * @param mutationRate the mutation rate
   * @return itself to function as builder class
   */
  public EvolveOptions setMutationRate(final float mutationRate) {
    this.mutationRate=mutationRate;
    return this;
  }
  /**
   * Gets mutation amount.
   *
   * @return the mutation amount
   */
  public int getMutationAmount() {
    return this.mutationAmount;
  }
  /**
   * Sets mutation amount.
   *
   * @param mutationAmount the mutation amount
   * @return itself to function as builder class
   */
  public EvolveOptions setMutationAmount(final int mutationAmount) {
    this.mutationAmount=mutationAmount;
    return this;
  }
  /**
   * Gets selection.
   *
   * @return the selection
   */
  public Selection getSelection() {
    return this.selection;
  }
  /**
   * Sets selection.
   *
   * @param selection the selection
   * @return itself to function as builder class
   */
  public EvolveOptions setSelection(final Selection selection) {
    this.selection=selection;
    return this;
  }
  /**
   * Get mutations.
   *
   * @return the mutations
   */
  public Mutation[] getMutations() {
    return this.mutations;
  }
  /**
   * Sets mutations.
   *
   * @param mutations the mutations
   * @return itself to function as builder class
   */
  public EvolveOptions setMutations(final Mutation[] mutations) {
    this.mutations=mutations;
    return this;
  }
  /**
   * Gets template.
   *
   * @return the template
   */
  public Network getTemplate() {
    return this.template;
  }
  /**
   * Sets template.
   *
   * @param template the template
   * @return itself to function as builder class
   */
  public EvolveOptions setTemplate(final Network template) {
    this.template=template;
    return this;
  }
  /**
   * Gets max nodes.
   *
   * @return the max nodes
   */
  public int getMaxNodes() {
    return this.maxNodes;
  }
  /**
   * Sets max nodes.
   *
   * @param maxNodes the max nodes
   * @return itself to function as builder class
   */
  public EvolveOptions setMaxNodes(final int maxNodes) {
    this.maxNodes=maxNodes;
    return this;
  }
  /**
   * Gets max connections.
   *
   * @return the max connections
   */
  public int getMaxConnections() {
    return this.maxConnections;
  }
  /**
   * Sets max connections.
   *
   * @param maxConnections the max connections
   * @return itself to function as builder class
   */
  public EvolveOptions setMaxConnections(final int maxConnections) {
    this.maxConnections=maxConnections;
    return this;
  }
  /**
   * Gets max gates.
   *
   * @return the max gates
   */
  public int getMaxGates() {
    return this.maxGates;
  }
  /**
   * Sets max gates.
   *
   * @param maxGates the max gates
   * @return itself to function as builder class
   */
  public EvolveOptions setMaxGates(final int maxGates) {
    this.maxGates=maxGates;
    return this;
  }
  /**
   * Is equal.
   *
   * @return the boolean
   */
  public boolean isEqual() {
    return this.equal;
  }
  /**
   * Sets equal.
   *
   * @param equal the equal
   * @return itself to function as builder class
   */
  public EvolveOptions setEqual(final boolean equal) {
    this.equal=equal;
    return this;
  }
  /**
   * Is clear.
   *
   * @return the boolean
   */
  public boolean isClear() {
    return this.clear;
  }
  /**
   * Sets clear.
   *
   * @param clear the clear
   * @return itself to function as builder class
   */
  public EvolveOptions setClear(final boolean clear) {
    this.clear=clear;
    return this;
  }
  /**
   * Gets error.
   *
   * @return the error
   */
  public float getError() {
    return this.error;
  }
  /**
   * Sets error.
   *
   * @param error the error
   * @return itself to function as builder class
   */
  public EvolveOptions setError(final float error) {
    this.error=error;
    return this;
  }
  /**
   * Gets growth.
   *
   * @return the growth
   */
  public float getGrowth() {
    return this.growth;
  }
  /**
   * Sets growth.
   *
   * @param growth the growth
   * @return itself to function as builder class
   */
  public EvolveOptions setGrowth(final float growth) {
    this.growth=growth;
    return this;
  }
  /**
   * Gets amount.
   *
   * @return the amount
   */
  public int getAmount() {
    return this.amount;
  }
  /**
   * Sets amount.
   *
   * @param amount the amount
   * @return itself to function as builder class
   */
  public EvolveOptions setAmount(final int amount) {
    this.amount=amount;
    return this;
  }
  /**
   * Gets loss.
   *
   * @return the loss
   */
  public Loss getLoss() {
    return this.loss;
  }
  /**
   * Sets loss.
   *
   * @param loss the loss
   * @return itself to function as builder class
   */
  public EvolveOptions setLoss(final Loss loss) {
    this.loss=loss;
    return this;
  }
  /**
   * Gets iterations.
   *
   * @return the iterations
   */
  public int getIterations() {
    return this.iterations;
  }
  /**
   * Sets iterations.
   *
   * @param iterations the iterations
   * @return itself to function as builder class
   */
  public EvolveOptions setIterations(final int iterations) {
    this.iterations=iterations;
    return this;
  }
  /**
   * Gets log.
   *
   * @return the log
   */
  public int getLog() {
    return this.log;
  }
  /**
   * Sets log.
   *
   * @param log the log
   * @return itself to function as builder class
   */
  public EvolveOptions setLog(final int log) {
    this.log=log;
    return this;
  }
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.mutations)
      +31*Objects.hash(this.fitnessFunction,this.populationSize,this.elitism,this.mutationRate,this.mutationAmount,this.selection,this.template,this.maxNodes,this.maxConnections,this.maxGates,this.equal,this.clear,this.error,this.growth,this.amount,this.loss,this.iterations,this.log);
  }
}
