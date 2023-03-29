package pama1234.util.neat.raimannma.architecture;

public class FloatBlock{
  public int offset,size;
  private float[] data;
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
  public float data(int pos) {
    return data[offset+pos];
  }
}