package pama1234.gdx.pvm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.*;

public class SimpleTransformer implements ClassFileTransformer{

  public SimpleTransformer() {
    super();
  }

  public byte[] transform(ClassLoader loader,String className,Class redefiningClass,ProtectionDomain domain,byte[] bytes) throws IllegalClassFormatException {
    return transformClass(redefiningClass,bytes);
  }

  private byte[] transformClass(Class classToTransform,byte[] b) {
    ClassPool pool=ClassPool.getDefault();
    CtClass cl=null;
    try {
      cl=pool.makeClass(new java.io.ByteArrayInputStream(b));
      CtBehavior[] methods=cl.getDeclaredBehaviors();
      for(int i=0;i<methods.length;i++) {
        if(methods[i].isEmpty()==false) {
          changeMethod(methods[i]);
        }
      }
      b=cl.toBytecode();
    }catch(Exception e) {
      e.printStackTrace();
    }catch(Throwable t) {
      t.printStackTrace();
    }finally {
      if(cl!=null) {
        cl.detach();
      }
    }
    return b;
  }

  private void changeMethod(CtBehavior method) throws NotFoundException,CannotCompileException {
    /*
     * if (method.getName().equals("doIt")) {
     * method.insertBefore("System.out.println(\"started method at \" + new java.util.Date());");
     * method.insertAfter("System.out.println(\"ended method at \" + new java.util.Date());"); }
     */

    //MY CODE
    //!Modifier.isAbstract(method.getModifiers()) -- abstract methods can't be modified. If you get exceptions, then add this to the if statement.
    //native methods can't be modified.
    if(!Modifier.isNative(method.getModifiers())) {
      String insertString="System.out.println(\"started method "+method.getName()+"\");";
      method.insertBefore(insertString);
    }
  }
}