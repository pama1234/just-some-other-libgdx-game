package pama1234.data;

public interface Data<D>{
  D toData();
  void fromData(D in);
}