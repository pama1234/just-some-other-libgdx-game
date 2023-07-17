package pama1234.shift.rosemoe.util;

public class LongArrayList{
  private long[] data;
  private int length;
  public LongArrayList() {
    data=new long[64];
  }
  public void add(long value) {
    data[length++]=value;
    if(data.length==length) {
      long[] newData=new long[length<<1];
      System.arraycopy(data,0,newData,0,length);
      data=newData;
    }
  }
  public int size() {
    return length;
  }
  public void set(int index,long value) {
    if(index>=length||index<0) {
      throw new ArrayIndexOutOfBoundsException(index);
    }
    data[index]=value;
  }
  public int lowerBoundByFirst(int key) {
    int low=0;
    int high=length-1;
    while(low<=high) {
      int mid=(low+high)>>>1;
      long midVal=IntPair.getFirst(data[mid]);
      if(midVal<key) low=mid+1;
      else if(midVal>key) high=mid-1;
      else return mid; // key found
    }
    return low; // key not found.
  }
  public int lowerBound(long key) {
    int low=0;
    int high=length-1;
    while(low<=high) {
      int mid=(low+high)>>>1;
      long midVal=data[mid];
      if(midVal<key) low=mid+1;
      else if(midVal>key) high=mid-1;
      else return mid; // key found
    }
    return low; // key not found.
  }
  public long get(int index) {
    if(index>=length||index<0) {
      throw new ArrayIndexOutOfBoundsException(index);
    }
    return data[index];
  }
  public void clear() {
    length=0;
  }
}
