package org.eclipse.tm4e.core.theme.css;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.theme.css.CSSConditionFactory;
import org.eclipse.tm4e.core.internal.theme.css.CSSDocumentHandler;
import org.eclipse.tm4e.core.internal.theme.css.CSSSelectorFactory;
import org.eclipse.tm4e.core.internal.theme.css.CSSStyle;
import org.eclipse.tm4e.core.internal.theme.css.ExtendedSelector;
import org.eclipse.tm4e.core.internal.theme.css.sac.SACParserFactory;
import org.eclipse.tm4e.core.theme.IStyle;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;
import org.w3c.css.sac.SelectorList;

public class CSSParser{
  protected final CSSDocumentHandler handler=new CSSDocumentHandler();
  protected CSSParser() {}
  public CSSParser(final InputStream source) throws Exception {
    this(toSource(source));
  }
  private static InputSource toSource(final InputStream source) {
    final var in=new InputSource();
    in.setByteStream(source);
    return in;
  }
  public CSSParser(final InputSource source) throws Exception {
    this(source,new SACParserFactory().makeParser());
  }
  public CSSParser(final String source) throws Exception {
    this(new InputSource(new StringReader(source)));
  }
  public CSSParser(final InputSource source,final Parser parser) throws CSSException,IOException {
    // org.apache.batik.css.parser.Parser.class.getName();
    // org.apache.batik.css.parser.Parser parserd=(org.apache.batik.css.parser.Parser)parser;
    // parserd.setDocumentHandler(handler);
    // parserd.setConditionFactory(CSSConditionFactory.INSTANCE);
    // parserd.setSelectorFactory(CSSSelectorFactory.INSTANCE);
    // parserd.parseStyleSheet(source);
    parser.setDocumentHandler(handler);
    parser.setConditionFactory(CSSConditionFactory.INSTANCE);
    parser.setSelectorFactory(CSSSelectorFactory.INSTANCE);
    parser.parseStyleSheet(source);
  }
  @Nullable
  public IStyle getBestStyle(final String... names) {
    int bestSpecificity=0;
    IStyle bestStyle=null;
    // System.out.println("456343312");
    // System.out.println(handler.getList()==null?"null":handler.getList().size());
    for(final IStyle style:handler.getList()) {
      final SelectorList list=((CSSStyle)style).getSelectorList();
      for(int i=0,l=list.getLength();i<l;i++) {
        final ExtendedSelector selector=(ExtendedSelector)list.item(i);
        final int nbMatch=selector.nbMatch(names);
        if((nbMatch>=bestSpecificity||bestStyle==null)
          &&nbMatch>0&&nbMatch==selector.nbClass()) {
          bestStyle=style;
          bestSpecificity=nbMatch;
        }
      }
    }
    return bestStyle;
  }
  public List<IStyle> getStyles() {
    return handler.getList();
  }
}
