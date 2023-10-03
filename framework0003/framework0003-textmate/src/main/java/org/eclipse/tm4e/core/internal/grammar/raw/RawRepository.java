package org.eclipse.tm4e.core.internal.grammar.raw;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.parser.PropertySettable;

public final class RawRepository extends HashMap<String,IRawRule> implements IRawRepository,PropertySettable<IRawRule>{
  private static final long serialVersionUID=1L;
  public static final String DOLLAR_BASE="$base";
  public static final String DOLLAR_SELF="$self";
  @SuppressWarnings({"null","unused"})
  private IRawRule getSafe(final String key) {
    final IRawRule obj=get(key);
    if(obj==null) {
      throw new NoSuchElementException("Key '"+key+"' does not exit found");
    }
    return obj;
  }
  @Override
  @Nullable
  public IRawRule getRule(final String name) {
    return get(name);
  }
  @Override
  public IRawRule getBase() {
    return getSafe(DOLLAR_BASE);
  }
  @Override
  public void setBase(final IRawRule base) {
    super.put(DOLLAR_BASE,base);
  }
  @Override
  public IRawRule getSelf() {
    return getSafe(DOLLAR_SELF);
  }
  @Override
  public void setSelf(final IRawRule self) {
    super.put(DOLLAR_SELF,self);
  }
  @Override
  public void putEntries(final PropertySettable<IRawRule> target) {
    for(final var entry:entrySet()) {
      target.setProperty(entry.getKey(),entry.getValue());
    }
  }
  @Override
  public void setProperty(final String name,final IRawRule value) {
    put(name,value);
  }
}
