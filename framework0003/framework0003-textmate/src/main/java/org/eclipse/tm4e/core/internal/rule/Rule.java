package org.eclipse.tm4e.core.internal.rule;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.internal.oniguruma.OnigCaptureIndex;
import org.eclipse.tm4e.core.internal.utils.RegexSource;
import org.eclipse.tm4e.core.internal.utils.StringUtils;

public abstract class Rule{
  final RuleId id;
  @Nullable
  private final String name;
  private final boolean nameIsCapturing;
  @Nullable
  private final String contentName;
  private final boolean contentNameIsCapturing;
  Rule(final RuleId id,@Nullable final String name,final @Nullable String contentName) {
    this.id=id;
    this.name=name;
    this.nameIsCapturing=RegexSource.hasCaptures(name);
    this.contentName=contentName;
    this.contentNameIsCapturing=RegexSource.hasCaptures(contentName);
  }
  @Nullable
  public String getName(@Nullable final CharSequence lineText,final OnigCaptureIndex @Nullable [] captureIndices) {
    final var name=this.name;
    if(!nameIsCapturing||name==null||lineText==null||captureIndices==null) {
      return name;
    }
    return RegexSource.replaceCaptures(name,lineText,captureIndices);
  }
  @Nullable
  public String getContentName(final CharSequence lineText,final OnigCaptureIndex[] captureIndices) {
    final var contentName=this.contentName;
    if(!contentNameIsCapturing||contentName==null) {
      return contentName;
    }
    return RegexSource.replaceCaptures(contentName,lineText,captureIndices);
  }
  public abstract void collectPatterns(IRuleRegistry grammar,RegExpSourceList out);
  public abstract CompiledRule compile(IRuleRegistry grammar,@Nullable String endRegexSource);
  public abstract CompiledRule compileAG(IRuleRegistry grammar,@Nullable String endRegexSource,boolean allowA,boolean allowG);
  @Override
  public String toString() {
    return StringUtils.toString(this,sb-> {
      sb.append("id=").append(id);
      sb.append(",name=").append(name);
    });
  }
}
