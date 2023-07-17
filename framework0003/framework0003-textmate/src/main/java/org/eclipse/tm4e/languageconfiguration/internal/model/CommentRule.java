package org.eclipse.tm4e.languageconfiguration.internal.model;

import org.eclipse.jdt.annotation.Nullable;

public final class CommentRule{
  @Nullable
  public final String lineComment;
  @Nullable
  public final CharacterPair blockComment;
  public CommentRule(@Nullable final String lineComment,@Nullable final CharacterPair blockComment) {
    this.lineComment=lineComment;
    this.blockComment=blockComment;
  }
}
