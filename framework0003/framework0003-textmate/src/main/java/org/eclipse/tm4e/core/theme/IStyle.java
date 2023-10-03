package org.eclipse.tm4e.core.theme;

import org.eclipse.jdt.annotation.Nullable;

public interface IStyle{
  @Nullable
  RGB getColor();
  @Nullable
  RGB getBackgroundColor();
  boolean isBold();
  boolean isItalic();
  boolean isUnderline();
  boolean isStrikeThrough();
}
