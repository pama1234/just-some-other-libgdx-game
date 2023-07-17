package org.eclipse.tm4e.core.internal.utils;

import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;

import pama1234.shift.misc.NonNull;

public final class NullSafetyHelper{
  @NonNull
  public static <T> T castNonNull(@Nullable final T value) {
    assert value!=null;
    return value;
  }
  @NonNull
  @SuppressWarnings("null")
  private static <T> T castNonNullUnsafe(final T value) {
    return value;
  }
  @Nullable
  public static <T> T castNullable(final T value) {
    return value;
  }
  public static <T> T defaultIfNull(@Nullable final T object,final T defaultValue) {
    if(object==null) {
      return defaultValue;
    }
    return object;
  }
  public static <T> T defaultIfNull(@Nullable final T object,final Supplier<T> defaultValue) {
    if(object==null) {
      return defaultValue.get();
    }
    return object;
  }
  @NonNull
  @SuppressWarnings("unchecked")
  public static <T> T lazyNonNull() {
    return (T)castNonNullUnsafe(null);
  }
  private NullSafetyHelper() {}
}
