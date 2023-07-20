package com.jediterm.terminal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.jediterm.core.Color;
import com.jediterm.core.util.TermSize;
import com.jediterm.terminal.emulator.mouse.MouseMode;
import com.jediterm.terminal.model.JediTerminal;

public interface TerminalDisplay {
  // Size information
  int getRowCount();

  int getColumnCount();

  void setCursor(int x, int y);

  void setCursorShape(CursorShape shape);

  void beep();

  void requestResize(@NotNull TermSize newWinSize, RequestOrigin origin, int cursorX, int cursorY,
                     JediTerminal.ResizeHandler resizeHandler);

  void scrollArea(final int scrollRegionTop, final int scrollRegionSize, int dy);

  void setCursorVisible(boolean shouldDrawCursor);

  void setScrollingEnabled(boolean enabled);

  void setBlinkingCursor(boolean enabled);

  String getWindowTitle();

  void setWindowTitle(String name);

  void terminalMouseModeSet(MouseMode mode);

  boolean ambiguousCharsAreDoubleWidth();

  default void setBracketedPasteMode(boolean enabled) {}

  default @Nullable Color getWindowForeground() {
    return null;
  }

  default @Nullable Color getWindowBackground() {
    return null;
  }
}
