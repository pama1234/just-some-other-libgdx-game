package org.eclipse.tm4e.ui.themes.css;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.jface.text.TextAttribute;
// import org.eclipse.jface.text.rules.IToken;
// import org.eclipse.jface.text.rules.Token;
import org.eclipse.tm4e.core.internal.utils.StringUtils;
import org.eclipse.tm4e.core.theme.IStyle;
import org.eclipse.tm4e.core.theme.RGB;
import org.eclipse.tm4e.core.theme.css.CSSParser;
// import org.eclipse.tm4e.ui.TMUIPlugin;
import org.eclipse.tm4e.ui.themes.ColorManager;
import org.eclipse.tm4e.ui.themes.ITokenProvider;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.textmate.IToken;
// import pama1234.gdx.textmate.EclipseTools;
import pama1234.gdx.textmate.StyleConst;
import pama1234.gdx.textmate.TextAttribute;
import pama1234.gdx.textmate.Token;
import pama1234.gdx.textmate.annotation.DeprecatedJface;

@DeprecatedJface
public class CSSTokenProvider implements ITokenProvider{
  private final Map<IStyle,@Nullable IToken> tokenMaps=new HashMap<>();
  private final Map<String,IToken> getTokenReturnValueCache=new ConcurrentHashMap<>();
  private final CSSParser parser;
  public CSSTokenProvider(final InputStream in) {
    CSSParser parser=null;
    try {
      parser=new CSSParser(in);
      for(final IStyle style:parser.getStyles()) {
        // System.out.println(style);
        final RGB color=style.getColor();
        if(color!=null) {
          int s=StyleConst.NORMAL;
          if(style.isBold()) {
            s=s|StyleConst.BOLD;
          }
          if(style.isItalic()) {
            s=s|StyleConst.ITALIC;
          }
          if(style.isUnderline()) {
            s=s|TextAttribute.UNDERLINE;
          }
          if(style.isStrikeThrough()) {
            s=s|TextAttribute.STRIKETHROUGH;
          }
          tokenMaps.put(style,
            // new Token(new TextAttribute(EclipseTools.eColor(ColorManager.getInstance().getColor(color)),null,s)));
            new Token(new TextAttribute(ColorManager.getInstance().getColor(color),null,s)));
        }
      }
    }catch(final Exception ex) {
      // TMUIPlugin.logError(ex);
      // System.out.println("12352341");
      // System.err.println(ex);
      ex.printStackTrace();
    }
    this.parser=parser==null?new NoopCSSParser():parser;
  }
  @Override
  public IToken getToken(@Nullable final String type) {
    if(type==null||type.isEmpty()) return DEFAULT_TOKEN;
    return getTokenReturnValueCache.computeIfAbsent(type,this::getTokenInternal);
  }
  private IToken getTokenInternal(final String type) {
    final IStyle style=parser.getBestStyle(StringUtils.splitToArray(type,'.'));
    if(style==null) return DEFAULT_TOKEN;
    final IToken token=tokenMaps.get(style);
    return token==null?DEFAULT_TOKEN:token;
  }
  @Nullable
  @Override
  public Color getColor(final boolean isForeground,final String... styles) {
    final IStyle style=parser.getBestStyle(styles);
    // System.out.println(style);
    if(style==null) return null;
    final RGB rgb=isForeground?style.getColor():style.getBackgroundColor();
    if(rgb==null) return null;
    return ColorManager.getInstance().getColor(rgb);
  }
  @Nullable
  @Override
  public Color getEditorForeground() {
    return getColor(true,"editor");
  }
  @Nullable
  @Override
  public Color getEditorBackground() {
    return getColor(false,"editor");
  }
  @Nullable
  @Override
  public Color getEditorSelectionForeground() {
    return getColor(true,"editor","selection");
  }
  @Nullable
  @Override
  public Color getEditorSelectionBackground() {
    return getColor(false,"editor","selection");
  }
  @Nullable
  @Override
  public Color getEditorCurrentLineHighlight() {
    return getColor(false,"editor","lineHighlight");
  }
  private static class NoopCSSParser extends CSSParser{
    @Override
    public List<IStyle> getStyles() {
      return Collections.emptyList();
    }
    @Override
    public @Nullable IStyle getBestStyle(String... names) {
      return null;
    }
  }
}
