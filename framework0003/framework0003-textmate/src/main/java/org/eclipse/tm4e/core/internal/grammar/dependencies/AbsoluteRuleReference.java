package org.eclipse.tm4e.core.internal.grammar.dependencies;

public abstract class AbsoluteRuleReference{
  static final class TopLevelRuleReference extends AbsoluteRuleReference{
    TopLevelRuleReference(final String scopeName) {
      super(scopeName);
    }
  }
  static final class TopLevelRepositoryRuleReference extends AbsoluteRuleReference{
    final String ruleName;
    TopLevelRepositoryRuleReference(final String scopeName,final String ruleName) {
      super(scopeName);
      this.ruleName=ruleName;
    }
    @Override
    String toKey() {
      return this.scopeName+'#'+this.ruleName;
    }
  }
  public final String scopeName;
  private AbsoluteRuleReference(final String scopeName) {
    this.scopeName=scopeName;
  }
  String toKey() {
    return this.scopeName;
  }
}
