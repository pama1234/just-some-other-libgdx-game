package org.eclipse.tm4e.core.internal.grammar.dependencies;

public final class IncludeReference{
  public enum Kind{
    Base,
    Self,
    RelativeReference,
    TopLevelReference,
    TopLevelRepositoryReference
  }
  public static final IncludeReference BASE=new IncludeReference(Kind.Base,"$base","");
  public static final IncludeReference SELF=new IncludeReference(Kind.Base,"$self","");
  public static IncludeReference parseInclude(final String include) {
    return switch(include) {
      case "$base"->BASE;
      case "$self"->SELF;
      default-> {
        final var indexOfSharp=include.indexOf("#");
        yield switch(indexOfSharp) {
          case -1->new IncludeReference(Kind.TopLevelReference,include,"");
          case 0->new IncludeReference(Kind.RelativeReference,"",include.substring(1));
          default-> {
            final var scopeName=include.substring(0,indexOfSharp);
            final var ruleName=include.substring(indexOfSharp+1);
            yield new IncludeReference(Kind.TopLevelRepositoryReference,scopeName,ruleName);
          }
        };
      }
    };
  }
  public final Kind kind;
  public final String scopeName;
  public final String ruleName;
  private IncludeReference(final Kind kind,final String scopeName,final String ruleName) {
    this.kind=kind;
    this.scopeName=scopeName;
    this.ruleName=ruleName;
  }
}
