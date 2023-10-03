package org.eclipse.tm4e.languageconfiguration.internal.registry;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.tm4e.languageconfiguration.LanguageConfigurationPlugin;
import org.eclipse.tm4e.languageconfiguration.internal.model.LanguageConfiguration;
import org.eclipse.tm4e.languageconfiguration.internal.supports.CharacterPairSupport;
import org.eclipse.tm4e.languageconfiguration.internal.supports.CommentSupport;
import org.eclipse.tm4e.languageconfiguration.internal.supports.OnEnterSupport;
import org.eclipse.tm4e.registry.TextMateResource;
import org.eclipse.tm4e.registry.XMLConstants;
import org.eclipse.tm4e.ui.internal.utils.ContentTypeHelper;

import pama1234.gdx.textmate.annotation.DeprecatedJface;

@DeprecatedJface
public final class LanguageConfigurationDefinition extends TextMateResource implements ILanguageConfigurationDefinition{
  private final IContentType contentType;
  private boolean onEnterEnabled=true;
  private boolean bracketAutoClosingEnabled=true;
  private boolean matchingPairsEnabled=true;
  @Nullable
  private CharacterPairSupport characterPair;
  @Nullable
  private OnEnterSupport onEnter;
  @Nullable
  private CommentSupport comment;
  public LanguageConfigurationDefinition(final IContentType contentType,final String path) {
    super(path);
    this.contentType=contentType;
  }
  public LanguageConfigurationDefinition(final IConfigurationElement ce) throws CoreException {
    super(ce);
    final var contentTypeId=ce.getAttribute(XMLConstants.CONTENT_TYPE_ID_ATTR);
    final IContentType contentType=ContentTypeHelper.getContentTypeById(contentTypeId);
    if(contentType==null) throw new CoreException(
      new Status(IStatus.ERROR,LanguageConfiguration.class,
        "Cannot load language configuration with unknown content type ID "+contentTypeId));
    this.contentType=contentType;
  }
  public LanguageConfigurationDefinition(final IContentType contentType,final String path,
    @Nullable final String pluginId,
    final boolean onEnterEnabled,final boolean bracketAutoClosingEnabled,final boolean matchingPairsEnabled) {
    super(path,pluginId);
    this.contentType=contentType;
    this.onEnterEnabled=onEnterEnabled;
    this.bracketAutoClosingEnabled=bracketAutoClosingEnabled;
    this.matchingPairsEnabled=matchingPairsEnabled;
  }
  @Nullable
  CharacterPairSupport getCharacterPair() {
    if(this.characterPair==null) {
      final LanguageConfiguration conf=getLanguageConfiguration();
      if(conf!=null) {
        this.characterPair=new CharacterPairSupport(conf);
      }
    }
    return characterPair;
  }
  @Nullable
  OnEnterSupport getOnEnter() {
    if(this.onEnter==null) {
      final LanguageConfiguration conf=getLanguageConfiguration();
      if(conf!=null&&(conf.getBrackets()!=null||conf.getOnEnterRules()!=null)) {
        this.onEnter=new OnEnterSupport(conf.getBrackets(),conf.getOnEnterRules());
      }
    }
    return onEnter;
  }
  @Nullable
  CommentSupport getCommentSupport() {
    if(this.comment==null) {
      final LanguageConfiguration conf=getLanguageConfiguration();
      if(conf!=null) {
        this.comment=new CommentSupport(conf.getComments());
      }
    }
    return comment;
  }
  @Override
  public IContentType getContentType() {
    return contentType;
  }
  @Nullable
  @Override
  public LanguageConfiguration getLanguageConfiguration() {
    try(var in=getInputStream()) {
      return LanguageConfiguration.load(new InputStreamReader(in,Charset.defaultCharset()));
    }catch(final IOException ex) {
      // LanguageConfigurationPlugin.logError(ex);
      System.err.println(ex);
      return null;
    }
  }
  @Override
  public boolean isOnEnterEnabled() {
    return onEnterEnabled;
  }
  @Override
  public void setOnEnterEnabled(final boolean onEnterEnabled) {
    this.onEnterEnabled=onEnterEnabled;
  }
  @Override
  public boolean isBracketAutoClosingEnabled() {
    return bracketAutoClosingEnabled;
  }
  @Override
  public void setBracketAutoClosingEnabled(final boolean bracketAutoClosingEnabled) {
    this.bracketAutoClosingEnabled=bracketAutoClosingEnabled;
  }
  @Override
  public boolean isMatchingPairsEnabled() {
    return matchingPairsEnabled;
  }
  @Override
  public void setMatchingPairsEnabled(final boolean matchingPairsEnabled) {
    this.matchingPairsEnabled=matchingPairsEnabled;
  }
}
