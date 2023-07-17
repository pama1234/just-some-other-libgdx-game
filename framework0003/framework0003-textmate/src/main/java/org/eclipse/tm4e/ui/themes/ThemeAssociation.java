package org.eclipse.tm4e.ui.themes;

import java.util.Objects;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;

public final class ThemeAssociation implements IThemeAssociation{
  private static final String THEME_ID_ATTR="themeId"; //$NON-NLS-1$
  private static final String SCOPE_NAME_ATTR="scopeName"; //$NON-NLS-1$
  private static final String WHEN_DARK_ATTR="whenDark"; //$NON-NLS-1$
  private final String themeId;
  @Nullable
  private String scopeName;
  private boolean whenDark;
  @Nullable
  private String pluginId;
  public ThemeAssociation() {
    themeId="<set-by-gson>";
  }
  public ThemeAssociation(final String themeId,final String scopeName,final boolean whenDark) {
    this.themeId=themeId;
    this.scopeName=scopeName;
    this.whenDark=whenDark;
  }
  public ThemeAssociation(final IConfigurationElement ce) {
    this(ce.getAttribute(THEME_ID_ATTR),ce.getAttribute(SCOPE_NAME_ATTR),
      "true".equals(ce.getAttribute(WHEN_DARK_ATTR)));
    this.pluginId=ce.getNamespaceIdentifier();
  }
  @Nullable
  @Override
  public String getPluginId() {
    return pluginId;
  }
  @Override
  public String getThemeId() {
    return themeId;
  }
  @Nullable
  @Override
  public String getScopeName() {
    return scopeName;
  }
  @Override
  public boolean isWhenDark() {
    return whenDark;
  }
  @Override
  public int hashCode() {
    int result=31+Boolean.hashCode(whenDark);
    result=31*result+Objects.hashCode(pluginId);
    result=31*result+Objects.hashCode(scopeName);
    return 31*result+themeId.hashCode();
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof ThemeAssociation other) return whenDark==other.whenDark
      &&Objects.equals(pluginId,other.pluginId)
      &&Objects.equals(scopeName,other.scopeName)
      &&Objects.equals(themeId,other.themeId);
    return false;
  }
}
