package org.eclipse.tm4e.core.internal.grammar.raw;

public interface IRawCaptures{
  IRawRule getCapture(String captureId);
  Iterable<String> getCaptureIds();
}
