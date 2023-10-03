package org.eclipse.tm4e.core.internal.matcher;

import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public interface NameMatcher<T>{
  NameMatcher<List<String>> DEFAULT=new NameMatcher<>() {
    @Override
    public boolean matches(final Collection<String> identifiers,final List<String> scopes) {
      if(scopes.size()<identifiers.size()) {
        return false;
      }
      final int[] lastIndex= {0};
      return identifiers.stream().allMatch(identifier-> {
        for(int i=lastIndex[0];i<scopes.size();i++) {
          if(scopesAreMatching(scopes.get(i),identifier)) {
            lastIndex[0]++;
            return true;
          }
        }
        return false;
      });
    }
    private boolean scopesAreMatching(@Nullable final String thisScopeName,final String scopeName) {
      if(thisScopeName==null) {
        return false;
      }
      if(thisScopeName.equals(scopeName)) {
        return true;
      }
      final int len=scopeName.length();
      return thisScopeName.length()>len
        &&thisScopeName.substring(0,len).equals(scopeName)
        &&thisScopeName.charAt(len)=='.';
    }
  };
  boolean matches(Collection<String> names,T scopes);
}
