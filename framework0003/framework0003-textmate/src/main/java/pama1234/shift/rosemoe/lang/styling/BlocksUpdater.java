package pama1234.shift.rosemoe.lang.styling;

import java.util.List;

public class BlocksUpdater{
  public static void update(List<CodeBlock> blocks,int restrict,int delta) {
    if(delta==0) {
      return;
    }
    var itr=blocks.iterator();
    while(itr.hasNext()) {
      var block=itr.next();
      if(block.startLine>=restrict) {
        block.startLine+=delta;
      }
      if(block.endLine>=restrict) {
        block.endLine+=delta;
      }
      if(block.startLine>=block.endLine) {
        itr.remove();
      }
    }
  }
}
