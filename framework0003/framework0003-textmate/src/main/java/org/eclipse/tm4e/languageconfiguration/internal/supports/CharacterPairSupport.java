package org.eclipse.tm4e.languageconfiguration.internal.supports;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.languageconfiguration.internal.model.AutoClosingPair;
import org.eclipse.tm4e.languageconfiguration.internal.model.AutoClosingPairConditional;
import org.eclipse.tm4e.languageconfiguration.internal.model.LanguageConfiguration;

public final class CharacterPairSupport{
  public static final String DEFAULT_AUTOCLOSE_BEFORE_LANGUAGE_DEFINED=";:.,=}])> \r\n\t";
  public static final String DEFAULT_AUTOCLOSE_BEFORE_WHITESPACE=" \r\n\t";
  public final List<AutoClosingPairConditional> autoClosingPairs;
  public final List<AutoClosingPair> surroundingPairs;
  public final String autoCloseBefore;
  @SuppressWarnings("unchecked")
  public CharacterPairSupport(final LanguageConfiguration config) {
    final var autoClosingPairs=config.getAutoClosingPairs();
    final var brackets=config.getBrackets();
    if(autoClosingPairs!=null) {
      this.autoClosingPairs=autoClosingPairs.stream().filter(Objects::nonNull)
        .map(el->new AutoClosingPairConditional(el.open,el.close,el.notIn))
        .toList();
    }else if(brackets!=null) {
      this.autoClosingPairs=brackets.stream().filter(Objects::nonNull)
        .map(el->new AutoClosingPairConditional(el.open,el.close,Collections.emptyList()))
        .toList();
    }else {
      this.autoClosingPairs=Collections.emptyList();
    }
    final var autoCloseBefore=config.getAutoCloseBefore();
    this.autoCloseBefore=autoCloseBefore!=null
      ?autoCloseBefore
      :CharacterPairSupport.DEFAULT_AUTOCLOSE_BEFORE_LANGUAGE_DEFINED;
    final var surroundingPairs=config.getSurroundingPairs();
    this.surroundingPairs=surroundingPairs!=null
      ?surroundingPairs.stream().filter(Objects::nonNull).collect(Collectors.toList())
      :(List<AutoClosingPair>)(List<?>)this.autoClosingPairs;
  }
  @Nullable
  public AutoClosingPairConditional getAutoClosingPair(final String text,final int offset,
    final String newCharacter) {
    if(newCharacter.isEmpty()) {
      return null;
    }
    for(final AutoClosingPairConditional autoClosingPair:autoClosingPairs) {
      final String opening=autoClosingPair.open;
      if(!opening.endsWith(newCharacter)) {
        continue;
      }
      if(opening.length()>1) {
        final String offsetPrefix=text.substring(0,offset);
        if(!offsetPrefix.endsWith(opening.substring(0,opening.length()-1))) {
          continue;
        }
      }
      return autoClosingPair;
    }
    return null;
  }
}
