package org.eclipse.tm4e.core.internal.theme.css.sac;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.css.sac.Parser;

public interface ISACParserFactory{
  @Nullable
  String getPreferredParserName();
  void setPreferredParserName(@Nullable String preferredParserName);
  Parser makeParser()
    throws ClassNotFoundException,IllegalAccessException,InstantiationException,ClassCastException;
  Parser makeParser(String name)
    throws ClassNotFoundException,IllegalAccessException,InstantiationException,ClassCastException;
}
