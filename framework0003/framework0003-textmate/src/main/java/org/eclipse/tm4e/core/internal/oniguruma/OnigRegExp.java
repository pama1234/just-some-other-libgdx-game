package org.eclipse.tm4e.core.internal.oniguruma;

import java.nio.charset.StandardCharsets;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.TMException;
import org.jcodings.specific.UTF8Encoding;
import org.joni.Matcher;
import org.joni.Option;
import org.joni.Regex;
import org.joni.Region;
import org.joni.Syntax;
import org.joni.WarnCallback;
import org.joni.exception.SyntaxException;

public final class OnigRegExp{
  // private static final Logger LOGGER=System.getLogger(OnigRegExp.class.getName());
  // private static final WarnCallback LOGGER_WARN_CALLBACK=message->System.out.println(message);
  @Nullable
  private OnigString lastSearchString;
  private int lastSearchPosition=-1;
  @Nullable
  private OnigResult lastSearchResult;
  private final Regex regex;
  private final boolean hasGAnchor;
  public OnigRegExp(final String source) {
    hasGAnchor=source.contains("\\G");
    // System.out.println("114514 "+source);
    final byte[] pattern=source.getBytes(StandardCharsets.UTF_8);
    try {
      // System.out.println(Option.CAPTURE_GROUP);
      regex=new Regex(pattern,0,pattern.length,Option.CAPTURE_GROUP,UTF8Encoding.INSTANCE,Syntax.DEFAULT,WarnCallback.NONE);
      // regex=new Regex(source,UTF8Encoding.INSTANCE);
      // regex=new Regex(pattern,0,pattern.length,Option.CAPTURE_GROUP,UTF8Encoding.INSTANCE,Syntax.DEFAULT,WarnCallback.DEFAULT);
      // LOGGER.isLoggable(Level.WARNING)?LOGGER_WARN_CALLBACK:WarnCallback.NONE);
    }catch(final SyntaxException ex) {
      throw new TMException("Parsing regex pattern \""+source+"\" failed with "+ex,ex);
    }
    // catch(SecurityException ex) {
    //   // System.out.println();
    //   // regex=null;
    //   ex.printStackTrace();
    //   // }finally{
    //   //   regex=null;
    // }
  }
  @Nullable
  OnigResult search(final OnigString str,final int startPosition) {
    if(hasGAnchor) {
      // Should not use caching, because the regular expression
      // targets the current search position (\G)
      return search(str.bytesUTF8,startPosition,str.bytesCount);
    }
    // final var lastSearchResult0=this.lastSearchResult;
    // if(lastSearchString==str
    //   &&lastSearchPosition<=startPosition
    //   &&(lastSearchResult0==null||lastSearchResult0.locationAt(0)>=startPosition)) {
    //   return lastSearchResult0;
    // }
    // lastSearchString=str;
    // lastSearchPosition=startPosition;
    // lastSearchResult=search(str.bytesUTF8,startPosition,str.bytesCount);
    // return lastSearchResult;
    synchronized(this) {
      final var lastSearchResult0=this.lastSearchResult;
      if(lastSearchString==str
        &&lastSearchPosition<=startPosition
        &&(lastSearchResult0==null||lastSearchResult0.locationAt(0)>=startPosition)) {
        return lastSearchResult0;
      }
    }
    var result=search(str.bytesUTF8,startPosition,str.bytesCount);
    synchronized(this) {
      lastSearchString=str;
      lastSearchPosition=startPosition;
      lastSearchResult=result;
    }
    return result;
  }
  @Nullable
  private OnigResult search(final byte[] data,final int startPosition,final int end) {
    final Matcher matcher=regex.matcher(data);
    final int status=matcher.search(startPosition,end,Option.DEFAULT);
    if(status!=Matcher.FAILED) {
      final Region region=matcher.getEagerRegion();
      return new OnigResult(region,-1);
    }
    return null;
  }
}
