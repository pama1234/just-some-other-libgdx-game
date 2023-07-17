package pama1234.gdx.game.textmate.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.tm4e.core.internal.theme.ParsedThemeRule;
import org.eclipse.tm4e.core.internal.theme.TextMateTheme;
import org.eclipse.tm4e.core.internal.theme.raw.RawThemeReader;
import org.eclipse.tm4e.core.registry.IThemeSource;

public class AbstractThemeTest{
  protected static final int _NOT_SET=0;
  protected static <T> List<T> list(@SuppressWarnings("unchecked") final T... items) {
    if(items.length==0) return Collections.emptyList();
    return Arrays.asList(items);
  }
  @SuppressWarnings("unchecked")
  protected static <K,V> Map<K,V> map(final K k,final V v,final Object... moreKVs) {
    final var map=new HashMap<K,V>();
    map.put(k,v);
    if(moreKVs.length==0) return map;
    boolean nextIsValue=false;
    K key=null;
    for(final Object obj:moreKVs) if(nextIsValue) {
      map.put(key,(V)obj);
      nextIsValue=false;
    }else {
      key=(K)obj;
      nextIsValue=true;
    }
    return map;
  }
  protected static TextMateTheme createTheme(final ParsedThemeRule... rules) {
    return TextMateTheme.createFromParsedTheme(list(rules),null);
  }
  protected static TextMateTheme createTheme(final String themeAsJsonString) throws Exception {
    return TextMateTheme.createFromRawTheme(RawThemeReader.readTheme(
      IThemeSource.fromString(IThemeSource.ContentType.JSON,themeAsJsonString)),
      null);
  }
  protected static List<ParsedThemeRule> parseTheme(final String themeAsJsonString) throws Exception {
    return TextMateTheme.parseTheme(RawThemeReader.readTheme(
      IThemeSource.fromString(IThemeSource.ContentType.JSON,themeAsJsonString)));
  }
}
