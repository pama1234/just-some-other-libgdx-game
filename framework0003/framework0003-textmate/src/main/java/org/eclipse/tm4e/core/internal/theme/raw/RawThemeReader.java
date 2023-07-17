package org.eclipse.tm4e.core.internal.theme.raw;

import org.eclipse.tm4e.core.internal.parser.PListParser;
import org.eclipse.tm4e.core.internal.parser.PListParserJSON;
import org.eclipse.tm4e.core.internal.parser.PListParserXML;
import org.eclipse.tm4e.core.internal.parser.PListParserYAML;
import org.eclipse.tm4e.core.internal.parser.PListPath;
import org.eclipse.tm4e.core.internal.parser.PropertySettable;
import org.eclipse.tm4e.core.registry.IThemeSource;

public final class RawThemeReader{
  private static final PropertySettable.Factory<PListPath> OBJECT_FACTORY=path->new RawTheme();
  private static final PListParser<RawTheme> JSON_PARSER=new PListParserJSON<>(OBJECT_FACTORY);
  private static final PListParser<RawTheme> XML_PARSER=new PListParserXML<>(OBJECT_FACTORY);
  private static final PListParser<RawTheme> YAML_PARSER=new PListParserYAML<>(OBJECT_FACTORY);
  public static IRawTheme readTheme(final IThemeSource source) throws Exception {
    try(var reader=source.getReader()) {
      switch(source.getContentType()) {
        case JSON:
          return JSON_PARSER.parse(reader);
        case YAML:
          return YAML_PARSER.parse(reader);
        case XML:
        default:
          return XML_PARSER.parse(reader);
      }
    }
  }
  private RawThemeReader() {}
}
