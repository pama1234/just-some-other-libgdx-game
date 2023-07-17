package org.eclipse.tm4e.languageconfiguration.internal.preferences;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.tm4e.languageconfiguration.LanguageConfigurationPlugin;
import org.eclipse.tm4e.languageconfiguration.internal.registry.ILanguageConfigurationDefinition;
import org.eclipse.tm4e.languageconfiguration.internal.registry.LanguageConfigurationDefinition;
import org.eclipse.tm4e.ui.internal.utils.ContentTypeHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import pama1234.gdx.textmate.annotation.DeprecatedJface;
import pama1234.shift.misc.NonNullByDefault;

@DeprecatedJface
public final class PreferenceHelper{
  @NonNullByDefault({})
  private static final Gson DEFAULT_GSON=new GsonBuilder()
    .registerTypeAdapter(LanguageConfigurationDefinition.class,
      (JsonDeserializer<LanguageConfigurationDefinition>)(json,typeOfT,context)-> {
        final JsonObject object=json.getAsJsonObject();
        final JsonElement pluginId=object.get("pluginId");
        final var contentTypeId=object.get("contentTypeId").getAsString();
        final var contentType=ContentTypeHelper.getContentTypeById(contentTypeId);
        if(contentType==null) {
          LanguageConfigurationPlugin.logError(
            "Cannot load language configuration with unknown content type ID "+contentTypeId,
            null);
          return null;
        }
        return new LanguageConfigurationDefinition(contentType, // $NON-NLS-1$
          object.get("path").getAsString(), //$NON-NLS-1$
          pluginId==null?null:pluginId.getAsString(),
          object.get("onEnterEnabled").getAsBoolean(), //$NON-NLS-1$
          object.get("bracketAutoClosingEnabled").getAsBoolean(), //$NON-NLS-1$
          object.get("matchingPairsEnabled").getAsBoolean()); //$NON-NLS-1$
      })
    .registerTypeAdapter(LanguageConfigurationDefinition.class,
      (JsonSerializer<LanguageConfigurationDefinition>)(definition,typeOfT,context)-> {
        final JsonObject object=new JsonObject();
        object.addProperty("path",definition.getPath()); //$NON-NLS-1$
        object.addProperty("pluginId",definition.getPluginId()); //$NON-NLS-1$
        object.addProperty("contentTypeId",definition.getContentType().getId()); //$NON-NLS-1$
        object.addProperty("onEnterEnabled",definition.isOnEnterEnabled()); //$NON-NLS-1$
        object.addProperty("bracketAutoClosingEnabled",definition.isBracketAutoClosingEnabled()); //$NON-NLS-1$
        object.addProperty("matchingPairsEnabled",definition.isMatchingPairsEnabled()); //$NON-NLS-1$
        return object;
      })
    .create();
  public static ILanguageConfigurationDefinition[] loadLanguageConfigurationDefinitions(final String json) {
    return Arrays.stream(DEFAULT_GSON.fromJson(json,LanguageConfigurationDefinition[].class))
      .filter(Objects::nonNull).toArray(
        ILanguageConfigurationDefinition[]::new);
  }
  public static String toJson(final Collection<ILanguageConfigurationDefinition> definitions) {
    return DEFAULT_GSON.toJson(definitions);
  }
  private PreferenceHelper() {}
}
