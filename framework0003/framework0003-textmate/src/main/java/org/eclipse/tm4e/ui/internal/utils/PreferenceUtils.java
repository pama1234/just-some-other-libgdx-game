package org.eclipse.tm4e.ui.internal.utils;

// import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.jface.preference.IPreferenceStore;
// import org.eclipse.tm4e.ui.TMUIPlugin;
import org.eclipse.tm4e.ui.internal.preferences.PreferenceConstants;

import pama1234.gdx.textmate.annotation.DeprecatedJface;

@DeprecatedJface
public final class PreferenceUtils{
  private static final String E4_CSS_PREFERENCE_NAME="org.eclipse.e4.ui.css.swt.theme"; //$NON-NLS-1$
  private static final String EDITORS_PREFERENCE_NAME="org.eclipse.ui.editors"; //$NON-NLS-1$
  private static @Nullable Boolean isDebugThrowError;
  // public static boolean isDebugGenerateTest() {
  //   return Boolean.parseBoolean(Platform.getDebugOption(TMUIPlugin.PLUGIN_ID+"/debug/log/GenerateTest"));
  // }
  // public static boolean isDebugThrowError() {
  //   var isDebugThrowError=PreferenceUtils.isDebugThrowError;
  //   if(isDebugThrowError==null) isDebugThrowError=PreferenceUtils.isDebugThrowError=Boolean.parseBoolean(
  //     Platform.getDebugOption(TMUIPlugin.PLUGIN_ID+"/debug/log/ThrowError"));
  //   return isDebugThrowError;
  // }
  @Nullable
  public static IEclipsePreferences getE4PreferenceStore() {
    return InstanceScope.INSTANCE.getNode(E4_CSS_PREFERENCE_NAME);
  }
  @Nullable
  public static String getE4PreferenceCSSThemeId() {
    final IEclipsePreferences preferences=getE4PreferenceStore();
    return preferences!=null?preferences.get(PreferenceConstants.E4_THEME_ID,null):null;
  }
  @Nullable
  public static IEclipsePreferences getEditorsPreferenceStore() {
    return InstanceScope.INSTANCE.getNode(EDITORS_PREFERENCE_NAME);
  }
  // @Nullable
  // public static IPreferenceStore getTM4EPreferencesStore() {
  //   final var plugin=TMUIPlugin.getDefault();
  //   return plugin==null?null:plugin.getPreferenceStore();
  // }
  private PreferenceUtils() {}
}
