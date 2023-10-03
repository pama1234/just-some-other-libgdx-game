package org.eclipse.tm4e.ui.internal.model;

import static org.eclipse.tm4e.core.internal.utils.NullSafetyHelper.castNullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jface.text.IDocument;
import org.eclipse.tm4e.ui.model.ITMModelManager;

public final class TMModelManager implements ITMModelManager{
  public static final TMModelManager INSTANCE=new TMModelManager();
  private final Map<IDocument,TMDocumentModel> models=new ConcurrentHashMap<>();
  private TMModelManager() {}
  @Override
  public TMDocumentModel connect(final IDocument document) {
    return models.computeIfAbsent(document,TMDocumentModel::new);
  }
  @Override
  public void disconnect(final IDocument document) {
    final var model=castNullable(models.remove(document));
    if(model!=null) {
      model.dispose();
    }
  }
  @Override
  public boolean isConnected(IDocument document) {
    return models.containsKey(document);
  }
}
