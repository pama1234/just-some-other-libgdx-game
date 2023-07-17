package pama1234.shift.rosemoe.lang.styling;

import pama1234.shift.rosemoe.TextMate;
import pama1234.shift.rosemoe.annotations.Experimental;
import pama1234.shift.rosemoe.widget.schemes.EditorColorScheme;

@Experimental
public interface ExternalRenderer{
  boolean requirePreDraw();
  boolean requirePostDraw();
  void draw(TextMate tm,EditorColorScheme colorScheme,boolean preOrPost);
}
