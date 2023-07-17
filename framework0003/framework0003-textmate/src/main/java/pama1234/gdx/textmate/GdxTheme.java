package pama1234.gdx.textmate;

import java.io.InputStream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.tm4e.ui.themes.Theme;

import com.badlogic.gdx.files.FileHandle;

public class GdxTheme extends Theme{
  public FileHandle fileHandle;
  public GdxTheme(IConfigurationElement ce) {
    super(ce);
  }
  public GdxTheme() {}
  public GdxTheme(String id,FileHandle fileHandle,String name,boolean dark,boolean isDefault) {
    super(id,fileHandle.path(),name,dark,isDefault);
    this.fileHandle=fileHandle;
  }
  @Override
  public InputStream getInputStream() {//安卓上无法从path字符串获取文件
    return fileHandle.read();
  }
}
