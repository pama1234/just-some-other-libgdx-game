package org.eclipse.tm4e.core.model;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.utils.StringUtils;

public final class Range{
  public final int fromLineNumber;
  public int toLineNumber;
  public Range(final int lineNumber) {
    this.fromLineNumber=lineNumber;
    this.toLineNumber=lineNumber;
  }
  public Range(final int fromLineNumber,final int toLineNumber) {
    this.fromLineNumber=fromLineNumber;
    this.toLineNumber=toLineNumber;
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final Range other) return fromLineNumber==other.fromLineNumber
      &&toLineNumber==other.toLineNumber;
    return false;
  }
  @Override
  public int hashCode() {
    final int result=31+fromLineNumber;
    return 31*result+toLineNumber;
  }
  @Override
  public String toString() {
    return StringUtils.toString(this,sb->sb
      .append("from=").append(fromLineNumber).append(", ")
      .append("to=").append(toLineNumber));
  }
}
