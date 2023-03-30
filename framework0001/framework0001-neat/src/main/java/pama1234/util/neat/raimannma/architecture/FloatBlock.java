package pama1234.util.neat.raimannma.architecture;

public class FloatBlock{
  private float[] data;
  public int offset,size;
  public FloatBlock(float[] data,int offset,int size) {
    this.data=data;
    this.offset=offset;
    this.size=size;
  }
  public FloatBlock(float[] data) {
    this.data=data;
    size=data.length;
  }
  public float[] data() {
    return data;
  }
  public void data(float[] in) {
    offset=0;
    size=in.length;
    data=in;
  }
  @Deprecated
  public void data(float[] in,int offset) {
    data(in,offset,in.length-offset);
  }
  public void data(float[] in,int offset,int size) {
    this.offset=offset;
    this.size=size;
    data=in;
  }
  public float get(int pos) {
    return data[offset+pos];
  }
  public void set(int pos,float in) {
    data[offset+pos]=in;
  }
}