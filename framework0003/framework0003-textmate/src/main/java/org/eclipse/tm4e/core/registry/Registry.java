package org.eclipse.tm4e.core.registry;

import static org.eclipse.tm4e.core.internal.utils.MoreCollections.nullToEmpty;
import static org.eclipse.tm4e.core.internal.utils.NullSafetyHelper.castNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.TMException;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.internal.grammar.BalancedBracketSelectors;
import org.eclipse.tm4e.core.internal.grammar.dependencies.ScopeDependencyProcessor;
import org.eclipse.tm4e.core.internal.grammar.raw.RawGrammarReader;
import org.eclipse.tm4e.core.internal.registry.SyncRegistry;
import org.eclipse.tm4e.core.internal.theme.TextMateTheme;
import org.eclipse.tm4e.core.internal.theme.raw.RawThemeReader;

public final class Registry{
  // private static final Logger LOGGER=System.getLogger(Registry.class.getName());
  private final IRegistryOptions _options;
  private final SyncRegistry _syncRegistry;
  private final Map<String,Boolean> _ensureGrammarCache=new HashMap<>();
  public Registry() {
    this(new IRegistryOptions() {});
  }
  public Registry(final IRegistryOptions options) {
    this._options=options;
    this._syncRegistry=new SyncRegistry(TextMateTheme.createFromRawTheme(options.getTheme(),options.getColorMap()));
  }
  public void setTheme(final IThemeSource source) throws TMException {
    try {
      this._syncRegistry.setTheme(TextMateTheme.createFromRawTheme(RawThemeReader.readTheme(source),_options.getColorMap()));
    }catch(final Exception ex) {
      throw new TMException("Loading theme from '"+source.getFilePath()+"' failed: "+ex.getMessage(),ex);
    }
  }
  public List<String> getColorMap() {
    return this._syncRegistry.getColorMap();
  }
  @Nullable
  public IGrammar loadGrammarWithEmbeddedLanguages(
    final String initialScopeName,
    final int initialLanguage,
    final Map<String,Integer> embeddedLanguages) {
    return this.loadGrammarWithConfiguration(initialScopeName,initialLanguage,
      new IGrammarConfiguration() {
        @Override
        public @Nullable Map<String,Integer> getEmbeddedLanguages() {
          return embeddedLanguages;
        }
      });
  }
  @Nullable
  public IGrammar loadGrammarWithConfiguration(
    final String initialScopeName,
    final int initialLanguage,
    final IGrammarConfiguration configuration) {
    return this._loadGrammar(
      initialScopeName,
      initialLanguage,
      configuration.getEmbeddedLanguages(),
      configuration.getTokenTypes(),
      new BalancedBracketSelectors(
        nullToEmpty(configuration.getBalancedBracketSelectors()),
        nullToEmpty(configuration.getUnbalancedBracketSelectors())));
  }
  @Nullable
  public IGrammar loadGrammar(final String initialScopeName) {
    return this._loadGrammar(initialScopeName,0,null,null,null);
  }
  @Nullable
  private IGrammar _loadGrammar(
    final String initialScopeName,
    final int initialLanguage,
    @Nullable final Map<String,Integer> embeddedLanguages,
    @Nullable final Map<String,Integer> tokenTypes,
    @Nullable final BalancedBracketSelectors balancedBracketSelectors) {
    final var dependencyProcessor=new ScopeDependencyProcessor(this._syncRegistry,initialScopeName);
    while(!dependencyProcessor.Q.isEmpty()) {
      dependencyProcessor.Q.forEach(request->this._loadSingleGrammar(request.scopeName));
      dependencyProcessor.processQueue();
    }
    return this._grammarForScopeName(
      initialScopeName,
      initialLanguage,
      embeddedLanguages,
      tokenTypes,
      balancedBracketSelectors);
  }
  private void _loadSingleGrammar(final String scopeName) {
    this._ensureGrammarCache.computeIfAbsent(scopeName,this::_doLoadSingleGrammar);
  }
  private boolean _doLoadSingleGrammar(final String scopeName) {
    final var grammarSource=this._options.getGrammarSource(scopeName);
    if(grammarSource==null) {
      // System.out.println(WARNING,"No grammar source for scope [{0}]",scopeName);
      System.err.println("No grammar source for scope [{0}]"+" "+scopeName);
      return false;
    }
    try {
      final var grammar=RawGrammarReader.readGrammar(grammarSource);
      this._syncRegistry.addGrammar(grammar,this._options.getInjections(scopeName));
    }catch(final Exception ex) {
      // System.out.println(ERROR,"Loading grammar for scope [{0}] failed: {1}",scopeName,ex.getMessage(),ex);
      System.err.println("Loading grammar for scope [{0}] failed: {1}"+" "+scopeName+" "+ex.getMessage()+" "+ex);
      return false;
    }
    return true;
  }
  public IGrammar addGrammar(final IGrammarSource source) throws TMException {
    return addGrammar(source,null,null,null);
  }
  public IGrammar addGrammar(
    final IGrammarSource source,
    @Nullable final List<String> injections,
    @Nullable final Integer initialLanguage,
    @Nullable final Map<String,Integer> embeddedLanguages) throws TMException {
    try {
      final var rawGrammar=RawGrammarReader.readGrammar(source);
      this._syncRegistry.addGrammar(rawGrammar,
        injections==null||injections.isEmpty()
          ?this._options.getInjections(rawGrammar.getScopeName())
          :injections);
      return castNonNull(
        this._grammarForScopeName(rawGrammar.getScopeName(),initialLanguage,embeddedLanguages,null,null));
    }catch(final Exception ex) {
      throw new TMException("Loading grammar from '"+source.getFilePath()+"' failed: "+ex.getMessage(),ex);
    }
  }
  @Nullable
  public IGrammar grammarForScopeName(final String scopeName) {
    return _grammarForScopeName(scopeName,null,null,null,null);
  }
  @Nullable
  private IGrammar _grammarForScopeName(
    final String scopeName,
    @Nullable final Integer initialLanguage,
    @Nullable final Map<String,Integer> embeddedLanguages,
    @Nullable final Map<String,Integer> tokenTypes,
    @Nullable final BalancedBracketSelectors balancedBracketSelectors) {
    return this._syncRegistry.grammarForScopeName(
      scopeName,
      initialLanguage==null?0:initialLanguage,
      embeddedLanguages,
      tokenTypes,
      balancedBracketSelectors);
  }
}
