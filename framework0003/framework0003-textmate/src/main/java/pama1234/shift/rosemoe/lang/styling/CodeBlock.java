package pama1234.shift.rosemoe.lang.styling;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import pama1234.shift.misc.NonNull;

public class CodeBlock{
  public static final Comparator<CodeBlock> COMPARATOR_END=(a,b)-> {
    var res=Integer.compare(a.endLine,b.endLine);
    if(res==0) {
      return Integer.compare(a.endColumn,b.endColumn);
    }else {
      return res;
    }
  };
  public static final Comparator<CodeBlock> COMPARATOR_START=(a,b)-> {
    var res=Integer.compare(a.startLine,b.startLine);
    if(res==0) {
      return Integer.compare(a.startColumn,b.startColumn);
    }else {
      return res;
    }
  };
  public static int binarySearchEndBlock(int line,List<CodeBlock> blocks) {
    if(blocks==null||blocks.isEmpty()) {
      return -1;
    }
    int left=0,right=blocks.size()-1,mid,row;
    int max=right;
    while(left<=right) {
      mid=left+(right-left)/2;
      if(mid<0||mid>max) {
        return -1;
      }
      CodeBlock block=blocks.get(mid);
      if(block==null) {
        int nonNullLeft=mid-1;
        int nonNullRight=mid+1;
        while(true) {
          if(nonNullLeft<left&&nonNullRight>right) {
            return -1;
          }else if(nonNullLeft>=left&&blocks.get(nonNullLeft)!=null) {
            mid=nonNullLeft;
            break;
          }else if(nonNullRight<=right&&blocks.get(nonNullRight)!=null) {
            mid=nonNullRight;
            break;
          }
          nonNullLeft--;
          nonNullRight++;
        }
        block=blocks.get(mid);
      }
      row=block.endLine;
      if(row>line) {
        right=mid-1;
      }else if(row<line) {
        left=mid+1;
      }else {
        left=mid;
        break;
      }
    }
    if(left<0||left>max) {
      return -1;
    }
    return left;
  }
  public int startLine;
  public int startColumn;
  public int endLine;
  public int endColumn;
  public boolean toBottomOfEndLine;
  public void clear() {
    startColumn=startLine=endLine=endColumn=0;
    toBottomOfEndLine=false;
  }
  @Override
  public boolean equals(Object o) {
    if(this==o) {
      return true;
    }
    if(o==null||getClass()!=o.getClass()) {
      return false;
    }
    CodeBlock codeBlock=(CodeBlock)o;
    return startLine==codeBlock.startLine&&startColumn==codeBlock.startColumn&&
      endLine==codeBlock.endLine&&endColumn==codeBlock.endColumn&&
      toBottomOfEndLine==codeBlock.toBottomOfEndLine;
  }
  @Override
  public int hashCode() {
    return Objects.hash(startLine,startColumn,endLine,endColumn,toBottomOfEndLine);
  }
  @NonNull
  @Override
  public String toString() {
    //@formatter:off
    return "BlockLine{"+
      "startLine="+
      startLine+
      ", startColumn="+
      startColumn+
      ", endLine="+
      endLine+
      ", endColumn="+
      endColumn+
      ", toBottomOfEndLine="+
      toBottomOfEndLine+
      '}';
    //@formatter:on
  }
}
