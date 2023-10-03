package pama1234.gdx.game.textmate.test;

import org.eclipse.tm4e.ui.internal.themes.AbstractThemeManager;
import org.eclipse.tm4e.ui.themes.ITheme;
import org.eclipse.tm4e.ui.themes.IThemeManager;
import org.eclipse.tm4e.ui.themes.Theme;
import org.osgi.service.prefs.BackingStoreException;

import pama1234.gdx.util.FileUtil;

public class ThemeManagerTest{
  private static final String Light="org.eclipse.tm4e.ui.themes.Light";
  private static final String Monokai="org.eclipse.tm4e.ui.themes.Monokai";
  private static final String SolarizedLight="org.eclipse.tm4e.ui.themes.SolarizedLight";
  private static final String Dark="org.eclipse.tm4e.ui.themes.Dark";
  private static final class MockThemeManager extends AbstractThemeManager{
    @Override
    public void save() throws BackingStoreException {}
  }
  private static IThemeManager manager;
  // @BeforeEach
  public static void setup() {
    manager=new MockThemeManager();
    // Register theme
    manager.registerTheme(new Theme(SolarizedLight,FileUtil.assetToFile("themes/SolarizedLight.css").toString(),"SolarizedLight",false,true));
    manager.registerTheme(new Theme(Light,FileUtil.assetToFile("themes/Light.css").toString(),"Light",false,false));
    manager.registerTheme(new Theme(Dark,FileUtil.assetToFile("themes/Dark.css").toString(),"Dark",true,true));
    manager.registerTheme(new Theme(Monokai,FileUtil.assetToFile("themes/Monokai.css").toString(),"Monokai",true,false));
  }
  // @Test
  public static void testGetAllThemes() {
    final ITheme[] themes=manager.getThemes();
    for(ITheme i:themes) System.out.println(i.getName()+" "+i.getEditorBackground());
    System.out.println(4+" "+themes.length);
  }
  // @Test
  public static void testDefaultThemeAssociation() {
    final ITheme theme=manager.getDefaultTheme();
    System.out.println(theme);
    System.out.println(SolarizedLight+" "+theme.getId());
  }
  // @Test
  public static void testDarkThemes() {
    // All themes for Dark E4 CSS Theme
    final ITheme[] darkThemes=manager.getThemes(true);
    System.out.println(darkThemes);
    System.out.println(2+" "+darkThemes.length);
    System.out.println(Dark+" "+darkThemes[0].getId());
    System.out.println(Monokai+" "+darkThemes[1].getId());
    // All themes for Other E4 CSS Theme
    final ITheme[] otherThemes=manager.getThemes(false);
    System.out.println(otherThemes);
    System.out.println(2+" "+otherThemes.length);
    System.out.println(SolarizedLight+" "+otherThemes[0].getId());
    System.out.println(Light+" "+otherThemes[1].getId());
  }
}
