package org.eclipse.tm4e.core.internal.theme;

import org.eclipse.jdt.annotation.Nullable;

public class StyleAttributes{
  private static final StyleAttributes NO_STYLE=new StyleAttributes(-1,0,0);
  public final int fontStyle;
  public final int foregroundId;
  public final int backgroundId;
  public static StyleAttributes of(final int fontStyle,final int foregroundId,final int backgroundId) {
    if(fontStyle==-1&&foregroundId==0&&backgroundId==0) {
      return NO_STYLE;
    }
    return new StyleAttributes(fontStyle,foregroundId,backgroundId);
  }
  private StyleAttributes(final int fontStyle,final int foregroundId,final int backgroundId) {
    this.fontStyle=fontStyle;
    this.foregroundId=foregroundId;
    this.backgroundId=backgroundId;
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final StyleAttributes other) return backgroundId==other.backgroundId
      &&fontStyle==other.fontStyle
      &&foregroundId==other.foregroundId;
    return false;
  }
  @Override
  public int hashCode() {
    int result=31+backgroundId;
    result=31*result+fontStyle;
    return 31*result+foregroundId;
  }
}
