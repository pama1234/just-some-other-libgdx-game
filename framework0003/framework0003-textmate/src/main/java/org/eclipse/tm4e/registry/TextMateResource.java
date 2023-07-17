package org.eclipse.tm4e.registry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;

public class TextMateResource implements ITMResource{
  private static final String PLATFORM_PLUGIN="platform:/plugin/"; //$NON-NLS-1$
  private String path;
  // public FileHandle fileHandle;
  @Nullable
  private String pluginId;
  public TextMateResource() {
    path="<set-by-gson>";
  }
  public TextMateResource(final String path) {
    this.path=path;
  }
  public TextMateResource(final IConfigurationElement ce) {
    this(ce.getAttribute(XMLConstants.PATH_ATTR));
    this.pluginId=ce.getNamespaceIdentifier();
  }
  public TextMateResource(final String path,@Nullable final String pluginId) {
    this.path=path;
    this.pluginId=pluginId;
  }
  @Override
  public String getPath() {
    return path;
  }
  @Nullable
  @Override
  public String getPluginId() {
    return pluginId;
  }
  @Override
  @SuppressWarnings("resource")
  public InputStream getInputStream() throws IOException {
    return pluginId!=null
      ?new URL(PLATFORM_PLUGIN+pluginId+"/"+path).openStream()
      :new FileInputStream(new File(path));
  }
  @Nullable
  protected String getResourceContent() {
    try(InputStream in=this.getInputStream()) {
      return convertStreamToString(in);
    }catch(final Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
  private static String convertStreamToString(final InputStream is) {
    try(var s=new Scanner(is)) {
      s.useDelimiter("\\A");
      return s.hasNext()?s.next():"";
    }
  }
}
