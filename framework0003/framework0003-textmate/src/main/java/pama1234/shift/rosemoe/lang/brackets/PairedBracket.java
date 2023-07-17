package pama1234.shift.rosemoe.lang.brackets;

public class PairedBracket{
  public final int leftIndex,leftLength,rightIndex,rightLength;
  public PairedBracket(int leftIndex,int rightIndex) {
    this(leftIndex,1,rightIndex,1);
  }
  public PairedBracket(int leftIndex,int leftLength,int rightIndex,int rightLength) {
    this.leftIndex=leftIndex;
    this.leftLength=leftLength;
    this.rightIndex=rightIndex;
    this.rightLength=rightLength;
  }
}
