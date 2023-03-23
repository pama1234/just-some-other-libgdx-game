package pama1234.gdx.game.neat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population{
  private NEATConfig config;
  public List<Genome> genomes;
  /**
   * 缓存列表，使用后需要清空
   */
  private List<Genome> newGenomes;
  @Deprecated
  private int speciesCount;
  public Population(NEATConfig config) {
    this.config=config;
    this.genomes=new ArrayList<>();
    this.newGenomes=new ArrayList<>();
    this.speciesCount=0;
  }
  public void initialize() {
    for(int i=0;i<config.getPopulationSize();i++) {
      Genome genome=new Genome(config.getNumInputs(),config.getNumOutputs());
      genome.initialize();
      genomes.add(genome);
    }
    speciesCount=1;
  }
  public void breedNextGeneration() {
    // 各个种群自我繁殖
    for(List<Genome> species:getSpecies()) {
      breedSpecies(species);
    }
    // 删除适应度低的个体
    genomes.sort(Collections.reverseOrder());
    while(genomes.size()>config.getPopulationSize()) {
      genomes.remove(genomes.size()-1);
    }
    // 将新个体加入种群
    for(int i=0;i<config.getPopulationSize()-genomes.size();i++) {
      Genome genome=new Genome(config.getNumInputs(),config.getNumOutputs());
      genome.initialize();
      newGenomes.add(genome);
    }
    for(Genome genome:newGenomes) {
      genomes.add(genome);
    }
    // 清空分组列表和新个体列表
    // speciesList.clear();
    newGenomes.clear();
  }
  public List<Genome> getBestGenomes() {
    List<Genome> bestGenomes=new ArrayList<>();
    genomes.sort(Collections.reverseOrder());
    for(int i=0;i<Math.min(10,genomes.size());i++) {
      bestGenomes.add(genomes.get(i));
    }
    return bestGenomes;
  }
  public Genome getBestGenome() {
    genomes.sort(Collections.reverseOrder());
    return genomes.get(0);
  }
  private void breedSpecies(List<Genome> species) {
    // 计算适应度总和和平均适应度
    double totalFitness=0.0;
    for(Genome genome:species) {
      totalFitness+=genome.getFitness();
    }
    double averageFitness=totalFitness/species.size();
    // 计算每个基因组的繁殖次数
    for(Genome genome:species) {
      double fitnessProportion=genome.getFitness()/totalFitness;
      int numOffspring=(int)(fitnessProportion*config.getPopulationSize()-1);
      for(int i=0;i<numOffspring;i++) {
        Genome offspring=breed(genome,getRandomGenome(species),averageFitness);
        newGenomes.add(offspring);
      }
    }
  }
  private List<List<Genome>> getSpecies() {
    // 分组个体
    List<List<Genome>> speciesList=new ArrayList<>();
    speciesList.add(Collections.singletonList(genomes.get(0)));
    for(int i=1;i<genomes.size();i++) {
      Genome genome=genomes.get(i);
      boolean assigned=false;
      for(List<Genome> species:speciesList) {
        if(genome.isCompatibleWith(species.get(0),config)) {
          species.add(genome);
          assigned=true;
          break;
        }
      }
      if(!assigned) {
        List<Genome> newSpecies=new ArrayList<>();
        newSpecies.add(genome);
        speciesList.add(newSpecies);
      }
    }
    return speciesList;
  }
  private Genome breed(Genome parent1,Genome parent2,double averageFitness) {
    Genome offspring;
    if(parent1.getFitness()==parent2.getFitness()) {
      offspring=parent1.crossover(parent2);
    }else if(parent1.getFitness()>parent2.getFitness()) {
      offspring=parent1.clone();
    }else {
      offspring=parent2.clone();
    }
    offspring.mutate(config.getMutationRate());
    offspring.setFitness(averageFitness);
    return offspring;
  }
  private Genome getRandomGenome(List<Genome> genomes) {
    return genomes.get((int)(Math.random()*genomes.size()));
  }
}