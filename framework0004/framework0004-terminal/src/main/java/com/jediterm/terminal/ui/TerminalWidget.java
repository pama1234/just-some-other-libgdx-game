package com.jediterm.terminal.ui;

import java.awt.Dimension;

import javax.swing.JComponent;

import com.jediterm.terminal.TerminalDisplay;
import com.jediterm.terminal.TtyConnector;

import pama1234.gdx.terminal.DeprecatedAwt;

/**
 * @author traff
 */
@DeprecatedAwt
public interface TerminalWidget{
  JediTermWidget createTerminalSession(TtyConnector ttyConnector);
  JComponent getComponent();
  default JComponent getPreferredFocusableComponent() {
    return getComponent();
  }
  boolean canOpenSession();
  void setTerminalPanelListener(TerminalPanelListener terminalPanelListener);
  Dimension getPreferredSize();
  TerminalDisplay getTerminalDisplay();
  void addListener(TerminalWidgetListener listener);
  void removeListener(TerminalWidgetListener listener);
}
