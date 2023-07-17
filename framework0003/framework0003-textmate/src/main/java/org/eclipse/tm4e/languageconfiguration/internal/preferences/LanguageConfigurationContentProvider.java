package org.eclipse.tm4e.languageconfiguration.internal.preferences;

import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.jface.viewers.IStructuredContentProvider;
// import org.eclipse.jface.viewers.Viewer;
import org.eclipse.tm4e.languageconfiguration.internal.registry.ILanguageConfigurationRegistryManager;

import pama1234.gdx.textmate.IStructuredContentProvider;
import pama1234.gdx.textmate.Viewer;
import pama1234.gdx.textmate.annotation.DeprecatedJface;

@DeprecatedJface
final class LanguageConfigurationContentProvider implements IStructuredContentProvider{
  private static final Object[] EMPTY= {};
  @Nullable
  private ILanguageConfigurationRegistryManager registry;
  @Override
  public Object[] getElements(@Nullable final Object input) {
    return registry!=null?registry.getDefinitions():EMPTY;
  }
  @Override
  public void inputChanged(@Nullable final Viewer viewer,@Nullable final Object oldInput,
    @Nullable final Object newInput) {
    registry=(ILanguageConfigurationRegistryManager)newInput;
  }
  @Override
  public void dispose() {
    registry=null;
  }
}
