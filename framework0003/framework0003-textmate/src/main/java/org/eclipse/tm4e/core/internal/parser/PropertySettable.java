package org.eclipse.tm4e.core.internal.parser;

public interface PropertySettable<V>{
  interface Factory<I>{
    PropertySettable<?> create(I args);
  }
  void setProperty(String name,V value);
}
