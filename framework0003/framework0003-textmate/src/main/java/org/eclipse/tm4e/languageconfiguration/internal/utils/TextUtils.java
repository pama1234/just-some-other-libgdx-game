package org.eclipse.tm4e.languageconfiguration.internal.utils;

import org.eclipse.jface.text.BadLocationException;
// import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import pama1234.gdx.textmate.annotation.DeprecatedJface;

@DeprecatedJface
public final class TextUtils{
  // public static boolean isEnter(final IDocument doc,final DocumentCommand cmd) {
  //   return cmd.length==0&&cmd.text!=null&&TextUtilities.equals(doc.getLegalLineDelimiters(),cmd.text)!=-1;
  // }
  public static String normalizeIndentation(final String str,final int tabSize,final boolean insertSpaces) {
    int firstNonWhitespaceIndex=TextUtils.firstNonWhitespaceIndex(str);
    if(firstNonWhitespaceIndex==-1) {
      firstNonWhitespaceIndex=str.length();
    }
    return TextUtils.normalizeIndentationFromWhitespace(str.substring(0,firstNonWhitespaceIndex),tabSize,
      insertSpaces)+str.substring(firstNonWhitespaceIndex);
  }
  private static String normalizeIndentationFromWhitespace(final String str,final int tabSize,
    final boolean insertSpaces) {
    int spacesCnt=0;
    for(int i=0;i<str.length();i++) {
      if(str.charAt(i)=='\t') {
        spacesCnt+=tabSize;
      }else {
        spacesCnt++;
      }
    }
    final var result=new StringBuilder();
    if(!insertSpaces) {
      final long tabsCnt=Math.round(Math.floor(spacesCnt/tabSize));
      spacesCnt=spacesCnt%tabSize;
      for(int i=0;i<tabsCnt;i++) {
        result.append('\t');
      }
    }
    for(int i=0;i<spacesCnt;i++) {
      result.append(' ');
    }
    return result.toString();
  }
  public static int startIndexOfOffsetTouchingString(final String text,final int offset,final String string) {
    int start=offset-string.length();
    start=start<0?0:start;
    int end=offset+string.length();
    end=end>=text.length()?text.length():end;
    try {
      final int indexInSubtext=text.substring(start,end).indexOf(string);
      return indexInSubtext==-1?-1:start+indexInSubtext;
    }catch(final IndexOutOfBoundsException e) {
      return -1;
    }
  }
  private static int firstNonWhitespaceIndex(final String str) {
    for(int i=0,len=str.length();i<len;i++) {
      final char c=str.charAt(i);
      if(c!=' '&&c!='\t') {
        return i;
      }
    }
    return -1;
  }
  public static String getIndentationFromWhitespace(final String whitespace,final int tabSize,final boolean insertSpaces) {
    final var tab="\t"; //$NON-NLS-1$
    int indentOffset=0;
    boolean startsWithTab=true;
    boolean startsWithSpaces=true;
    final String spaces=insertSpaces
      ?" ".repeat(tabSize)
      :"";
    while(startsWithTab||startsWithSpaces) {
      startsWithTab=whitespace.startsWith(tab,indentOffset);
      startsWithSpaces=insertSpaces&&whitespace.startsWith(spaces,indentOffset);
      if(startsWithTab) {
        indentOffset+=tab.length();
      }
      if(startsWithSpaces) {
        indentOffset+=spaces.length();
      }
    }
    return whitespace.substring(0,indentOffset);
  }
  public static String getLinePrefixingWhitespaceAtPosition(final IDocument d,final int offset) {
    try {
      // find start of line
      final int p=offset;
      final IRegion info=d.getLineInformationOfOffset(p);
      final int start=info.getOffset();
      // find white spaces
      final int end=findEndOfWhiteSpace(d,start,offset);
      return d.get(start,end-start);
    }catch(final BadLocationException excp) {
      // stop work
    }
    return ""; //$NON-NLS-1$
  }
  private static int findEndOfWhiteSpace(final IDocument document,int offset,final int end)
    throws BadLocationException {
    while(offset<end) {
      final char c=document.getChar(offset);
      if(c!=' '&&c!='\t') {
        return offset;
      }
      offset++;
    }
    return end;
  }
  public static boolean isBlankLine(final IDocument document,final int line) {
    try {
      int offset=document.getLineOffset(line);
      final int lineEnd=offset+document.getLineLength(line);
      while(offset<lineEnd) {
        if(!Character.isWhitespace(document.getChar(offset))) {
          return false;
        }
        offset++;
      }
    }catch(final BadLocationException e) {
      // Ignore, forcing a positive result
    }
    return true;
  }
  private TextUtils() {}
}
