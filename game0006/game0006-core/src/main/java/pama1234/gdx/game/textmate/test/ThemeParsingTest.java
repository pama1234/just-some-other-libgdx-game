package pama1234.gdx.game.textmate.test;

import static org.eclipse.tm4e.core.internal.theme.FontStyle.Bold;
import static org.eclipse.tm4e.core.internal.theme.FontStyle.Italic;
import static org.eclipse.tm4e.core.internal.theme.FontStyle.None;
import static org.eclipse.tm4e.core.internal.theme.FontStyle.NotSet;
import static org.eclipse.tm4e.core.internal.theme.FontStyle.Strikethrough;
import static org.eclipse.tm4e.core.internal.theme.FontStyle.Underline;

import java.util.List;

import org.eclipse.tm4e.core.internal.theme.ParsedThemeRule;

/**
 * @see <a href=
 *      "https://github.com/Microsoft/vscode-textmate/blob/e8d1fc5d04b2fc91384c7a895f6c9ff296a38ac8/src/tests/themes.test.ts#L286">
 *      github.com/Microsoft/vscode-textmate/blob/master/src/tests/themes.test.ts</a>
 */
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ThemeParsingTest extends AbstractThemeTest{
  // @Test
  // @Order(1)
  // @DisplayName("Theme parsing can parse")
  public static void testCanParse() throws Exception {
    final List<ParsedThemeRule> actual=parseTheme("""
      { "settings": [
      { "settings": { "foreground": "#F8F8F2", "background": "#272822" } },
      { "scope": "source, something", "settings": { "background": "#100000" } },
      { "scope": ["bar", "baz"], "settings": { "background": "#010000" } },
      { "scope": "source.css selector bar", "settings": { "fontStyle": "bold" } },
      { "scope": "constant", "settings": { "fontStyle": "italic", "foreground": "#ff0000" } },
      { "scope": "constant.numeric", "settings": { "foreground": "#00ff00" } },
      { "scope": "constant.numeric.hex", "settings": { "fontStyle": "bold" } },
      { "scope": "constant.numeric.oct", "settings": { "fontStyle": "bold italic underline" } },
      { "scope": "constant.numeric.bin", "settings": { "fontStyle": "bold strikethrough" } },
      { "scope": "constant.numeric.dec", "settings": { "fontStyle": "", "foreground": "#0000ff" } },
      { "scope": "foo", "settings": { "fontStyle": "", "foreground": "#CFA" } }
      ]}""");
    final ParsedThemeRule[] expected=new ParsedThemeRule[] {
      new ParsedThemeRule("",null,0,NotSet,"#F8F8F2","#272822"),
      new ParsedThemeRule("source",null,1,NotSet,null,"#100000"),
      new ParsedThemeRule("something",null,1,NotSet,null,"#100000"),
      new ParsedThemeRule("bar",null,2,NotSet,null,"#010000"),
      new ParsedThemeRule("baz",null,2,NotSet,null,"#010000"),
      new ParsedThemeRule("bar",list("selector","source.css"),3,Bold,null,null),
      new ParsedThemeRule("constant",null,4,Italic,"#ff0000",null),
      new ParsedThemeRule("constant.numeric",null,5,NotSet,"#00ff00",null),
      new ParsedThemeRule("constant.numeric.hex",null,6,Bold,null,null),
      new ParsedThemeRule("constant.numeric.oct",null,7,Bold|Italic|Underline,null,null),
      new ParsedThemeRule("constant.numeric.bin",null,8,Bold|Strikethrough,null,null),
      new ParsedThemeRule("constant.numeric.dec",null,9,None,"#0000ff",null),
      new ParsedThemeRule("foo",null,10,None,"#CFA",null),
    };
    // assertArrayEquals(expected,actual.toArray());
    for(ParsedThemeRule i:expected) System.out.println(i);
    System.out.println();
    for(ParsedThemeRule i:actual) System.out.println(i);
  }
}
