package org.eclipse.tm4e.ui.internal.model;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.google.common.base.Strings;

final class DocumentHelper{
  enum DocumentEventType{
    INSERT,
    REPLACE,
    REMOVE
  }
  static DocumentEventType getEventType(final DocumentEvent event) {
    if(Strings.isNullOrEmpty(event.getText())) {
      return DocumentEventType.REMOVE;
    }
    return event.getLength()==0
      ?DocumentEventType.INSERT
      :DocumentEventType.REPLACE;
  }
  static int getStartLineCharIndex(final DocumentEvent event) throws BadLocationException {
    return event.getOffset()-event.getDocument().getLineOffset(getStartLineIndex(event));
  }
  static int getStartLineIndex(final DocumentEvent event) throws BadLocationException {
    return event.getDocument().getLineOfOffset(event.getOffset());
  }
  static int getEndLineIndexOfAddedText(final DocumentEvent event) throws BadLocationException {
    final IDocument doc=event.getDocument();
    final int offsetAfterAddedText=event.getOffset()+event.getText().length();
    final boolean isAppendToDocumentEnd=offsetAfterAddedText==doc.getLength();
    if(isAppendToDocumentEnd) {
      return doc.getNumberOfLines()-1;
    }
    return doc.getLineOfOffset(offsetAfterAddedText);
  }
  static int getEndLineIndexOfRemovedText(final DocumentEvent event) throws BadLocationException {
    return event.getDocument().getLineOfOffset(event.getOffset()+event.getLength());
  }
  static String getLineText(final IDocument document,final int lineIndex,final boolean withLineDelimiter)
    throws BadLocationException {
    final int lo=document.getLineOffset(lineIndex);
    int ll=document.getLineLength(lineIndex);
    if(!withLineDelimiter) {
      final String delim=document.getLineDelimiter(lineIndex);
      ll=ll-(delim!=null?delim.length():0);
    }
    return document.get(lo,ll);
  }
  private static IRegion getRegion(final IDocument document,final int fromLineIndex,final int toLineIndex)
    throws BadLocationException {
    final int startOffset=document.getLineOffset(fromLineIndex);
    final int endOffset=document.getLineOffset(toLineIndex)+document.getLineLength(toLineIndex);
    return new Region(startOffset,endOffset-startOffset);
  }
  private DocumentHelper() {}
}
