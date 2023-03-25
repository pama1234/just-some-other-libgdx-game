package pama1234.gdx.game.neat;

import pama1234.gdx.game.neat.util.Genome;
import pama1234.gdx.game.neat.util.Population;

/**
 * 使用AI辅助制作，由多个AI生成的内容拼凑而成，未验证
 * </p>
 * 参考了 https://github.com/raimannma/NEAT4J
 */
public class NEAT{
  private NEATConfig config;
  private Population population;
  private FitnessEvaluator evaluator;
  private Genome bestGenome;
  private int generationCount;
  public NEAT(NEATConfig config) {
    this.config=config;
    this.evaluator=new FitnessEvaluator();
    this.population=new Population(config);
    this.population.initialize();
    this.bestGenome=population.getBestGenome();
    this.generationCount=0;
  }
  public void update() {
    // 计算种群适应度
    evaluator.evaluate(population);
    // 更新最佳基因组
    Genome bestGenomeInPopulation=population.getBestGenome();
    if(bestGenomeInPopulation.getScore()>bestGenome.getScore()) {
      bestGenome=bestGenomeInPopulation;
    }
    // 选择和繁殖下一代
    population.breedNextGeneration();
    // 更新迭代计数器
    generationCount++;
  }
  public float[] evaluate(float[] inputs) {
    return bestGenome.evaluate(inputs);
  }
  public int getGenerationCount() {
    return generationCount;
  }
  public void setGenerationCount(int generationCount) {
    this.generationCount=generationCount;
  }
  public NEATConfig getConfig() {
    return config;
  }
  public void setConfig(NEATConfig config) {
    this.config=config;
  }
}