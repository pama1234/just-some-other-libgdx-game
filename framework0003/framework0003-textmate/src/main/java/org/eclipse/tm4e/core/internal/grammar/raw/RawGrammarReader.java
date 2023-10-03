package org.eclipse.tm4e.core.internal.grammar.raw;

import org.eclipse.tm4e.core.internal.parser.PListParser;
import org.eclipse.tm4e.core.internal.parser.PListParserJSON;
import org.eclipse.tm4e.core.internal.parser.PListParserXML;
import org.eclipse.tm4e.core.internal.parser.PListParserYAML;
import org.eclipse.tm4e.core.internal.parser.PListPath;
import org.eclipse.tm4e.core.internal.parser.PropertySettable;
import org.eclipse.tm4e.core.registry.IGrammarSource;

public final class RawGrammarReader{
  public static final PropertySettable.Factory<PListPath> OBJECT_FACTORY=path-> {
    if(path.size()==0) {
      return new RawGrammar();
    }
    return switch(path.last()) {
      case RawRule.REPOSITORY->new RawRepository();
      case RawRule.BEGIN_CAPTURES,RawRule.CAPTURES,RawRule.END_CAPTURES,RawRule.WHILE_CAPTURES->new RawCaptures();
      default->new RawRule();
    };
  };
  private static final PListParser<RawGrammar> JSON_PARSER=new PListParserJSON<>(OBJECT_FACTORY);
  private static final PListParser<RawGrammar> XML_PARSER=new PListParserXML<>(OBJECT_FACTORY);
  private static final PListParser<RawGrammar> YAML_PARSER=new PListParserYAML<>(OBJECT_FACTORY);
  public static IRawGrammar readGrammar(final IGrammarSource source) throws Exception {
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
  private RawGrammarReader() {}
}
