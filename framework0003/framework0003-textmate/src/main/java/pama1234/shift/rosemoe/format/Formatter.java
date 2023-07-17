package pama1234.shift.rosemoe.format;

import javax.annotation.Nullable;

import org.eclipse.tm4e.ui.text.Content;

import pama1234.shift.misc.NonNull;
import pama1234.shift.rosemoe.text.TextRange;

public interface Formatter{
  void format(@NonNull Content text,@NonNull TextRange cursorRange);
  void formatRegion(@NonNull Content text,@NonNull TextRange rangeToFormat,@NonNull TextRange cursorRange);
  void setReceiver(@Nullable FormatResultReceiver receiver);
  boolean isRunning();
  void destroy();
  default void cancel() {}
  interface FormatResultReceiver{
    void onFormatSucceed(Content text,@Nullable TextRange cursorRange);
    void onFormatFail(@Nullable Throwable throwable);
  }
}
