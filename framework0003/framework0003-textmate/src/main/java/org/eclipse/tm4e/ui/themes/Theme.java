package org.eclipse.tm4e.ui.themes;

import java.io.InputStream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.jface.text.rules.IToken;
import org.eclipse.tm4e.registry.TextMateResource;
import org.eclipse.tm4e.registry.XMLConstants;
// import org.eclipse.tm4e.ui.TMUIPlugin;
import org.eclipse.tm4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.tm4e.ui.themes.css.CSSTokenProvider;
// import org.eclipse.ui.texteditor.AbstractTextEditor;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.textmate.IToken;
import pama1234.gdx.textmate.StyledText;
import pama1234.gdx.textmate.annotation.DeprecatedJface;
import pama1234.shift.eclipse.AbstractTextEditor;

@DeprecatedJface
public class Theme extends TextMateResource implements ITheme{
  private static final String DARK_ATTR="dark";
  private static final String DEFAULT_ATTR="default";
  @Nullable
  private ITokenProvider tokenProvider;
  private final String id;
  private final String name;
  private boolean dark;
  private boolean isDefault;
  public Theme() {
    name="<set-by-gson>";
    id="<set-by-gson>";
  }
  public Theme(final String id,final String path,final String name,final boolean dark,final boolean isDefault) {
    super(path);
    this.id=id;
    this.name=name;
    this.dark=dark;
    this.isDefault=isDefault;
  }
  public Theme(final IConfigurationElement ce) {
    super(ce);
    id=ce.getAttribute(XMLConstants.ID_ATTR);
    name=ce.getAttribute(XMLConstants.NAME_ATTR);
    dark=Boolean.parseBoolean(ce.getAttribute(DARK_ATTR));
    isDefault=Boolean.parseBoolean(ce.getAttribute(DEFAULT_ATTR));
  }
  @Override
  public String getId() {
    return id;
  }
  @Override
  public String getName() {
    return name;
  }
  @Override
  public IToken getToken(final String type) {
    final ITokenProvider provider=getTokenProvider();
    return provider==null?ITokenProvider.DEFAULT_TOKEN:provider.getToken(type);
  }
  @Nullable
  @Override
  public Color getEditorForeground() {
    final ITokenProvider provider=getTokenProvider();
    final Color themeColor=provider!=null?provider.getEditorForeground():null;
    return ColorManager.getInstance()
      .getPriorityColor(themeColor,AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND);
  }
  @Nullable
  @Override
  public Color getEditorBackground() {
    final ITokenProvider provider=getTokenProvider();
    // System.out.println(provider);
    final Color themeColor=provider!=null?provider.getEditorBackground():null;
    // System.out.println(provider.getEditorBackground());
    return ColorManager.getInstance()
      .getPriorityColor(themeColor,AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
  }
  @Nullable
  @Override
  public Color getEditorSelectionForeground() {
    final ITokenProvider provider=getTokenProvider();
    final Color themeColor=provider!=null?provider.getEditorSelectionForeground():null;
    return ColorManager.getInstance()
      .getPriorityColor(themeColor,AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND);
  }
  @Nullable
  @Override
  public Color getEditorSelectionBackground() {
    final ITokenProvider provider=getTokenProvider();
    final Color themeColor=provider!=null?provider.getEditorSelectionBackground():null;
    return ColorManager.getInstance()
      .getPriorityColor(themeColor,AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND);
  }
  @Nullable
  @Override
  public Color getEditorCurrentLineHighlight() {
    final ITokenProvider provider=getTokenProvider();
    final Color themeColor=provider!=null?provider.getEditorCurrentLineHighlight():null;
    final ColorManager manager=ColorManager.getInstance();
    return manager.isColorUserDefined(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)
      ?manager.getPreferenceEditorColor(PreferenceConstants.EDITOR_CURRENTLINE_HIGHLIGHT)
      :themeColor;
  }
  @Nullable
  public ITokenProvider getTokenProvider() {
    if(tokenProvider==null) {
      // try(InputStream in=getFileInputStream()) {
      try(InputStream in=getInputStream()) {
        tokenProvider=new CSSTokenProvider(in);
      }catch(final Exception ex) {
        // System.err.println(ex);
        ex.printStackTrace();
        return null;
      }
    }
    return tokenProvider;
  }
  // @Override
  // public InputStream getInputStream() {
  //   // return super.getInputStream();
  //   return fileHandle.read();
  // }
  // public abstract InputStream getInputStream();
  // private InputStream getFileInputStream() {
  //   return fileHandle.read();
  // }
  @Nullable
  @Override
  public String toCSSStyleSheet() {
    return super.getResourceContent();
  }
  @Override
  public boolean isDark() {
    return dark;
  }
  @Override
  public boolean isDefault() {
    return isDefault;
  }
  @Override
  public void initializeViewerColors(final StyledText styledText) {
    Color color=getEditorBackground();
    if(color!=null) styledText.setBackground(color);
    color=getEditorForeground();
    if(color!=null) styledText.setForeground(color);
    color=getEditorSelectionBackground();
    if(color!=null) styledText.setSelectionBackground(color);
    color=getEditorSelectionForeground();
    if(color!=null) styledText.setSelectionForeground(color);
  }
  @Override
  public @Nullable Color getColor(boolean isForeground,String... styles) {
    final ITokenProvider provider=getTokenProvider();
    final Color themeColor=provider!=null?provider.getColor(isForeground,styles):null;
    return themeColor;
    // return ColorManager.getInstance()
    //   .getPriorityColor(themeColor,AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND);
  }
}
