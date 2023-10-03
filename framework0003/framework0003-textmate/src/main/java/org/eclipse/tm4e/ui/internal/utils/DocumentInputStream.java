package org.eclipse.tm4e.ui.internal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import pama1234.shift.misc.NonNullByDefault;

public final class DocumentInputStream extends InputStream{
  private final IDocument doc;
  private int pos=0;
  public DocumentInputStream(final IDocument document) {
    doc=document;
  }
  public IDocument getDocument() {
    return doc;
  }
  @Override
  public int read(@NonNullByDefault({}) final byte[] buff,final int buffOffset,final int len) throws IOException {
    Objects.checkFromIndexSize(buffOffset,len,buff.length);
    if(len==0) return 0;
    final var docLen=doc.getLength();
    if(pos>=docLen) return -1;
    var bytesRead=-1;
    try {
      buff[buffOffset]=(byte)doc.getChar(pos++);
      bytesRead=1;
      while(bytesRead<len) {
        if(pos>=docLen) {
          break;
        }
        buff[buffOffset+bytesRead++]=(byte)doc.getChar(pos++);
      }
    }catch(final BadLocationException ex) {
      // ignore
    }
    return bytesRead;
  }
  @Override
  public int read() throws IOException {
    try {
      if(pos<doc.getLength()) return doc.getChar(pos++)&0xFF;
    }catch(final BadLocationException ex) {
      // ignore
    }
    return -1;
  }
}
