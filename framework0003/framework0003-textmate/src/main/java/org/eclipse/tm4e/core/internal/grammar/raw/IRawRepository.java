package org.eclipse.tm4e.core.internal.grammar.raw;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.parser.PropertySettable;

public interface IRawRepository{
  static IRawRepository merge(@Nullable final IRawRepository... sources) {
    final var merged=new RawRepository();
    for(final var source:sources) {
      if(source==null) continue;
      source.putEntries(merged);
    }
    return merged;
  }
  void putEntries(PropertySettable<IRawRule> target);
  @Nullable
  IRawRule getRule(String name);
  IRawRule getBase();
  IRawRule getSelf();
  void setSelf(IRawRule raw);
  void setBase(IRawRule base);
}
