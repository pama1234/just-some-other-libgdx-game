package org.eclipse.tm4e.languageconfiguration.internal.supports;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.tm4e.languageconfiguration.internal.model.CharacterPair;
import org.eclipse.tm4e.languageconfiguration.internal.model.CommentRule;

public final class CommentSupport{
  @Nullable
  private final CommentRule comments;
  public CommentSupport(@Nullable final CommentRule comments) {
    this.comments=comments;
  }
  private boolean isInComment(final IDocument document,final int offset) {
    try {
      if(isInBlockComment(document.get(0,offset))) {
        return true;
      }
      final int line=document.getLineOfOffset(offset);
      final int lineOffset=document.getLineOffset(line);
      return isInLineComment(document.get(lineOffset,offset-lineOffset));
    }catch(final BadLocationException e) {
      return false;
    }
  }
  @Nullable
  public String getLineComment() {
    final var comments=this.comments;
    return comments==null?null:comments.lineComment;
  }
  @Nullable
  public CharacterPair getBlockComment() {
    final var comments=this.comments;
    return comments==null?null:comments.blockComment;
  }
  private boolean isInLineComment(final String indexLinePrefix) {
    final var comments=this.comments;
    if(comments==null) return false;
    return indexLinePrefix.indexOf(comments.lineComment)!=-1;
  }
  private boolean isInBlockComment(final String indexPrefix) {
    final var comments=this.comments;
    if(comments==null) return false;
    final var blockComment=comments.blockComment;
    if(blockComment==null) return false;
    final String commentOpen=blockComment.open;
    final String commentClose=blockComment.close;
    int index=indexPrefix.indexOf(commentOpen);
    while(index!=-1&&index<indexPrefix.length()) {
      final int closeIndex=indexPrefix.indexOf(commentClose,index+commentOpen.length());
      if(closeIndex==-1) {
        return true;
      }
      index=indexPrefix.indexOf(commentOpen,closeIndex+commentClose.length());
    }
    return false;
  }
}
