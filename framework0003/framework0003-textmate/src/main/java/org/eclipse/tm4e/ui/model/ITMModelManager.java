package org.eclipse.tm4e.ui.model;

import org.eclipse.jface.text.IDocument;

public interface ITMModelManager{
  ITMDocumentModel connect(IDocument document);
  void disconnect(IDocument document);
  boolean isConnected(IDocument document);
}
