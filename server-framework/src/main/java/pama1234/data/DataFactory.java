package pama1234.data;

public interface DataFactory<D,T extends Data<D>>extends Factory<D,T>{
  @Override
  D save(T in);
  @Override
  T load(D in);

  D saveTo(T in,D data);
  T loadTo(D in,T element);
}
