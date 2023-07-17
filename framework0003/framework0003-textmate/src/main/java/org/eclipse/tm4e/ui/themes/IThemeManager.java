package org.eclipse.tm4e.ui.themes;

import org.eclipse.jdt.annotation.Nullable;
import org.osgi.service.prefs.BackingStoreException;

import com.badlogic.gdx.graphics.Color;

public interface IThemeManager{
  void registerTheme(ITheme theme);
  void unregisterTheme(ITheme theme);
  @Nullable
  ITheme getThemeById(String themeId);
  ITheme[] getThemes();
  ITheme getDefaultTheme();
  ITheme[] getThemes(boolean dark);
  ITheme getThemeForScope(String scopeName,boolean dark);
  ITheme getThemeForScope(String scopeName);
  void registerThemeAssociation(IThemeAssociation association);
  void unregisterThemeAssociation(IThemeAssociation association);
  IThemeAssociation[] getAllThemeAssociations();
  IThemeAssociation[] getThemeAssociationsForScope(String scopeName);
  void save() throws BackingStoreException;
  boolean isDarkEclipseTheme();
  boolean isDarkEclipseTheme(@Nullable String eclipseThemeId);
  ITheme getThemeForScope(String scopeName,Color background);
}
