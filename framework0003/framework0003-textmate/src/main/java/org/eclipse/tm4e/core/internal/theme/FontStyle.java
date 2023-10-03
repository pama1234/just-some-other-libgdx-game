package org.eclipse.tm4e.core.internal.theme;

public final class FontStyle{
  public static final int NotSet=-1;
  // This can are bit-flags, so it can be `Italic | Bold`
  public static final int None=0;
  public static final int Italic=1;
  public static final int Bold=2;
  public static final int Underline=4;
  public static final int Strikethrough=8;
  public static String fontStyleToString(final int fontStyle) {
    if(fontStyle==NotSet) {
      return "not set";
    }
    if(fontStyle==None) {
      return "none";
    }
    final var style=new StringBuilder();
    if((fontStyle&Italic)==Italic) {
      style.append("italic ");
    }
    if((fontStyle&Bold)==Bold) {
      style.append("bold ");
    }
    if((fontStyle&Underline)==Underline) {
      style.append("underline ");
    }
    if((fontStyle&Strikethrough)==Strikethrough) {
      style.append("strikethrough ");
    }
    if(style.isEmpty()) {
      return "none";
    }
    style.setLength(style.length()-1);
    return style.toString();
  }
  private FontStyle() {}
}
