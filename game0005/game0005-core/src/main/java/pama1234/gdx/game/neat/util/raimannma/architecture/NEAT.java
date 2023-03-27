package pama1234.gdx.game.neat.util.raimannma.architecture;

import static pama1234.gdx.game.neat.util.raimannma.methods.Utils.pickRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import pama1234.gdx.game.neat.util.raimannma.methods.Mutation;
import pama1234.gdx.game.neat.util.raimannma.methods.Selection;
import pama1234.gdx.game.neat.util.raimannma.methods.Utils;

/**
 * The type Neat.
 *
 * @author Manuel Raimann
 */
public class NEAT{
  /**
   * The Output.
   */
  private final int output;
  /**
   * The Input.
   */
  private final int input;
  /**
   * The Fitness function.
   */
  private final ToDoubleFunction<Network> fitnessFunction;
  /**
   * The Equal.
   */
  private final boolean equal;
  /**
   * The Clear.
   */
  private final boolean clear;
  /**
   * The Mutation rate.
   */
  private final float mutationRate;
  /**
   * The Mutation amount.
   */
  private final int mutationAmount;
  /**
   * The Elitism.
   */
  private final int elitism;
  /**
   * The Max gates.
   */
  private final int maxGates;
  /**
   * The Max connections.
   */
  private final int maxConnections;
  /**
   * The Max nodes.
   */
  private final int maxNodes;
  /**
   * The Template.
   */
  private final Network template;
  /**
   * The Mutation.
   */
  private final Mutation[] mutation;
  /**
   * The Selection.
   */
  private final Selection selection;
  /**
   * The Generation.
   */
  public int generation;
  /**
   * The Population.
   */
  public List<Network> population;
  /**
   * The Population size.
   */
  private int populationSize;
  /**
   * Instantiates a new Neat.
   *
   * @param inputSize  the inputSize
   * @param outputSize the outputSize
   * @param options    the options
   */
  public NEAT(final int inputSize,final int outputSize,final EvolveOptions options) {
    this.input=inputSize;
    this.output=outputSize;
    this.fitnessFunction=options.getFitnessFunction();
    this.equal=options.isEqual();
    this.clear=options.isClear();
    this.populationSize=options.getPopulationSize();
    this.elitism=options.getElitism();
    this.mutationRate=options.getMutationRate();
    this.mutationAmount=options.getMutationAmount();
    this.selection=options.getSelection();
    this.mutation=options.getMutations();
    this.template=options.getTemplate();
    this.maxNodes=options.getMaxNodes();
    this.maxConnections=options.getMaxConnections();
    this.maxGates=options.getMaxGates();
    this.generation=0;
    // creating the initial population
    this.population=new ArrayList<>();
    for(int i=0;i<this.populationSize;i++) {
      final Network copy=this.template==null
        ?new Network(this.input,this.output)
        :this.template.copy();
      copy.score=Float.NaN;
      this.population.add(copy);
    }
  }
  /**
   * Evaluates, selects, breeds and mutates population
   *
   * @return the fittest network of the population
   */
  public Network evolve() {
    // evaluate the population
    this.evaluate();
    // sort the population
    this.sort();
    // store the fittest network
    final Network fittest=this.population.get(0).copy();
    // store the elitists in a separate list
    final List<Network> elitists=this.population.subList(0,this.elitism);
    // create new population
    final Set<Network> newPopulation=new HashSet<>();
    while(newPopulation.size()<this.populationSize-this.elitism) {
      // crossover and add to new population
      newPopulation.add(Network.crossover(this.getParent(),this.getParent(),this.equal));
    }
    // convert Set to List
    this.population=new ArrayList<>(newPopulation);
    // mutate the population
    this.mutate();
    // add all elitist to the new population
    this.population.addAll(elitists);
    // reset scores
    this.population.forEach(network->network.score=Float.NaN);
    // increment generation counter
    this.generation++;
    // return the fittest network
    return fittest;
  }
  /**
   * Evaluates this population.
   */
  private void evaluate() {
    if(this.clear) {
      this.population.forEach(Network::clear);
    }
    this.population
      .parallelStream() //parallel
      .forEach(genome-> {
        // calculate score with the given fitness function
        genome.score=(float)this.fitnessFunction.applyAsDouble(genome);
        if(Double.isNaN(genome.score)) {
          genome.score=-Float.MAX_VALUE;
        }
      });
  }
  /**
   * Sorts the population by score (descending).
   */
  private void sort() {
    // sort from high to low
    this.population.sort((o1,o2)->Double.compare(o2.score,o1.score));
  }
  /**
   * Returns a genome for crossover based on the selection method provided.
   *
   * @return Selected genome for offspring generation
   */
  private Network getParent() {
    return this.selection.select(this.population);
  }
  /**
   * Mutates the given population.
   */
  private void mutate() {
    for(final Network network:this.population) {
      if(Utils.randDouble()<=this.mutationRate) {
        for(int j=0;j<this.mutationAmount;j++) {
          network.mutate(this.selectMutationMethod(network));
        }
      }
    }
  }
  /**
   * Selects a random mutation method.
   *
   * @param genome the genome
   * @return the mutation
   */
  private Mutation selectMutationMethod(final Network genome) {
    Mutation mutationMethod;
    do {
      mutationMethod=pickRandom(this.mutation);
    }while((mutationMethod==Mutation.ADD_NODE&&genome.nodes.size()>=this.maxNodes)
      ||(mutationMethod==Mutation.ADD_CONN&&genome.connections.size()>=this.maxConnections)
      ||(mutationMethod==Mutation.ADD_GATE&&genome.gates.size()>=this.maxGates));
    return mutationMethod;
  }
  /**
   * Returns the fittest genome of the current population
   *
   * @return Current population's fittest genome
   */
  public Network getFittest() {
    this.evaluate();
    this.sort();
    return this.population.get(0);
  }
  /**
   * Returns the average fitness of the current population
   *
   * @return Average fitness of the current population
   */
  public float getAverage() {
    // evaluate
    this.evaluate();
    return (float)this.population.stream().mapToDouble(network->network.score).average().orElseThrow();
  }
  /**
   * Convert the population to a JsonObject.
   *
   * @return the json object representing the population
   */
  public JsonObject toJson() {
    final JsonArray jsonArray=new JsonArray();
    this.population.stream().map(Network::toJSON).forEach(jsonArray::add);
    final JsonObject jsonObject=new JsonObject();
    jsonObject.add("genomes",jsonArray);
    return jsonObject;
  }
  /**
   * Imports population from a json.
   *
   * @param jsonObject a population represented as JsonObject.
   */
  public void fromJson(final JsonObject jsonObject) {
    final JsonArray arr=jsonObject.get("genomes").getAsJsonArray();
    IntStream.range(0,arr.size())
      .forEach(i->this.population.add(Network.fromJSON(arr.get(i).getAsJsonObject())));
    this.populationSize=this.population.size();
  }
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.mutation)
      +31*Objects.hash(
        this.output,
        this.input,
        this.fitnessFunction,
        this.equal,
        this.clear,
        this.mutationRate,
        this.mutationAmount,
        this.elitism,
        this.maxGates,
        this.maxConnections,
        this.maxNodes,
        this.template,
        this.selection,
        this.generation,
        this.population,
        this.populationSize);
  }
}
