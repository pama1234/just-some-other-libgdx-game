package org.eclipse.tm4e.languageconfiguration.internal.utils;

import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;

// @DeprecatedJface
public final class RegExpUtils{
  public static String escapeRegExpCharacters(final String value) {
    return value.replaceAll("[\\-\\\\\\{\\}\\*\\+\\?\\|\\^\\$\\.\\[\\]\\(\\)\\#]","\\\\$0"); //$NON-NLS-1$ //$NON-NLS-2$
  }
  @Nullable
  public static Pattern create(final String regex) {
    try {
      return Pattern.compile(regex);
    }catch(final Exception ex) {
      // LanguageConfigurationPlugin.logError("Failed to parse pattern: "+regex,ex);
      System.err.println("Failed to parse pattern: "+regex+" "+ex);
      return null;
    }
  }
  private RegExpUtils() {}
}
