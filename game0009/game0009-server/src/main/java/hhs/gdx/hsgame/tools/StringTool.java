package hhs.gdx.hsgame.tools;

import java.util.Arrays;
import java.util.LinkedList;

public class StringTool{
  public static <T> LinkedList<T> linkedList(T[] list) {
    return new LinkedList<>(Arrays.asList(list));
  }
}
