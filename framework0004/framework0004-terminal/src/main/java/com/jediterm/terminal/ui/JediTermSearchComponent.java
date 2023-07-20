package com.jediterm.terminal.ui;

import java.awt.event.KeyListener;

import javax.swing.JComponent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.jediterm.terminal.SubstringFinder;

import pama1234.gdx.terminal.DeprecatedAwt;

@DeprecatedAwt
public interface JediTermSearchComponent{
  @NotNull
  JComponent getComponent();
  void addListener(@NotNull JediTermSearchComponentListener listener);
  void addKeyListener(@NotNull KeyListener listener);
  void onResultUpdated(@Nullable SubstringFinder.FindResult results);
}
