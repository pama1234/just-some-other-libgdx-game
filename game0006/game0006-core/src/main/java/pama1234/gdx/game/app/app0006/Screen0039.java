package pama1234.gdx.game.app.app0006;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.grammar.IToken;
import org.eclipse.tm4e.core.grammar.ITokenizeLineResult;
import org.eclipse.tm4e.core.internal.grammar.ScopeStack;
import org.eclipse.tm4e.core.internal.theme.StyleAttributes;
import org.eclipse.tm4e.core.internal.theme.TextMateTheme;
import org.eclipse.tm4e.core.internal.theme.raw.RawThemeReader;
import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IGrammarSource.ContentType;
import org.eclipse.tm4e.core.registry.IThemeSource;
import org.eclipse.tm4e.core.registry.Registry;
import org.eclipse.tm4e.ui.internal.themes.AbstractThemeManager;
import org.eclipse.tm4e.ui.themes.IThemeManager;
import org.eclipse.tm4e.ui.themes.Theme;
import org.osgi.service.prefs.BackingStoreException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.textmate.test.MatcherTest;
import pama1234.gdx.game.textmate.test.ThemeManagerTest;
import pama1234.gdx.game.textmate.test.ThemeParsingTest;
import pama1234.gdx.game.textmate.test.TokenizeLineTest;
import pama1234.gdx.textmate.GdxTheme;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.cam.CameraController2D;
import pama1234.gdx.util.font.TextStyleBase;

public class Screen0039 extends ScreenCore2D{
  static {}
  private static final class MockThemeManager extends AbstractThemeManager{
    @Override
    public void save() throws BackingStoreException {}
  }
  public IThemeManager manager;
  public TextMateTheme textMateTheme;
  public Theme theme;
  public FileHandle fileHandle;
  public File file;
  public int fileAvailable;
  public Registry registry;
  public IGrammar grammarJava;
  public String javaText;
  public TextMateTextStyle textStyle;
  @Override
  public void setup() {
    cam2d.pixelPerfect=CameraController2D.SMOOTH;
    javaText="/** javaDoc */ public static void main(String[] args) { System.out.println(\"Hello World!\"); }";
    test_1();
    String textMateSrc=Gdx.files.internal("themes/theme-light-tm4e.json").readString();
    textMateTheme=loadTextMateTheme(textMateSrc);
    fileHandle=Gdx.files.internal("themes/pama1234-light.css");
    file=FileUtil.assetToFile(fileHandle);
    fileHandle.read();
    test_4();
    textStyle=new TextMateTextStyle(grammarJava,theme,textMateTheme);
    System.out.println(textMateTheme.match(ScopeStack.from("comment")).backgroundId);
  }
  public void test_4() {
    manager=new MockThemeManager();
    theme=new GdxTheme(
      "pama1234.themes.ProcessingLight",fileHandle,
      "ProcessingLight",false,true);
    manager.registerTheme(theme);
    if(theme.getEditorBackground()!=null) {
      backgroundColor(theme.getEditorBackground());
      textColor(theme.getEditorForeground());
    }
    try {
      fileAvailable=fileHandle.read().available();
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  public void test_1() {
    registry=new Registry();
    grammarJava=registry.addGrammar(
      IGrammarSource.fromString(ContentType.JSON,Gdx.files.internal("themes/java.json").readString()));
  }
  public void test_2() {
    try {
      MatcherTest.matcherTests();
      System.out.println();
      ThemeParsingTest.testCanParse();
      System.out.println();
      TokenizeLineTest.testTokenizeLine();
      TokenizeLineTest.testTokenizeLine2();
      TokenizeLineTest.testTokenizeMultiByteLine();
      TokenizeLineTest.testTokenizeMultiByteLine2();
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void test_3() {
    ThemeManagerTest.setup();
    ThemeManagerTest.testGetAllThemes();
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    textStyle(textStyle);
    text(javaText,0,-20);
    textStyle(null);
    if(file!=null) text(file+" "+file.exists()+" "+fileHandle.exists()+" "+fileAvailable);
    if(theme!=null) text(theme.getName()+" "+theme.getClass().getName()+" "+theme.getEditorBackground(),0,20);
  }
  @Override
  public void frameResized() {}
  public static class TextMateTextStyle extends TextStyleBase{
    public Color defaultBackground=color(255),defaultForeground=color(0);
    public HashMap<String,ITokenizeLineResult<IToken[]>> result=new HashMap<>();
    public ITokenizeLineResult<IToken[]> current;
    public IToken[] tokens;
    public int tokenPointer;
    public IGrammar grammar;
    public Theme theme;
    public TextMateTheme textMateTheme;
    public TextMateTextStyle(IGrammar grammar,Theme theme,TextMateTheme textMateTheme) {
      this.grammar=grammar;
      this.theme=theme;
      this.textMateTheme=textMateTheme;
    }
    public Color fromTextMateBackground(int i) {
      if(current!=null) {
        if(i>=tokens[tokenPointer].getEndIndex()) tokenPointer++;
        IToken iToken=tokens[tokenPointer];
        if(i<iToken.getStartIndex()) return null;
        List<String> list=iToken.getScopes();
        @Nullable
        StyleAttributes match=textMateTheme.match(ScopeStack.from(list));
        if(match.backgroundId!=0) {
          return Color.valueOf(textMateTheme.getColorMap().get(match.backgroundId));
        }
      }
      return null;
    }
    public Color fromTextMateForeground(int i) {
      if(current!=null) {
        IToken iToken=tokens[tokenPointer];
        if(i<iToken.getStartIndex()) return null;
        List<String> list=iToken.getScopes();
        StyleAttributes match=textMateTheme.match(ScopeStack.from(list));
        if(match.foregroundId!=0) {
          return Color.valueOf(textMateTheme.getColorMap().get(match.foregroundId));
        }
      }
      return null;
    }
    @Override
    public Color background(int x,int y,int i) {
      Color color=fromTextMateBackground(i);
      if(color!=null) return color;
      return defaultBackground;
    }
    @Override
    public Color foreground(int x,int y,int i) {
      return defaultForeground;
    }
    public Color fromThemeBackground(int i) {
      if(current!=null) {
        IToken iToken=tokens[tokenPointer];
        if(i>=iToken.getEndIndex()) {
          tokenPointer++;
          List<String> list=iToken.getScopes();
          for(String string:list) {
            @Nullable
            Color color=theme.getColor(false,string.split("."));
            if(color!=null) return color;
          }
        }
      }
      return null;
    }
    public Color fromThemeForeground() {
      if(current!=null) {
        IToken iToken=tokens[tokenPointer];
        List<String> list=iToken.getScopes();
        for(String string:list) {
          @Nullable
          Color color=theme.getColor(true,string.split("."));
          if(color!=null) return color;
        }
      }
      return null;
    }
    @Override
    public int style(int x,int y) {
      return 0;
    }
    @Override
    public void text(String in) {
      super.text(in);
      current=result.get(in);
      if(current==null) {
        result.put(in,current=grammar.tokenizeLine(in));
        for(IToken i:current.getTokens()) {
          System.out.println(i.getScopes());
        }
      }
      tokens=current.getTokens();
      tokenPointer=0;
    }
  }
  public TextMateTheme loadTextMateTheme(String themeAsJsonString) {
    try {
      IThemeSource themeSrc=IThemeSource.fromString(IThemeSource.ContentType.JSON,themeAsJsonString);
      return TextMateTheme.createFromRawTheme(RawThemeReader.readTheme(
        themeSrc),
        null);
    }catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
