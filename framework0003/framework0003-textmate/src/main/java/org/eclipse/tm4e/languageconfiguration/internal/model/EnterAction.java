package org.eclipse.tm4e.languageconfiguration.internal.model;

import org.eclipse.jdt.annotation.Nullable;

public class EnterAction{
  public enum IndentAction{
    None,
    Indent,
    IndentOutdent,
    Outdent;
    public static IndentAction get(final @Nullable String value) {
      // see
      // https://github.com/microsoft/vscode/blob/13ba7bb446a638d37ebccb1a7d74e31c32bb9790/src/vs/workbench/contrib/codeEditor/browser/languageConfigurationExtensionPoint.ts#L341
      if(value==null) {
        return IndentAction.None;
      }
      switch(value) {
        case "none":
          return IndentAction.None;
        case "indent":
          return IndentAction.Indent;
        case "indentOutdent":
          return IndentAction.IndentOutdent;
        case "outdent":
          return IndentAction.Outdent;
        default:
          return IndentAction.None;
      }
    }
  }
  public final IndentAction indentAction;
  @Nullable
  public String appendText;
  @Nullable
  public Integer removeText;
  public EnterAction(final IndentAction indentAction) {
    this.indentAction=indentAction;
  }
  EnterAction withAppendText(@Nullable final String appendText) {
    this.appendText=appendText;
    return this;
  }
  EnterAction withRemoveText(@Nullable final Integer removeText) {
    this.removeText=removeText;
    return this;
  }
}
