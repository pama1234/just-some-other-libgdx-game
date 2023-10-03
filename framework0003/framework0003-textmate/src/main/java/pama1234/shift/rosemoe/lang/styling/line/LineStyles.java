package pama1234.shift.rosemoe.lang.styling.line;

import java.util.ArrayList;
import java.util.List;

public class LineStyles extends LineAnchorStyle{
  private final List<LineAnchorStyle> styles=new ArrayList<>();
  public LineStyles(int line) {
    super(line);
  }
  public int addStyle(LineAnchorStyle style) {
    if(style instanceof LineStyles) {
      throw new IllegalArgumentException("Can not add LineStyles object");
    }
    if(style.getLine()!=getLine()) {
      throw new IllegalArgumentException("target line differs from this object");
    }
    int result=1;
    LineAnchorStyle foundStyle=findOne(style.getClass());
    if(foundStyle!=null) {
      eraseStyle(foundStyle.getClass());
      result=0;
    }
    styles.add(style);
    return result;
  }
  public <T extends LineAnchorStyle> int eraseStyle(Class<T> type) {
    List<LineAnchorStyle> all=findAll(type);
    styles.removeAll(all);
    return all.size();
  }
  public void updateElements() {
    for(LineAnchorStyle style:styles) {
      style.setLine(getLine());
    }
  }
  public int getElementCount() {
    return styles.size();
  }
  public LineAnchorStyle getElementAt(int index) {
    return styles.get(index);
  }
  public <T extends LineAnchorStyle> T findOne(Class<T> type) {
    for(LineAnchorStyle element:styles) {
      if(type.isInstance(element)) {
        return type.cast(element);
      }
    }
    return null;
  }
  public <T extends LineAnchorStyle> List<LineAnchorStyle> findAll(Class<T> type) {
    List<LineAnchorStyle> filteredStyles=new ArrayList<>();
    for(LineAnchorStyle element:styles) {
      if(type.isInstance(element)) {
        filteredStyles.add(element);
      }
    }
    return filteredStyles;
  }
  public int typedElementCount(Class<?> type) {
    int count=0;
    for(LineAnchorStyle element:styles) {
      if(type.isInstance(element)) {
        count++;
      }
    }
    return count;
  }
}
