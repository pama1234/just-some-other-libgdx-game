package pama1234.data;

public interface Factory<D,T>{
  public D save(T in);
  public T load(D in);
}
