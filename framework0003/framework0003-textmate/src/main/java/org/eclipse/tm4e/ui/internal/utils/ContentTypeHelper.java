package org.eclipse.tm4e.ui.internal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashSet;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
// import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.text.IDocument;
// import org.eclipse.ui.IEditorInput;
// import org.eclipse.ui.IStorageEditorInput;

import pama1234.gdx.textmate.annotation.DeprecatedJface;
import pama1234.shift.eclipse.IEditorInput;

@DeprecatedJface
public final class ContentTypeHelper{
  @Nullable
  public static ContentTypeInfo findContentTypes(final IDocument document) throws CoreException {
    // Find content types from FileBuffers
    final ContentTypeInfo contentTypes=findContentTypesFromFileBuffers(document);
    if(contentTypes!=null) {
      return contentTypes;
    }
    // Find content types from the IEditorInput
    return findContentTypesFromEditorInput(document);
  }
  @Nullable
  public static IContentType getContentTypeById(final String contentTypeId) {
    final IContentTypeManager manager=Platform.getContentTypeManager();
    return manager.getContentType(contentTypeId);
  }
  // ------------------------- Find content types from FileBuffers
  @Nullable
  private static ContentTypeInfo findContentTypesFromFileBuffers(final IDocument document) throws CoreException {
    final ITextFileBufferManager bufferManager=FileBuffers.getTextFileBufferManager();
    final ITextFileBuffer buffer=bufferManager.getTextFileBuffer(document);
    if(buffer!=null) {
      return getContentTypes(buffer);
    }
    return null;
  }
  @Nullable
  private static ContentTypeInfo getContentTypes(final ITextFileBuffer buffer) throws CoreException {
    try {
      final String fileName=buffer.getFileStore().getName();
      final var contentTypes=new LinkedHashSet<IContentType>();
      final IContentType bufferContentType=buffer.getContentType();
      if(bufferContentType!=null) {
        contentTypes.add(bufferContentType);
      }
      if(buffer.isDirty()) {
        // Buffer is dirty (content of the filesystem is not synch with
        // the editor content), use IDocument content.
        try(var input=new DocumentInputStream(buffer.getDocument())) {
          final IContentType[] contentTypesForInput=Platform.getContentTypeManager()
            .findContentTypesFor(input,fileName);
          if(contentTypesForInput!=null) {
            contentTypes.addAll(Arrays.asList(contentTypesForInput));
            return new ContentTypeInfo(fileName,contentTypes.toArray(IContentType[]::new));
          }
        }
      }
      // Buffer is synchronized with filesystem content
      try(InputStream contents=getContents(buffer)) {
        contentTypes.addAll(
          Arrays.asList(Platform.getContentTypeManager().findContentTypesFor(contents,fileName)));
        return new ContentTypeInfo(fileName,contentTypes.toArray(IContentType[]::new));
      }catch(final Exception e) {
        return null;
      }
    }catch(final IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }
  private static InputStream getContents(final ITextFileBuffer buffer) throws CoreException {
    final IPath path=buffer.getLocation();
    if(path!=null) {
      final IWorkspaceRoot workspaceRoot=ResourcesPlugin.getWorkspace().getRoot();
      final IFile file=workspaceRoot.getFile(path);
      if(file.exists()&&buffer.isSynchronized()) {
        return file.getContents();
      }
    }
    return buffer.getFileStore().openInputStream(EFS.NONE,null);
  }
  // ------------------------- Find content types from FileBuffers
  @Nullable
  private static ContentTypeInfo findContentTypesFromEditorInput(final IDocument document) {
    final IEditorInput editorInput=getEditorInput(document);
    if(editorInput!=null) {
      // if(editorInput instanceof final IStorageEditorInput storageInput) {
      //   try {
      //     final IStorage storage=storageInput.getStorage();
      //     final String fileName=storage.getName();
      //     try(InputStream input=storage.getContents()) {
      //       return new ContentTypeInfo(fileName,
      //         Platform.getContentTypeManager().findContentTypesFor(input,fileName));
      //     }
      //   }catch(final Exception e) {
      //     return null;
      //   }
      // }
    }
    return null;
  }
  @Nullable
  private static IEditorInput getEditorInput(final IDocument document) {
    try {
      // This utilities class is very ugly, I have not found a clean mean
      // to retrieve the IEditorInput linked to a document.
      // Here the strategy to retrieve the IEditorInput:
      // AbstractDocumentProvider#ElementInfo add a IDocumentListener to
      // the document.
      // ElementInfo contains a fElement which is the IEditorInput (like
      // ISorageEditorInput, see StorageDocumentProvider)
      // get list of IDocumentListener
      final ListenerList<?> listeners=ClassHelper.getFieldValue(document,"fDocumentListeners");
      if(listeners!=null) {
        // Get AbstractDocumentProvider#ElementInfo
        final Object[] l=listeners.getListeners();
        for(int i=0;i<l.length;i++) {
          final Object info=l[i];
          try {
            final Object input=ClassHelper.getFieldValue(info,"fElement");
            if(input instanceof final IEditorInput editorInput) {
              return editorInput;
            }
          }catch(final RuntimeException e) {
            e.printStackTrace();
          }
        }
      }
    }catch(final RuntimeException e) {
      e.printStackTrace();
    }
    return null;
  }
  private ContentTypeHelper() {}
}
