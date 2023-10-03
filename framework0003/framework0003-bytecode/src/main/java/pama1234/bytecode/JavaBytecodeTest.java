package pama1234.bytecode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaBytecodeTest{
  public static void main(String[] args) {

    try {
      MemClassLoader mcl=new MemClassLoader();
      mcl.data=Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/data/test/MyClass.class"));
      Class<?> clas=mcl.loadClass("oblivion.test.MyClass");

      System.out.println("aClass.getName() = "+clas.getName());

      Method mm=clas.getMethod("main",String[].class);
      mm.invoke(null,new Object[] {args});

    }catch(ClassNotFoundException|IOException|NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}