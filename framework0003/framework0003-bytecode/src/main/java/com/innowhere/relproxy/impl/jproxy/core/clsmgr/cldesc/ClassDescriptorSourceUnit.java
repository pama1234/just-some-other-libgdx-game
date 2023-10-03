package com.innowhere.relproxy.impl.jproxy.core.clsmgr.cldesc;

import com.innowhere.relproxy.impl.jproxy.core.clsmgr.JProxyEngine;
import com.innowhere.relproxy.impl.jproxy.core.clsmgr.srcunit.SourceFileJavaNormal;
import com.innowhere.relproxy.impl.jproxy.core.clsmgr.srcunit.SourceScriptRoot;
import com.innowhere.relproxy.impl.jproxy.core.clsmgr.srcunit.SourceUnit;
import java.util.LinkedList;

/**
 *
 * @author jmarranz
 */
public abstract class ClassDescriptorSourceUnit extends ClassDescriptor{
  protected final JProxyEngine engine;
  protected volatile long timestamp;
  protected final SourceUnit sourceUnit;
  protected LinkedList<ClassDescriptorInner> innerClasses;
  protected boolean pendingToRemove=false; // Se usa como monohilo, no hay problemas de sincronización

  public ClassDescriptorSourceUnit(JProxyEngine engine,String className,SourceUnit sourceFile,long timestamp) {
    super(className);
    this.engine=engine;
    this.sourceUnit=sourceFile;
    this.timestamp=timestamp;
  }

  public static ClassDescriptorSourceUnit create(boolean script,JProxyEngine engine,String className,SourceUnit sourceFile,long timestamp) {
    if(sourceFile instanceof SourceScriptRoot) return new ClassDescriptorSourceScript(engine,className,(SourceScriptRoot)sourceFile,timestamp);
    else if(sourceFile instanceof SourceFileJavaNormal) return new ClassDescriptorSourceFileJava(engine,className,(SourceFileJavaNormal)sourceFile,timestamp);
    else return null; // WTF!!
  }

  public SourceUnit getSourceUnit() {
    return sourceUnit;
  }

  public String getEncoding() {
    return engine.getSourceEncoding();
  }

  @Override
  public boolean isInnerClass() {
    return false;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void updateTimestamp(long timestamp) {
    this.timestamp=timestamp;
  }

  public boolean isPendingToRemove() {
    return pendingToRemove;
  }

  public void setPendingToRemove(boolean pendingToRemove) {
    this.pendingToRemove=pendingToRemove;
  }

  public void cleanOnSourceCodeChanged() {
    // Como ha cambiado la clase, reseteamos las dependencias        
    setClassBytes(null);
    setLastLoadedClass(null);
    clearInnerClassDescriptors(); // El código fuente nuevo puede haber cambiado totalmente las innerclasses antiguas (añadido, eliminado)
  }

  public boolean isInnerClass(String className) {
    int pos=className.lastIndexOf('$');
    if(pos==-1) return false; // No es innerclass
    String baseClassName=className.substring(0,pos);
    return this.className.equals(baseClassName); // Si es false es que es una innerclass pero de otra clase
  }

  public LinkedList<ClassDescriptorInner> getInnerClassDescriptors() {
    return innerClasses;
  }

  public void clearInnerClassDescriptors() {
    if(innerClasses!=null) innerClasses.clear();
  }

  public ClassDescriptorInner getInnerClassDescriptor(String className,boolean addWhenMissing) {
    if(innerClasses!=null) {
      for(ClassDescriptorInner classDesc:innerClasses) {
        if(classDesc.getClassName().equals(className)) return classDesc;
      }
    }

    if(!addWhenMissing) return null;

    return addInnerClassDescriptor(className);
  }

  public ClassDescriptorInner addInnerClassDescriptor(String className) {
    if(!isInnerClass(className)) return null;

    if(innerClasses==null) innerClasses=new LinkedList<ClassDescriptorInner>();

    ClassDescriptorInner classDesc=new ClassDescriptorInner(className,this);
    innerClasses.add(classDesc);
    return classDesc;
  }

  @Override
  public void resetLastLoadedClass() {
    super.resetLastLoadedClass();

    LinkedList<ClassDescriptorInner> innerClassDescList=getInnerClassDescriptors();
    if(innerClassDescList!=null) {
      for(ClassDescriptorInner innerClassDesc:innerClassDescList) innerClassDesc.resetLastLoadedClass();
    }
  }

}
