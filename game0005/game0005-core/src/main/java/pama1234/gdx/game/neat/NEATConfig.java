package pama1234.gdx.game.neat;

public class NEATConfig{
  private int numInputs;
  private int numOutputs;
  private int populationSize;
  private int maxGenerations;
  private double mutationRate;
  private double c1;
  private double c2;
  private double c3;
  private double compatibilityThreshold;
  public NEATConfig() {
    this.numInputs=0;
    this.numOutputs=0;
    this.populationSize=0;
    this.maxGenerations=0;
    this.mutationRate=0.5;
    this.c1=1.0;
    this.c2=1.0;
    this.c3=0.4;
    this.compatibilityThreshold=3.0;
  }
  public void setNumInputs(int numInputs) {
    this.numInputs=numInputs;
  }
  public void setNumOutputs(int numOutputs) {
    this.numOutputs=numOutputs;
  }
  public void setPopulationSize(int populationSize) {
    this.populationSize=populationSize;
  }
  public void setMaxGenerations(int maxGenerations) {
    this.maxGenerations=maxGenerations;
  }
  public void setMutationRate(double mutationRate) {
    this.mutationRate=mutationRate;
  }
  public void setC1(double c1) {
    this.c1=c1;
  }
  public void setC2(double c2) {
    this.c2=c2;
  }
  public void setC3(double c3) {
    this.c3=c3;
  }
  public void setCompatibilityThreshold(double compatibilityThreshold) {
    this.compatibilityThreshold=compatibilityThreshold;
  }
  public int getNumInputs() {
    return numInputs;
  }
  public int getNumOutputs() {
    return numOutputs;
  }
  public int getPopulationSize() {
    return populationSize;
  }
  public int getMaxGenerations() {
    return maxGenerations;
  }
  public double getMutationRate() {
    return mutationRate;
  }
  public double getC1() {
    return c1;
  }
  public double getC2() {
    return c2;
  }
  public double getC3() {
    return c3;
  }
  public double getCompatibilityThreshold() {
    return compatibilityThreshold;
  }
}