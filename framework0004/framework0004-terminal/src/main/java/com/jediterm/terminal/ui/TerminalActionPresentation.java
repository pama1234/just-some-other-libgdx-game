package com.jediterm.terminal.ui;

import java.util.Collections;
import java.util.List;

import javax.swing.KeyStroke;

import org.jetbrains.annotations.NotNull;

import pama1234.gdx.terminal.DeprecatedAwt;

@DeprecatedAwt
public class TerminalActionPresentation{
  private final String myName;
  private final List<KeyStroke> myKeyStrokes;
  public TerminalActionPresentation(@NotNull String name,@NotNull KeyStroke keyStroke) {
    this(name,Collections.singletonList(keyStroke));
  }
  public TerminalActionPresentation(@NotNull String name,@NotNull List<KeyStroke> keyStrokes) {
    myName=name;
    myKeyStrokes=keyStrokes;
  }
  public @NotNull String getName() {
    return myName;
  }
  public @NotNull List<KeyStroke> getKeyStrokes() {
    return myKeyStrokes;
  }
}
