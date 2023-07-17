package org.eclipse.tm4e.ui.internal.utils;

import org.eclipse.core.runtime.content.IContentType;

public final class ContentTypeInfo{
  private final String fileName;
  private final IContentType[] contentTypes;
  public ContentTypeInfo(final String fileName,final IContentType[] contentTypes) {
    this.fileName=fileName;
    this.contentTypes=contentTypes;
  }
  public String getFileName() {
    return fileName;
  }
  public IContentType[] getContentTypes() {
    return contentTypes;
  }
}
