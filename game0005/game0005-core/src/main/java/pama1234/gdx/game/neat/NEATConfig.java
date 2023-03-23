package pama1234.gdx.game.neat;

public class NEATConfig{
  private int numInputs;
  private int numOutputs;
  private int populationSize;
  private int maxGenerations;
  private float mutationRate;
  private float c1;
  private float c2;
  private float c3;
  private float compatibilityThreshold;
  public NEATConfig() {
    this.numInputs=0;
    this.numOutputs=0;
    this.populationSize=0;
    this.maxGenerations=0;
    this.mutationRate=0.5f;
    this.c1=1.0f;
    this.c2=1.0f;
    this.c3=0.4f;
    this.compatibilityThreshold=3.0f;
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
  public void setMutationRate(float mutationRate) {
    this.mutationRate=mutationRate;
  }
  public void setC1(float c1) {
    this.c1=c1;
  }
  public void setC2(float c2) {
    this.c2=c2;
  }
  public void setC3(float c3) {
    this.c3=c3;
  }
  public void setCompatibilityThreshold(float compatibilityThreshold) {
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
  public float getMutationRate() {
    return mutationRate;
  }
  public float getC1() {
    return c1;
  }
  public float getC2() {
    return c2;
  }
  public float getC3() {
    return c3;
  }
  public float getCompatibilityThreshold() {
    return compatibilityThreshold;
  }
  // Method to get the disjoint coefficient
  public float getDisjointCoefficient() {
    return c1;
  }
  // Method to get the excess coefficient
  public float getExcessCoefficient() {
    return c2;
  } // Method to get the weight difference coefficient
  public float getWeightDifferenceCoefficient() {
    return c3;
  }
}