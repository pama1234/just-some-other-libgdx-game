package org.eclipse.tm4e.core.internal.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public final class ScopeStack{
  @Nullable
  static ScopeStack push(@Nullable ScopeStack path,final List<String> scopeNames) {
    for(final var name:scopeNames) {
      path=new ScopeStack(path,name);
    }
    return path;
  }
  public static ScopeStack from(final String first) {
    return new ScopeStack(null,first);
  }
  @Nullable
  public static ScopeStack from(final String... segments) {
    ScopeStack result=null;
    for(int i=0;i<segments.length;i++) {
      result=new ScopeStack(result,segments[i]);
    }
    return result;
  }
  @Nullable
  public static ScopeStack from(final List<String> segments) {
    ScopeStack result=null;
    for(int i=0;i<segments.size();i++) {
      result=new ScopeStack(result,segments.get(i));
    }
    return result;
  }
  @Nullable
  public final ScopeStack parent;
  public final String scopeName;
  public ScopeStack(@Nullable final ScopeStack parent,final String scopeName) {
    this.parent=parent;
    this.scopeName=scopeName;
  }
  public ScopeStack push(final String scopeName) {
    return new ScopeStack(this,scopeName);
  }
  public List<String> getSegments() {
    @Nullable
    ScopeStack item=this;
    final var result=new ArrayList<String>();
    while(item!=null) {
      result.add(item.scopeName);
      item=item.parent;
    }
    Collections.reverse(result);
    return result;
  }
  @Override
  public String toString() {
    return String.join(" ",getSegments());
  }
  public boolean isExtending(final ScopeStack other) {
    if(this==other) {
      return true;
    }
    final var parent=this.parent;
    if(parent==null) {
      return false;
    }
    return parent.isExtending(other);
  }
  public List<String> getExtensionIfDefined(@Nullable final ScopeStack base) {
    final var result=new ArrayList<String>();
    @Nullable
    ScopeStack item=this;
    while(item!=null&&item!=base) {
      result.add(item.scopeName);
      item=item.parent;
    }
    if(item==base) {
      Collections.reverse(result);
      return result;
    }
    return Collections.emptyList();
  }
}
