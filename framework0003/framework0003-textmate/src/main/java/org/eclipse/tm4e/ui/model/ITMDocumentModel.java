package org.eclipse.tm4e.ui.model;

import org.eclipse.jface.text.IDocument;
import org.eclipse.tm4e.core.model.ITMModel;

public interface ITMDocumentModel extends ITMModel{
  IDocument getDocument();
}
