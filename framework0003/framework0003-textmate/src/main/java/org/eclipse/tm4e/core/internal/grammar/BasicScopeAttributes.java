package org.eclipse.tm4e.core.internal.grammar;

final class BasicScopeAttributes{
  final int languageId;
  final int tokenType;
  BasicScopeAttributes(final int languageId,final int tokenType) {
    this.languageId=languageId;
    this.tokenType=tokenType;
  }
}
