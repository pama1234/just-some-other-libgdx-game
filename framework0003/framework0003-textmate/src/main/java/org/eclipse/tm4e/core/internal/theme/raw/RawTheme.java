package org.eclipse.tm4e.core.internal.theme.raw;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.parser.PropertySettable;
import org.eclipse.tm4e.core.internal.theme.IThemeSetting;

public final class RawTheme extends HashMap<String,@Nullable Object>
  implements IRawTheme,IRawThemeSetting,IThemeSetting,PropertySettable<Object>{
  private static final long serialVersionUID=1L;
  @Nullable
  @Override
  public String getName() {
    return (String)super.get("name");
  }
  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public Collection<IRawThemeSetting> getSettings() {
    return (Collection<IRawThemeSetting>)super.get("settings");
  }
  @Nullable
  @Override
  public Object getScope() {
    return super.get("scope");
  }
  @Nullable
  @Override
  public IThemeSetting getSetting() {
    return (IThemeSetting)super.get("settings");
  }
  @Nullable
  @Override
  public Object getFontStyle() {
    return super.get("fontStyle");
  }
  @Nullable
  @Override
  public String getBackground() {
    return (String)super.get("background");
  }
  @Nullable
  @Override
  public String getForeground() {
    return (String)super.get("foreground");
  }
  @Override
  public void setProperty(final String name,final Object value) {
    put(name,value);
  }
}
