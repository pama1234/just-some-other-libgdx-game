package org.eclipse.tm4e.core.internal.grammar.raw;

import java.util.Collection;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.rule.RuleId;

public interface IRawRule{
  @Nullable
  RuleId getId();
  void setId(RuleId id);
  @Nullable
  String getInclude();
  @Nullable
  String getName();
  @Nullable
  String getContentName();
  @Nullable
  String getMatch();
  @Nullable
  IRawCaptures getCaptures();
  @Nullable
  String getBegin();
  @Nullable
  IRawCaptures getBeginCaptures();
  @Nullable
  String getEnd();
  @Nullable
  String getWhile();
  @Nullable
  IRawCaptures getEndCaptures();
  @Nullable
  IRawCaptures getWhileCaptures();
  @Nullable
  Collection<IRawRule> getPatterns();
  @Nullable
  IRawRepository getRepository();
  boolean isApplyEndPatternLast();
}
