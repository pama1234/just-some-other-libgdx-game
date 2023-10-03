package pama1234.android.dex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import pama1234.util.R;

public class AndroidDexTest extends Activity{
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    File file=null;
    try {
      file=File.createTempFile("input",".dex");

      InputStream reader=getResources().openRawResource(R.raw.classes);
      OutputStream writer=new FileOutputStream(file.getAbsolutePath());

      while(true) {
        int x=reader.read();
        if(x==-1) break;
        writer.write(x);
      }

      Context context=this;
      DexClassLoader dexloader=new DexClassLoader(file.getAbsolutePath(),null,
        null,
        AndroidDexTest.class.getClassLoader());

      @SuppressLint("DiscouragedPrivateApi")
      Field f=BaseDexClassLoader.class.getDeclaredField("pathList");
      f.setAccessible(true);
      Object dexPathObjNew=f.get(dexloader); // get the pathList of dexloader that hold the loaded dex file path 
      f.set(AndroidDexTest.class.getClassLoader(),dexPathObjNew); // set the pathlist of current context classloader to the pathList of dexloader
      Class<?> c=AndroidDexTest.class.getClassLoader().loadClass("com.example.myapplication2.helloActivity");

      Intent i=new Intent(null,c);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(i);
    }catch(IOException|NoSuchFieldException|ClassNotFoundException|IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {

  }
}
