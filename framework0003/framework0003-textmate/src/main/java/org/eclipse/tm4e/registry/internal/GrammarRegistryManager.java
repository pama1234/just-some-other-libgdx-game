package org.eclipse.tm4e.registry.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.registry.GrammarDefinition;
import org.eclipse.tm4e.registry.IGrammarDefinition;
import org.eclipse.tm4e.registry.TMEclipseRegistryPlugin;
import org.eclipse.tm4e.registry.XMLConstants;
import org.eclipse.tm4e.registry.internal.preferences.PreferenceConstants;
import org.eclipse.tm4e.registry.internal.preferences.PreferenceHelper;
import org.osgi.service.prefs.BackingStoreException;

public final class GrammarRegistryManager extends AbstractGrammarRegistryManager{
  private static final String EXTENSION_GRAMMARS="grammars";
  @Nullable
  private static GrammarRegistryManager INSTANCE;
  public static GrammarRegistryManager getInstance() {
    if(INSTANCE!=null) {
      return INSTANCE;
    }
    INSTANCE=createInstance();
    return INSTANCE;
  }
  private static synchronized GrammarRegistryManager createInstance() {
    if(INSTANCE!=null) {
      return INSTANCE;
    }
    final var manager=new GrammarRegistryManager();
    manager.load();
    return manager;
  }
  private GrammarRegistryManager() {}
  private void load() {
    loadGrammarsFromExtensionPoints();
    loadGrammarsFromPreferences();
  }
  private void loadGrammarsFromExtensionPoints() {
    final IConfigurationElement[] cf=Platform.getExtensionRegistry()
      .getConfigurationElementsFor(TMEclipseRegistryPlugin.PLUGIN_ID,EXTENSION_GRAMMARS);
    for(final IConfigurationElement ce:cf) {
      final String extensionName=ce.getName();
      switch(extensionName) {
        case XMLConstants.GRAMMAR_ELT:
          super.registerGrammarDefinition(new GrammarDefinition(ce));
          break;
        case XMLConstants.INJECTION_ELT: {
          final String scopeName=ce.getAttribute(XMLConstants.SCOPE_NAME_ATTR);
          final String injectTo=ce.getAttribute(XMLConstants.INJECT_TO_ATTR);
          super.registerInjection(scopeName,injectTo);
          break;
        }
        case XMLConstants.SCOPE_NAME_CONTENT_TYPE_BINDING_ELT: {
          final String contentTypeId=ce.getAttribute(XMLConstants.CONTENT_TYPE_ID_ATTR);
          final IContentType contentType=Platform.getContentTypeManager().getContentType(contentTypeId);
          if(contentType==null) {
            Platform.getLog(getClass())
              .warn("No content-type found with id='"+contentTypeId+"', ignoring TM4E association.");
          }else {
            final String scopeName=ce.getAttribute(XMLConstants.SCOPE_NAME_ATTR);
            super.registerContentTypeBinding(contentType,scopeName);
          }
          break;
        }
      }
    }
  }
  private void loadGrammarsFromPreferences() {
    // Load grammar definitions from the
    // "${workspace_loc}/metadata/.plugins/org.eclipse.core.runtime/.settings/org.eclipse.tm4e.registry.prefs"
    final var prefs=InstanceScope.INSTANCE.getNode(TMEclipseRegistryPlugin.PLUGIN_ID);
    final var json=prefs.get(PreferenceConstants.GRAMMARS,null);
    if(json!=null) {
      final IGrammarDefinition[] definitions=PreferenceHelper.loadGrammars(json);
      for(final IGrammarDefinition definition:definitions) {
        userCache.registerGrammarDefinition(definition);
      }
    }
  }
  @Override
  public void save() throws BackingStoreException {
    // Save grammar definitions in the
    // "${workspace_loc}/metadata/.plugins/org.eclipse.core.runtime/.settings/org.eclipse.tm4e.registry.prefs"
    final var json=PreferenceHelper.toJson(userCache.getDefinitions());
    final var prefs=InstanceScope.INSTANCE.getNode(TMEclipseRegistryPlugin.PLUGIN_ID);
    prefs.put(PreferenceConstants.GRAMMARS,json);
    prefs.flush();
  }
}
