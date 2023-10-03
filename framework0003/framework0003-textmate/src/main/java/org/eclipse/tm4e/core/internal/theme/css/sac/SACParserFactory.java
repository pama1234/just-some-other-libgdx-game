package org.eclipse.tm4e.core.internal.theme.css.sac;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.w3c.css.sac.Parser;

public final class SACParserFactory extends AbstractSACParserFactory{
  private static Map<String,@Nullable String> parsers=new HashMap<>();
  static {
    // Register Flute SAC Parser
    registerSACParser(SACConstants.SACPARSER_FLUTE);
    // Register Flute SAC CSS3Parser
    registerSACParser(SACConstants.SACPARSER_FLUTE_CSS3);
    // Register SteadyState SAC Parser
    registerSACParser(SACConstants.SACPARSER_STEADYSTATE);
    // Register Batik SAC Parser
    registerSACParser(SACConstants.SACPARSER_BATIK);
  }
  public SACParserFactory() {
    // Flute parser is the default SAC Parser to use.
    super.setPreferredParserName(SACConstants.SACPARSER_BATIK);
  }
  @Override
  public Parser makeParser(final String name) throws ClassNotFoundException,IllegalAccessException,
    InstantiationException,ClassCastException {
    final String classNameParser=parsers.get(name);
    if(classNameParser!=null) {
      final Class<?> classParser=super.getClass().getClassLoader().loadClass(classNameParser);
      try {
        final var parser=(Parser)classParser.getDeclaredConstructor().newInstance();
        assert parser!=null;
        return parser;
      }catch(InvocationTargetException|NoSuchMethodException ex) {
        throw (InstantiationException)new InstantiationException().initCause(ex);
      }
    }
    throw new IllegalAccessException(
      "SAC parser with name="+name+" was not registered into SAC parser factory.");
  }
  private static void registerSACParser(final String parser) {
    registerSACParser(parser,parser);
  }
  private static void registerSACParser(final String name,final String classNameParser) {
    parsers.put(name,classNameParser);
  }
}
