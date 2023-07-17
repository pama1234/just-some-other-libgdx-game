package org.eclipse.tm4e.core.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.grammar.IStateStack;
import org.eclipse.tm4e.core.internal.grammar.StateStack;
import org.eclipse.tm4e.core.internal.utils.MoreCollections;
import org.eclipse.tm4e.core.internal.utils.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import pama1234.shift.misc.NonNull;
import pama1234.shift.misc.NonNullByDefault;

public class TMTokenizationSupport implements ITokenizationSupport{
  private final IGrammar _grammar;
  private final IStateStack _initialState;
  private final DecodeMap decodeMap=new DecodeMap();
  public TMTokenizationSupport(final IGrammar grammar) {
    this(grammar,StateStack.NULL);
  }
  public TMTokenizationSupport(final IGrammar grammar,final IStateStack initialState) {
    this._grammar=grammar;
    _initialState=initialState;
  }
  @Override
  public IStateStack getInitialState() {
    return _initialState;
  }
  @Override
  public TokenizationResult tokenize(final String line,@Nullable final IStateStack state) {
    return tokenize(line,state,null,null);
  }
  private final LoadingCache<List<String>,String> tokenTypeOfScopesCache=CacheBuilder.newBuilder()
    .expireAfterAccess(5,TimeUnit.SECONDS)
    .build(new CacheLoader<List<String>,String>() {
      @Override
      public String load(final List<String> scopes) throws Exception {
        return decodeTextMateToken(decodeMap,scopes);
      }
    });
  @Override
  public TokenizationResult tokenize(final String line,
    @Nullable final IStateStack state,
    @Nullable final Integer offsetDeltaOrNull,
    @Nullable final Duration timeLimit) {
    final int offsetDelta=offsetDeltaOrNull==null?0:offsetDeltaOrNull;
    final var tokenizationResult=_grammar.tokenizeLine(line,state,timeLimit);
    final var tokens=tokenizationResult.getTokens();
    // Create the result early and fill in the tokens later
    final var tmTokens=new ArrayList<TMToken>(tokens.length<10?tokens.length:10);
    String lastTokenType=null;
    for(final var token:tokens) {
      final String tokenType=tokenTypeOfScopesCache.getUnchecked(token.getScopes());
      // do not push a new token if the type is exactly the same (also helps with ligatures)
      if(!tokenType.equals(lastTokenType)) {
        final int tokenStartIndex=token.getStartIndex();
        tmTokens.add(new TMToken(tokenStartIndex+offsetDelta,tokenType));
        lastTokenType=tokenType;
      }
    }
    final var lastToken=tokens[tokens.length-1];
    return new TokenizationResult(
      tmTokens,
      // TODO Math.min() is a temporary workaround because currently in some cases lastToken.getEndIndex()
      // incorrectly returns larger values than line.length() for some reasons.
      // See for example GrammarTest#testTokenize1IllegalToken()
      offsetDelta+Math.min(line.length(),lastToken.getEndIndex()),
      tokenizationResult.getRuleStack(),
      tokenizationResult.isStoppedEarly());
  }
  private String decodeTextMateToken(final DecodeMap decodeMap,final List<String> scopes) {
    final var prevTokenScopes=decodeMap.prevToken.scopes;
    final int prevTokenScopesLength=prevTokenScopes.size();
    final var prevTokenScopeTokensMaps=decodeMap.prevToken.scopeTokensMaps;
    final var scopeTokensMaps=new HashMap<Integer,Map<Integer,Boolean>>();
    Map<Integer,Boolean> prevScopeTokensMaps=new HashMap<>();
    boolean sameAsPrev=true;
    for(int level=1;level<scopes.size();level++) {
      final String scope=scopes.get(level);
      if(sameAsPrev) {
        if(level<prevTokenScopesLength&&prevTokenScopes.get(level).equals(scope)) {
          prevScopeTokensMaps=prevTokenScopeTokensMaps.get(level);
          scopeTokensMaps.put(level,prevScopeTokensMaps);
          continue;
        }
        sameAsPrev=false;
      }
      final Integer[] tokens=decodeMap.getTokenIds(scope);
      prevScopeTokensMaps=new HashMap<>(prevScopeTokensMaps);
      for(final Integer token:tokens) {
        prevScopeTokensMaps.put(token,true);
      }
      scopeTokensMaps.put(level,prevScopeTokensMaps);
    }
    decodeMap.prevToken=new TMTokenDecodeData(scopes,scopeTokensMaps);
    return decodeMap.getToken(prevScopeTokensMaps);
  }
  @NonNullByDefault({})
  private record TMTokenDecodeData(
    @NonNull List<String> scopes,
    @NonNull Map<Integer,Map<Integer,Boolean>> scopeTokensMaps) {
  }
  @NonNullByDefault({})
  private static final class DecodeMap{
    private int lastAssignedTokenId=0;
    private final Map<String,Integer[]> scopeToTokenIds=new HashMap<>();
    private final Map<String,Integer> tokenToTokenId=new HashMap<>();
    private final List<String> tokenIdToToken=MoreCollections.asArrayList("element-at-index-zero-is-unused");
    TMTokenDecodeData prevToken=new TMTokenDecodeData(Collections.emptyList(),Collections.emptyMap());
    Integer[] getTokenIds(final String scope) {
      Integer[] tokens=this.scopeToTokenIds.get(scope);
      if(tokens!=null) {
        return tokens;
      }
      final String[] tmpTokens=StringUtils.splitToArray(scope,'.');
      tokens=new Integer[tmpTokens.length];
      for(int i=0;i<tmpTokens.length;i++) {
        final String token=tmpTokens[i];
        Integer tokenId=this.tokenToTokenId.get(token);
        if(tokenId==null) {
          tokenId=++this.lastAssignedTokenId;
          this.tokenToTokenId.put(token,tokenId);
          this.tokenIdToToken.add(token);
        }
        tokens[i]=tokenId;
      }
      this.scopeToTokenIds.put(scope,tokens);
      return tokens;
    }
    String getToken(final Map<Integer,Boolean> tokenMap) {
      final var result=new StringBuilder();
      boolean isFirst=true;
      for(int i=1;i<=this.lastAssignedTokenId;i++) {
        if(tokenMap.containsKey(i)) {
          if(isFirst) {
            isFirst=false;
          }else {
            result.append('.');
          }
          result.append(this.tokenIdToToken.get(i));
        }
      }
      return result.toString();
    }
  }
}
