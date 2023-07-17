package org.eclipse.tm4e.languageconfiguration.internal.model;

public final class CompleteEnterAction extends EnterAction{
  public final String indentation;
  public CompleteEnterAction(final EnterAction action,final String indentation) {
    super(action.indentAction);
    this.indentation=indentation;
    this.appendText=action.appendText;
    this.removeText=action.removeText;
  }
}
