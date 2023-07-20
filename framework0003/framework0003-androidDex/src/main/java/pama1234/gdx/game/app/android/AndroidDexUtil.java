package pama1234.gdx.game.app.android;

import java.io.*;

import dalvik.system.DexClassLoader;
import pama1234.dex.DexUtil;

public class AndroidDexUtil implements DexUtil{
  @Override
  public ClassLoader getClassLoader() {
    return DexClassLoader.getSystemClassLoader();
  }
  @Override
  public DexCompiler getDexCompiler() {
    return new AndroidDexCompiler();
  }
  public static class AndroidDexCompiler implements DexCompiler{
  }
  //  public static void main(String[] args) throws IOException {
  //    InputStream is=getAssets().open("MyClass.class");
  //    File file=new File(getCacheDir(),"mycode.dex");
  //    OutputStream os=new FileOutputStream(file);
  //    //---
  ////    DexFile dexFile=new DexFile(is);
  //    DexFile dexFile=new DexFile(file);
  ////    PathClassLoader
  //    DexFileOutput dexFileOutput=new DexFileOutput(os);
  //    dexFile.writeTo(dexFileOutput);
  //    //---
  //    ClassLoader loader=new DexClassLoader(dexFile.getAbsolutePath(),getCacheDir().getAbsolutePath(),null,Object.class.getClassLoader());
  //    Class<?> myClass=loader.loadClass("com.example.MyClass");
  //    Object myInstance=myClass.newInstance();
  //  }
}
