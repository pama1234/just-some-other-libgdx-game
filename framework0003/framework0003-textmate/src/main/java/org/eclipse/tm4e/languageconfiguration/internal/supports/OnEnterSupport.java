package org.eclipse.tm4e.languageconfiguration.internal.supports;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.languageconfiguration.internal.model.CharacterPair;
import org.eclipse.tm4e.languageconfiguration.internal.model.EnterAction;
import org.eclipse.tm4e.languageconfiguration.internal.model.EnterAction.IndentAction;
import org.eclipse.tm4e.languageconfiguration.internal.model.OnEnterRule;
import org.eclipse.tm4e.languageconfiguration.internal.utils.RegExpUtils;

public class OnEnterSupport{
  private static final List<CharacterPair> DEFAULT_BRACKETS=List.of(
    new CharacterPair("(",")"), //$NON-NLS-1$ //$NON-NLS-2$
    new CharacterPair("{","}"), //$NON-NLS-1$ //$NON-NLS-2$
    new CharacterPair("[","]")); //$NON-NLS-1$ //$NON-NLS-2$
  private final List<ProcessedBracketPair> brackets;
  private final List<OnEnterRule> regExpRules;
  public OnEnterSupport(@Nullable final List<CharacterPair> brackets,@Nullable final List<OnEnterRule> regExpRules) {
    this.brackets=(brackets!=null?brackets:DEFAULT_BRACKETS)
      .stream()
      .filter(Objects::nonNull)
      .map(ProcessedBracketPair::new)
      .toList();
    this.regExpRules=regExpRules!=null?regExpRules:Collections.emptyList();
  }
  @Nullable
  public EnterAction onEnter(
    // TODO autoIndent: EditorAutoIndentStrategy,
    // TODO final String previousLineText,
    final String beforeEnterText,
    final String afterEnterText) {
    // (1): `regExpRules`
    for(final OnEnterRule rule:regExpRules) {
      final var beforeText=rule.beforeText;
      if(beforeText.matcher(beforeEnterText).find()) {
        final var afterText=rule.afterText;
        if(afterText!=null) {
          if(afterText.matcher(afterEnterText).find()) {
            return rule.action;
          }
        }else {
          return rule.action;
        }
      }
    }
    // (2): Special indent-outdent
    if(!beforeEnterText.isEmpty()&&!afterEnterText.isEmpty()) {
      for(final ProcessedBracketPair bracket:brackets) {
        if(bracket.matchOpen(beforeEnterText)&&bracket.matchClose(afterEnterText)) {
          return new EnterAction(IndentAction.IndentOutdent);
        }
      }
    }
    // (3): Open bracket based logic
    if(!beforeEnterText.isEmpty()) {
      for(final ProcessedBracketPair bracket:brackets) {
        if(bracket.matchOpen(beforeEnterText)) {
          return new EnterAction(IndentAction.Indent);
        }
      }
    }
    return null;
  }
  private static final class ProcessedBracketPair{
    private static final Pattern B_REGEXP=Pattern.compile("\\B"); //$NON-NLS-1$
    @Nullable
    private final Pattern openRegExp;
    @Nullable
    private final Pattern closeRegExp;
    private ProcessedBracketPair(final CharacterPair charPair) {
      openRegExp=createOpenBracketRegExp(charPair.open);
      closeRegExp=createCloseBracketRegExp(charPair.close);
    }
    private boolean matchOpen(final String beforeEnterText) {
      return openRegExp!=null&&openRegExp.matcher(beforeEnterText).find();
    }
    private boolean matchClose(final String afterEnterText) {
      return closeRegExp!=null&&closeRegExp.matcher(afterEnterText).find();
    }
    @Nullable
    private static Pattern createOpenBracketRegExp(final String bracket) {
      final var str=new StringBuilder(RegExpUtils.escapeRegExpCharacters(bracket));
      final var c=String.valueOf(str.charAt(0));
      if(!B_REGEXP.matcher(c).find()) {
        str.insert(0,"\\b"); //$NON-NLS-1$
      }
      str.append("\\s*$"); //$NON-NLS-1$
      return RegExpUtils.create(str.toString());
    }
    @Nullable
    private static Pattern createCloseBracketRegExp(final String bracket) {
      final var str=new StringBuilder(RegExpUtils.escapeRegExpCharacters(bracket));
      final var c=String.valueOf(str.charAt(str.length()-1));
      if(!B_REGEXP.matcher(c).find()) {
        str.append("\\b"); //$NON-NLS-1$
      }
      str.insert(0,"^\\s*"); //$NON-NLS-1$
      return RegExpUtils.create(str.toString());
    }
  }
}
