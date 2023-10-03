package pama1234.shift.rosemoe.lang.styling;

import java.util.List;

import pama1234.shift.rosemoe.text.CharPosition;

public interface Spans{
  void adjustOnInsert(CharPosition start,CharPosition end);
  void adjustOnDelete(CharPosition start,CharPosition end);
  Reader read();
  boolean supportsModify();
  Modifier modify();
  int getLineCount();
  interface Reader{
    void moveToLine(int line);
    int getSpanCount();
    Span getSpanAt(int index);
    List<Span> getSpansOnLine(int line);
  }
  interface Modifier{
    void setSpansOnLine(int line,List<Span> spans);
    void addLineAt(int line,List<Span> spans);
    void deleteLineAt(int line);
  }
}
