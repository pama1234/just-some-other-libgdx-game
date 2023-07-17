package org.eclipse.tm4e.registry.internal.preferences;

import java.util.Collection;

import org.eclipse.tm4e.registry.GrammarDefinition;
import org.eclipse.tm4e.registry.IGrammarDefinition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

public final class PreferenceHelper{
  private static final Gson DEFAULT_GSON=new GsonBuilder()
    .registerTypeAdapter(IGrammarDefinition.class,
      (InstanceCreator<GrammarDefinition>)type->new GrammarDefinition())
    .create();
  public static IGrammarDefinition[] loadGrammars(final String json) {
    return DEFAULT_GSON.fromJson(json,GrammarDefinition[].class);
  }
  public static String toJson(final Collection<IGrammarDefinition> definitions) {
    return DEFAULT_GSON.toJson(definitions);
  }
  private PreferenceHelper() {}
}
