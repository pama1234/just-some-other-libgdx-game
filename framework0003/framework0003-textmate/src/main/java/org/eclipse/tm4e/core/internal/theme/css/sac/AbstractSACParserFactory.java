package org.eclipse.tm4e.core.internal.theme.css.sac;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.css.sac.Parser;
import org.w3c.css.sac.helpers.ParserFactory;

public abstract class AbstractSACParserFactory extends ParserFactory implements ISACParserFactory{
  // private static final Logger LOGGER=System.getLogger(AbstractSACParserFactory.class.getName());
  @Nullable
  private String preferredParserName;
  @Override
  public Parser makeParser() throws ClassNotFoundException,IllegalAccessException,InstantiationException,
    NullPointerException,ClassCastException {
    try {
      if(preferredParserName!=null) {
        return makeParser(preferredParserName);
      }
    }catch(final Exception|LinkageError ex) {
      // System.out.println(ERROR,ex.getMessage(),ex);
      System.err.println(ex.getMessage()+" "+ex);
    }
    return super.makeParser();
  }
  @Nullable
  @Override
  public String getPreferredParserName() {
    return preferredParserName;
  }
  @Override
  public void setPreferredParserName(@Nullable final String preferredParserName) {
    this.preferredParserName=preferredParserName;
  }
}
