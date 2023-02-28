package pama1234.math.hash;

public abstract class HashFunction2f{
  public float seed;
  public HashFunction2f(float seed) {
    this.seed=seed;
  }
  public abstract float get(float x,float y);
}
