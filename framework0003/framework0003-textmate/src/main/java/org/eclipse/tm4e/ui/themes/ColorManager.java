package org.eclipse.tm4e.ui.themes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.tm4e.core.internal.utils.StringUtils;
import org.eclipse.tm4e.core.theme.RGB;
// import org.eclipse.tm4e.ui.internal.utils.PreferenceUtils;
// import org.eclipse.ui.texteditor.AbstractTextEditor;

import com.badlogic.gdx.graphics.Color;

public final class ColorManager{
  private static final ColorManager INSTANCE=new ColorManager();
  public static ColorManager getInstance() {
    return INSTANCE;
  }
  private static Color rgbToColor(RGB rgb) {
    // return new Color(UI.getDisplay(),rgb.red,rgb.green,rgb.blue);
    return new Color(rgb.red/255f,rgb.green/255f,rgb.blue/255f,1);
  }
  private final Map<RGB,Color> fColorTable=new HashMap<>(10);
  private ColorManager() {}
  public Color getColor(final RGB rgb) {
    return fColorTable.computeIfAbsent(rgb,ColorManager::rgbToColor);
  }
  @Deprecated
  public void dispose() {
    // fColorTable.values().forEach(Color::dispose);
  }
  @Nullable
  @Deprecated
  public Color getPreferenceEditorColor(final String tokenId) {
    //   final var prefStore=PreferenceUtils.getEditorsPreferenceStore();
    //   if(prefStore==null) return null;
    //   return getColor(stringToRGB(prefStore.get(tokenId,"")));
    return null;
  }
  @Deprecated
  public boolean isColorUserDefined(final String tokenId) {
    //   final var prefStore=PreferenceUtils.getEditorsPreferenceStore();
    //   if(prefStore==null) return false;
    //   final String systemDefaultToken=getSystemDefaultToken(tokenId);
    //   return "".equals(systemDefaultToken) // returns true if system default token doesn't exists
    //     ||!prefStore.getBoolean(systemDefaultToken,true);
    return false;
  }
  @Nullable
  @Deprecated
  public Color getPriorityColor(@Nullable final Color themeColor,final String tokenId) {
    if(isColorUserDefined(tokenId)) {
      return getPreferenceEditorColor(tokenId);
    }
    //   return themeColor!=null?themeColor:null;
    return themeColor!=null?themeColor:null;
  }
  // private String getSystemDefaultToken(final String tokenId) {
  //   return switch(tokenId) {
  //     case AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND->AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT;
  //     case AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND->AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT;
  //     case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND->AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT;
  //     case AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND->AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT;
  //     default->"";
  //   };
  // }
  // private RGB stringToRGB(final String value) {
  //   final String[] rgbValues=StringUtils.splitToArray(value,',');
  //   return rgbValues.length==3
  //     ?new RGB(Integer.parseInt(rgbValues[0]),Integer.parseInt(rgbValues[1]),Integer.parseInt(rgbValues[2]))
  //     :new RGB(255,255,255);
  // }
}
