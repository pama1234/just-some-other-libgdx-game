package org.eclipse.tm4e.core.internal.theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.annotation.Nullable;

public class ThemeTrieElementRule{
  public int scopeDepth;
  @Nullable
  public final List<String> parentScopes;
  public int fontStyle;
  public int foreground;
  public int background;
  public ThemeTrieElementRule(final int scopeDepth,@Nullable final List<String> parentScopes,final int fontStyle,final int foreground,
    final int background) {
    this.scopeDepth=scopeDepth;
    this.parentScopes=parentScopes;
    this.fontStyle=fontStyle;
    this.foreground=foreground;
    this.background=background;
  }
  @Override
  public ThemeTrieElementRule clone() {
    return new ThemeTrieElementRule(this.scopeDepth,this.parentScopes,this.fontStyle,this.foreground,this.background);
  }
  public static List<ThemeTrieElementRule> cloneArr(final List<ThemeTrieElementRule> arr) {
    final var r=new ArrayList<ThemeTrieElementRule>(arr.size());
    for(final var e:arr) {
      r.add(e.clone());
    }
    return r;
  }
  public void acceptOverwrite(final int scopeDepth,final int fontStyle,final int foreground,final int background) {
    if(this.scopeDepth>scopeDepth) {
      // TODO!!!
      // console.log('how did this happen?');
    }else {
      this.scopeDepth=scopeDepth;
    }
    // console.log('TODO -> my depth: ' + this.scopeDepth + ', overwriting depth: ' + scopeDepth);
    if(fontStyle!=FontStyle.NotSet) {
      this.fontStyle=fontStyle;
    }
    if(foreground!=0) {
      this.foreground=foreground;
    }
    if(background!=0) {
      this.background=background;
    }
  }
  @Override
  public int hashCode() {
    int result=31+background;
    result=31*result+fontStyle;
    result=31*result+foreground;
    result=31*result+Objects.hashCode(parentScopes);
    return 31*result+scopeDepth;
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final ThemeTrieElementRule other) return scopeDepth==other.scopeDepth
      &&background==other.background
      &&fontStyle==other.fontStyle
      &&foreground==other.foreground
      &&Objects.equals(parentScopes,other.parentScopes);
    return false;
  }
}
