package com.innowhere.relproxy.impl.jproxy.core.clsmgr.cldesc;

import com.innowhere.relproxy.RelProxyException;
import com.innowhere.relproxy.impl.jproxy.core.JProxyImpl;
import com.innowhere.relproxy.impl.jproxy.core.clsmgr.JProxyEngine;
import com.innowhere.relproxy.impl.jproxy.core.clsmgr.srcunit.SourceScriptRoot;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;

/**
 *
 * @author jmarranz
 */
public class ClassDescriptorSourceScript extends ClassDescriptorSourceUnit{
  protected String source;

  public ClassDescriptorSourceScript(JProxyEngine engine,String className,SourceScriptRoot sourceFile,long timestamp) {
    super(engine,className,sourceFile,timestamp);

    generateSourceCode();
  }

  public SourceScriptRoot getSourceScript() {
    return (SourceScriptRoot)sourceUnit;
  }

  private void generateSourceCode() {
    boolean[] hasHashBang=new boolean[1];

    String scriptCode=getSourceScript().getScriptCode(getEncoding(),hasHashBang);

    boolean completeClass=isCompleteClass(scriptCode);

    StringBuilder finalCode=new StringBuilder();
    if(completeClass) {
      if(hasHashBang[0]) finalCode.append("\n"); // Como hemos quitado la línea #! añadimos una nueva para que los números de línea en caso de error coincidan con el original
      finalCode.append(scriptCode);
    }else {
      JProxyImpl jproxy=engine.getJProxy();
      String mainParamsDec=null;
      String mainReturnType=null;

      Class mainParamClass=jproxy.getMainParamClass();
      if(mainParamClass.equals(String[].class)) {
        mainParamsDec="String[] args";
        mainReturnType="void";
      }else if(mainParamClass.equals(ScriptContext.class)) {
        mainParamsDec=ScriptEngine.class.getName()+" engine,"+ScriptContext.class.getName()+" context";
        mainReturnType="Object";

        if(scriptCode.equals("")) scriptCode="return null;";
      }

      finalCode.append("public class "+className+" { public static "+mainReturnType+" main("+mainParamsDec+") {\n"); // Lo ponemos todo en una línea para que en caso de error la línea de error coincida con el script original pues hemos quitado la primera línea #!
      finalCode.append(scriptCode);
      finalCode.append("  }\n");
      finalCode.append("}\n");
    }
    this.source=finalCode.toString();
  }

  private boolean isCompleteClass(String code) {
    // Buscamos si hay un " class ..." o un "import..." al comienzo para soportar la definición de una clase completa como script       
    int pos=code.indexOf("class");
    if(pos==-1) return false;
    // Hay al menos un "class", ojo que puede ser parte de una variable o dentro de un comentario, pero si no existiera desde luego que no es clase completa

    pos=getFirstPosIgnoringCommentsAndSeparators(code);
    if(pos==-1) return false;

    // Lo primero que nos tenemos encontrar es un import o una declaración de class
    int pos2=code.indexOf("import",pos);
    if(pos2==pos) return true; // Si hay un import hay declaración de clase

    // Vemos si es un "public class..." o similar
    int posClass=code.indexOf("class",pos);
    String visibility=code.substring(pos,posClass);
    visibility=visibility.trim(); // No consideramos \n hay que ser retorcido poner un \n entre el public y el class por ejemplo
    if(visibility.isEmpty()) return true; // No hay visibilidad, que no compile no es cosa nuestra
    return ("private".equals(visibility)||"public".equals(visibility)||"protected".equals(visibility));
  }

  private int getFirstPosIgnoringCommentsAndSeparators(String code) {
    int i=-1;
    for(i=0;i<code.length();i++) {
      char c=code.charAt(i);
      if(c==' '||c=='\n'||c=='\t') continue;
      else if(c=='/'&&i+1<code.length()) {
        char c2=code.charAt(i+1);
        if(c2=='/') {
          i=getFirstPosIgnoringOneLineComment(code,i);
          if(i==-1) return -1; // Comentario mal formado
        }else if(c2=='*') {
          i=getFirstPosIgnoringMultiLineComment(code,i);
          if(i==-1) return -1; // Comentario mal formado                    
        }
      }else break;
    }
    return i;
  }

  private int getFirstPosIgnoringOneLineComment(String code,int start) {
    return code.indexOf('\n',start);
  }

  private int getFirstPosIgnoringMultiLineComment(String code,int start) {
    return code.indexOf("*/",start);
  }

  @Override
  public void updateTimestamp(long timestamp) {
    long oldTimestamp=this.timestamp;
    if(oldTimestamp!=timestamp) generateSourceCode();
    super.updateTimestamp(timestamp);
  }

  public String getSourceCode() {
    return source;
  }

  public void callMainMethod(LinkedList<String> argsToScript) throws Throwable {
    try {
      Class scriptClass=getLastLoadedClass();
      Method method=scriptClass.getDeclaredMethod("main",new Class[] {String[].class});
      String[] argsToScriptArr=argsToScript.size()>0?argsToScript.toArray(new String[argsToScript.size()]):new String[0];
      method.invoke(null,new Object[] {argsToScriptArr});
    }catch(IllegalAccessException ex) {
      throw new RelProxyException(ex);
    }catch(NoSuchMethodException ex) {
      throw new RelProxyException(ex);
    }catch(SecurityException ex) {
      throw new RelProxyException(ex);
    }catch(IllegalArgumentException ex) {
      throw new RelProxyException(ex);
    }catch(InvocationTargetException ex) {
      throw ex.getCause();
    } // Los errores de ejecución se envuelven en un InvocationTargetException        
  }

  public Object callMainMethod(ScriptEngine engine,ScriptContext context) throws Throwable {
    Class scriptClass=getLastLoadedClass();
    return callMainMethod(scriptClass,engine,context);
  }

  public static Object callMainMethod(Class scriptClass,ScriptEngine engine,ScriptContext context) throws Throwable {
    try {
      Method method=scriptClass.getDeclaredMethod("main",new Class[] {ScriptEngine.class,ScriptContext.class});
      return method.invoke(null,new Object[] {engine,context});
    }catch(IllegalAccessException ex) {
      throw new RelProxyException(ex);
    }catch(NoSuchMethodException ex) {
      throw new RelProxyException(ex);
    }catch(SecurityException ex) {
      throw new RelProxyException(ex);
    }catch(IllegalArgumentException ex) {
      throw new RelProxyException(ex);
    }catch(InvocationTargetException ex) {
      throw ex.getCause();
    } // Los errores de ejecución se envuelven en un InvocationTargetException        
  }
}
