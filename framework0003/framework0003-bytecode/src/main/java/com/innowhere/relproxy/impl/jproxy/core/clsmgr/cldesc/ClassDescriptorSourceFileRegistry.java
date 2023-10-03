package com.innowhere.relproxy.impl.jproxy.core.clsmgr.cldesc;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author jmarranz
 */
public class ClassDescriptorSourceFileRegistry{
  protected final Map<String,ClassDescriptorSourceUnit> sourceUnitMapByClassName;

  public ClassDescriptorSourceFileRegistry() {
    this.sourceUnitMapByClassName=new HashMap<String,ClassDescriptorSourceUnit>();
  }

  public ClassDescriptorSourceFileRegistry(ClassDescriptorSourceFileRegistry origin) {
    this.sourceUnitMapByClassName=new HashMap<String,ClassDescriptorSourceUnit>(origin.sourceUnitMapByClassName);
  }

  public boolean isEmpty() {
    return sourceUnitMapByClassName.isEmpty();
  }

  public Collection<ClassDescriptorSourceUnit> getClassDescriptorSourceFileColl() {
    return sourceUnitMapByClassName.values();
  }

  public ClassDescriptorSourceUnit getClassDescriptorSourceUnit(String className) {
    return sourceUnitMapByClassName.get(className);
  }

  public ClassDescriptorSourceUnit removeClassDescriptorSourceUnit(String className) {
    return sourceUnitMapByClassName.remove(className);
  }

  public void addClassDescriptorSourceUnit(ClassDescriptorSourceUnit sourceFile) {
    sourceUnitMapByClassName.put(sourceFile.getClassName(),sourceFile);
  }

  public void setAllClassDescriptorSourceFilesPendingToRemove(boolean pending) {
    for(Map.Entry<String,ClassDescriptorSourceUnit> entries:sourceUnitMapByClassName.entrySet()) entries.getValue().setPendingToRemove(pending);
  }

  public LinkedList<ClassDescriptorSourceUnit> getAllClassDescriptorSourceFilesPendingToRemove(LinkedList<ClassDescriptorSourceUnit> deletedSourceFiles) {
    for(Map.Entry<String,ClassDescriptorSourceUnit> entries:sourceUnitMapByClassName.entrySet()) {
      ClassDescriptorSourceUnit classDesc=entries.getValue();
      boolean pending=classDesc.isPendingToRemove();
      if(pending) deletedSourceFiles.add(classDesc);
    }
    return deletedSourceFiles;
  }

  public ClassDescriptor getClassDescriptor(String className) {
    // Puede ser el de una innerclass
    // Las innerclasses no están como tales en sourceFileMap pues sólo está la clase contenedora pero también la consideramos hotloadable
    String parentClassName;
    int pos=className.lastIndexOf('$');
    boolean inner;
    if(pos!=-1) {
      parentClassName=className.substring(0,pos);
      inner=true;
    }else {
      parentClassName=className;
      inner=false;
    }
    ClassDescriptorSourceUnit sourceDesc=sourceUnitMapByClassName.get(parentClassName);
    if(sourceDesc==null) return null;
    if(!inner) return sourceDesc;
    return sourceDesc.getInnerClassDescriptor(className,true);
  }
}
