package pama1234.shift.rosemoe.text;

import org.eclipse.jdt.annotation.NonNull;

public class TextRange{
  private CharPosition start;
  private CharPosition end;
  public TextRange(@NonNull CharPosition start,@NonNull CharPosition end) {
    this.start=start;
    this.end=end;
  }
  @NonNull
  public CharPosition getStart() {
    return start;
  }
  public void setStart(@NonNull CharPosition start) {
    this.start=start;
  }
  @NonNull
  public CharPosition getEnd() {
    return end;
  }
  public void setEnd(@NonNull CharPosition end) {
    this.end=end;
  }
  public int getStartIndex() {
    return start.index;
  }
  public int getEndIndex() {
    return end.index;
  }
}
