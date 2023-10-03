package pama1234.shift.rosemoe.lang.styling;

public class TextStyle{
  public final static int COLOR_ID_BIT_COUNT=19;
  public final static long FOREGROUND_BITS=((1<<(COLOR_ID_BIT_COUNT))-1);
  public final static long BACKGROUND_BITS=FOREGROUND_BITS<<COLOR_ID_BIT_COUNT;
  public final static long BOLD_BIT=1L<<(COLOR_ID_BIT_COUNT*2);
  public final static long ITALICS_BIT=BOLD_BIT<<1;
  public final static long STRIKETHROUGH_BIT=ITALICS_BIT<<1;
  public final static long NO_COMPLETION_BIT=STRIKETHROUGH_BIT<<1;
  public static long makeStyle(int foregroundColorId) {
    checkColorId(foregroundColorId);
    return foregroundColorId;
  }
  public static long makeStyle(int foregroundColorId,boolean noCompletion) {
    checkColorId(foregroundColorId);
    return ((long)foregroundColorId)|(noCompletion?NO_COMPLETION_BIT:0);
  }
  public static long makeStyle(int foregroundColorId,int backgroundColorId,boolean bold,
    boolean italic,boolean strikeThrough) {
    return makeStyle(foregroundColorId,backgroundColorId,bold,italic,strikeThrough,false);
  }
  public static long makeStyle(int foregroundColorId,int backgroundColorId,boolean bold,
    boolean italic,boolean strikeThrough,boolean noCompletion) {
    checkColorId(foregroundColorId);
    checkColorId(backgroundColorId);
    return ((long)foregroundColorId)+
      (((long)backgroundColorId)<<COLOR_ID_BIT_COUNT)|(bold?BOLD_BIT:0)|(italic?ITALICS_BIT:0)|(strikeThrough?STRIKETHROUGH_BIT:0)|(noCompletion?NO_COMPLETION_BIT:0);
  }
  public static int getForegroundColorId(long style) {
    return (int)(style&FOREGROUND_BITS);
  }
  public static int getBackgroundColorId(long style) {
    return (int)((style&BACKGROUND_BITS)>>COLOR_ID_BIT_COUNT);
  }
  public static boolean isBold(long style) {
    return (style&BOLD_BIT)!=0;
  }
  public static boolean isItalics(long style) {
    return (style&ITALICS_BIT)!=0;
  }
  public static boolean isStrikeThrough(long style) {
    return (style&STRIKETHROUGH_BIT)!=0;
  }
  public static boolean isNoCompletion(long style) {
    return (style&NO_COMPLETION_BIT)!=0;
  }
  public static long getStyleBits(long style) {
    return style&(BOLD_BIT+ITALICS_BIT+STRIKETHROUGH_BIT);
  }
  public static void checkColorId(int colorId) {
    if(colorId>(1<<COLOR_ID_BIT_COUNT)-1||colorId<0) {
      throw new IllegalArgumentException("color id must be positive and bit count is less than "+COLOR_ID_BIT_COUNT);
    }
  }
}
