package org.eclipse.tm4e.ui.internal.utils;

import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.text.IDocument;

public final class ResourceUtils{
  @Nullable
  public static IResource findResource(final IDocument doc) {
    // for local unit tests to prevent ExceptionInInitiaizerError
    if(!Platform.isRunning()) return null;
    final var buffer=ITextFileBufferManager.DEFAULT.getTextFileBuffer(doc);
    if(buffer==null) return null;
    final var loc=buffer.getLocation();
    if(loc==null) return null;
    return ResourcesPlugin.getWorkspace().getRoot().findMember(loc);
  }
  private ResourceUtils() {}
}
