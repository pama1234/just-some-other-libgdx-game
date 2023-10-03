package org.eclipse.tm4e.core.internal.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.Nullable;

public final class MoreCollections{
  public static <T> List<T> asArrayList(final T firstItem) {
    final var list=new ArrayList<T>();
    list.add(firstItem);
    return list;
  }
  public static <T> List<T> asArrayList(final T firstItem,final List<T> moreItems) {
    final var list=new ArrayList<T>();
    list.add(firstItem);
    list.addAll(moreItems);
    return list;
  }
  @Nullable
  public static <T> T findFirstMatching(final List<T> list,final Predicate<T> filter) {
    for(final T e:list) {
      if(filter.test(e)) return e;
    }
    return null;
  }
  @Nullable
  public static <T> T findLastElement(final List<T> list) {
    if(list.isEmpty()) return null;
    return getLastElement(list);
  }
  public static <T> T getElementAt(final List<T> list,final int index) {
    if(index<0) return list.get(list.size()+index);
    return list.get(index);
  }
  public static <T> T getLastElement(final List<T> list) {
    return list.get(list.size()-1);
  }
  public static <T> List<T> nullToEmpty(@Nullable final List<T> list) {
    return list==null?Collections.emptyList():list;
  }
  public static <T> T removeLastElement(final List<T> list) {
    return list.remove(list.size()-1);
  }
  public static String toStringWithIndex(final List<?> list) {
    if(list.isEmpty()) return "[]";
    final var sb=new StringBuilder("[");
    for(int i=0,l=list.size();i<l;i++) {
      final var e=list.get(i);
      sb.append(i).append(':').append(e==list?"(this List)":e);
      if(i==l-1) break;
      sb.append(", ");
    }
    return sb.append(']').toString();
  }
  private MoreCollections() {}
}
