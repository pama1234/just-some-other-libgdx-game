package org.eclipse.tm4e.core.internal.parser;

import java.io.Reader;

public interface PListParser<T>{
  T parse(Reader contents) throws Exception;
}
