package org.eclipse.tm4e.core.internal.grammar.raw;

import java.util.HashMap;

import org.eclipse.tm4e.core.internal.parser.PropertySettable;

public final class RawCaptures extends HashMap<String,IRawRule> implements IRawCaptures,PropertySettable<IRawRule>{
  private static final long serialVersionUID=1L;
  @Override
  public IRawRule getCapture(final String captureId) {
    return get(captureId);
  }
  @Override
  public Iterable<String> getCaptureIds() {
    return keySet();
  }
  @Override
  public void setProperty(final String name,final IRawRule value) {
    put(name,value);
  }
}
