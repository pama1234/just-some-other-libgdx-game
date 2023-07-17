package org.eclipse.tm4e.core.internal.parser;

public interface PListPath extends Iterable<String>{
  String first();
  String get(int index);
  String last();
  int size();
}
