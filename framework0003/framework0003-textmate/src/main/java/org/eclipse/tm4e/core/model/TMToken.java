package org.eclipse.tm4e.core.model;

import org.eclipse.jdt.annotation.Nullable;

public final class TMToken{
  public final int startIndex;
  public final String type;
  public TMToken(final int startIndex,final String type) {
    this.startIndex=startIndex;
    this.type=type;
  }
  @Override
  public boolean equals(final @Nullable Object obj) {
    if(this==obj) return true;
    if(obj instanceof final TMToken other) {
      return startIndex==other.startIndex
        &&type.equals(other.type);
    }
    return false;
  }
  @Override
  public int hashCode() {
    return 31*(31+startIndex)+type.hashCode();
  }
  @Override
  public String toString() {
    return "("+startIndex+", "+(type.isEmpty()?"<empty>":type)+")";
  }
}
