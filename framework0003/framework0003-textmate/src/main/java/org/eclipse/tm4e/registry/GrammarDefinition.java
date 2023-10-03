package org.eclipse.tm4e.registry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;

public class GrammarDefinition extends TextMateResource implements IGrammarDefinition{
  @Nullable
  private String scopeName;
  public GrammarDefinition() {}
  public GrammarDefinition(final String scopeName,final String path) {
    super(path);
    this.scopeName=scopeName;
  }
  public GrammarDefinition(final IConfigurationElement ce) {
    super(ce);
    this.scopeName=ce.getAttribute(XMLConstants.SCOPE_NAME_ATTR);
  }
  @Override
  public String getScopeName() {
    assert scopeName!=null;
    return scopeName;
  }
}
