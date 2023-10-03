package org.eclipse.tm4e.core.internal.rule;

import org.eclipse.jdt.annotation.Nullable;

public final class RuleId{
  public static final RuleId NO_RULE=new RuleId(0);
  public static final RuleId END_RULE=new RuleId(-1);
  public static final RuleId WHILE_RULE=new RuleId(-2);
  public static RuleId of(final int id) {
    if(id<0) throw new IllegalArgumentException("[id] must be > 0");
    return new RuleId(id);
  }
  public final int id;
  private RuleId(final int id) {
    this.id=id;
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final RuleId other) return id==other.id;
    return false;
  }
  public boolean equals(final RuleId otherRule) {
    return id==otherRule.id;
  }
  public boolean notEquals(final RuleId otherRule) {
    return id!=otherRule.id;
  }
  @Override
  public int hashCode() {
    return id;
  }
  @Override
  public String toString() {
    return Integer.toString(id);
  }
}
