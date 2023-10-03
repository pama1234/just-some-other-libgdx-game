package org.eclipse.tm4e.languageconfiguration.internal.model;

import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;

public final class OnEnterRule{
  public final Pattern beforeText;
  @Nullable
  public final Pattern afterText;
  // TODO @Nullable public final Pattern previousLineText;
  public final EnterAction action;
  public OnEnterRule(final Pattern beforeText,@Nullable final Pattern afterText,final EnterAction action) {
    this.beforeText=beforeText;
    this.afterText=afterText;
    this.action=action;
  }
  OnEnterRule(final String beforeText,@Nullable final String afterText,final EnterAction action) {
    this.beforeText=Pattern.compile(beforeText);
    this.afterText=afterText==null?null:Pattern.compile(afterText);
    this.action=action;
  }
}
