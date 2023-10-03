package pama1234.math.hash;

@Deprecated
public abstract class HashFunction3f{
  public float seed;
  public HashFunction3f(float seed) {
    this.seed=seed;
  }
  public abstract float get(float x,float y,float z);
}
