package pama1234.gdx.game.app.app0005;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.android.dx.BinaryOp;
import com.android.dx.Code;
import com.android.dx.DexMaker;
import com.android.dx.FieldId;
import com.android.dx.Local;
import com.android.dx.MethodId;
import com.android.dx.TypeId;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0024 extends ScreenCore2D{
  public static final PrintStream stderr=System.err,stdout=System.out;
  public static final PrintStream utilerr,utilout;
  public static StringBuffer outBuilder=new StringBuffer(),errBuilder=new StringBuffer();
  public static ArrayList<String> outText,errText;
  static {
    outText=new ArrayList<>();
    errText=new ArrayList<>();
    System.setOut(utilout=new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        stdout.write(b);
        char a=(char)b;
        if(a=='\n') {
          outText.add(outBuilder.toString());
          outBuilder.setLength(0);
        }else errBuilder.append(a);
      }
    }));
    System.setErr(utilerr=new PrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        stderr.write(b);
        char a=(char)b;
        if(a=='\n') {
          errText.add(errBuilder.toString());
          errBuilder.setLength(0);
        }else errBuilder.append(a);
      }
    }));
  }
  public DexMaker dexMaker;
  {
    isAndroid=true;
  }
  @Override
  public void setup() {
    if(isAndroid) {
      cam2d.minScale=1/8f;
      //---
      test_2();
    }
  }
  public void test_1() {
    // TypeId<?> fileTest=TypeId.get("LFileTest;");
    // dexMaker.declare(fileTest,"FileTest.generated",Modifier.PUBLIC,TypeId.OBJECT);
    TypeId<?> helloWorld=TypeId.get("LHelloWorld;");
    // dexMaker.declare(helloWorld,"FileTest.generated",Modifier.PUBLIC,TypeId.OBJECT);
    dexMaker.declare(helloWorld,"HelloWorld.generated",Modifier.PUBLIC,TypeId.OBJECT);
    generateHelloMethod(dexMaker,helloWorld);
    FileHandle localCache=Gdx.files.local("data/cache/dex");
    if(!localCache.exists()) localCache.mkdirs();
    File outputDir=localCache.file();
    ClassLoader loader;
    try {
      loader=dexMaker.generateAndLoad(Screen0024.class.getClassLoader(),outputDir);
      Class<?> helloWorldClass=loader.loadClass("HelloWorld");
      helloWorldClass.getMethod("hello").invoke(null);
    }catch(IOException|ClassNotFoundException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
      e.printStackTrace();
    }
  }
  public void test_2() {
    dexMaker=new DexMaker();
    TypeId<?> fileTest=TypeId.get("LFileTest;");
    dexMaker.declare(fileTest,Gdx.files.internal("java/test0002/FileTest.javad").path(),Modifier.PUBLIC,TypeId.OBJECT);
    // File.createTempFile();
    FileHandle localCache=Gdx.files.local("data/cache/dex");
    if(!localCache.exists()) localCache.mkdirs();
    File outputDir=localCache.file();
    ClassLoader loader;
    try {
      loader=dexMaker.generateAndLoad(Screen0024.class.getClassLoader(),outputDir);
      Class<?> helloWorldClass=loader.loadClass("FileTest");
      helloWorldClass.getMethod("hello").invoke(null);
    }catch(IOException|ClassNotFoundException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    text(outText.size()+" "+errText.size(),0,-40);
    for(int i=0;i<outText.size();i++) {
      text(Integer.toString(outText.get(i).length()),-60,i*20);
      text(outText.get(i),0,i*20);
    }
    for(int i=0;i<errText.size();i++) {
      text(Integer.toString(errText.get(i).length()),580,40+i*20);
      text(errText.get(i),640,40+i*20);
    }
  }
  @Override
  public void frameResized() {}
  /**
   * Generates Dalvik bytecode equivalent to the following method. public static void hello() {
   * int a = 0xabcd; int b = 0xaaaa; int c = a - b; String s = Integer.toHexString(c);
   * System.out.println(s); return; }
   */
  public void generateHelloMethod(DexMaker dexMaker,TypeId<?> declaringType) {
    // TypeId<System> systemType=TypeId.get(System.class);
    // Lookup some types we'll need along the way.
    TypeId<PrintStream> printStreamType=TypeId.get(PrintStream.class);
    // Identify the 'hello()' method on declaringType.
    MethodId<?,Void> hello=declaringType.getMethod(TypeId.VOID,"hello");
    // Declare that method on the dexMaker. Use the returned Code instance
    // as a builder that we can append instructions to.
    Code code=dexMaker.declare(hello,Modifier.STATIC|Modifier.PUBLIC);
    // Declare all the locals we'll need up front. The API requires this.
    Local<Integer> a=code.newLocal(TypeId.INT);
    Local<Integer> b=code.newLocal(TypeId.INT);
    Local<Integer> c=code.newLocal(TypeId.INT);
    Local<String> s=code.newLocal(TypeId.STRING);
    // Local<String> s_2=code.newLocal(TypeId.STRING);
    Local<PrintStream> localSystemOut=code.newLocal(printStreamType);
    // int a = 0xabcd;
    code.loadConstant(a,0xabcd);
    // int b = 0xaaaa;
    code.loadConstant(b,0xaaaa);
    // int c = a - b;
    code.op(BinaryOp.SUBTRACT,c,a,b);
    // String s = Integer.toHexString(c);
    MethodId<Integer,String> toHexString=TypeId.get(Integer.class).getMethod(TypeId.STRING,"toHexString",TypeId.INT);
    code.invokeStatic(toHexString,s,c);
    // System.out.println(s);
    // FieldId<System,PrintStream> systemOutField=systemType.getField(printStreamType,"out");
    FieldId<Screen0024,PrintStream> systemOutField=TypeId.get(Screen0024.class).getField(printStreamType,"utilout");
    code.sget(systemOutField,localSystemOut);
    MethodId<PrintStream,Void> printlnMethod=printStreamType.getMethod(
      TypeId.VOID,"println",TypeId.STRING);
    code.invokeVirtual(printlnMethod,null,localSystemOut,s);
    // return;
    code.returnVoid();
  }
  public static void main(String[] args) {
    int a=0xabcd;
    int b=0xaaaa;
    int c=a-b;
    String s=Integer.toHexString(c);
    stdout.println(s);
  }
}
