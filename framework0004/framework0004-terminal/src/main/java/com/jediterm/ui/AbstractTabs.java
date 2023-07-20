package com.jediterm.ui;

import java.awt.Component;

import javax.swing.JComponent;

import pama1234.gdx.terminal.DeprecatedAwt;

/**
 * @author traff
 */
@DeprecatedAwt
public interface AbstractTabs<T extends Component>{
  int getTabCount();
  void addTab(String name,T terminal);
  String getTitleAt(int index);
  int getSelectedIndex();
  void setSelectedIndex(int index);
  int indexOfComponent(Component component);
  void removeAll();
  void remove(T terminal);
  void setTitleAt(int index,String name);
  void setSelectedComponent(T terminal);
  JComponent getComponent();
  T getComponentAt(int index);
  void addChangeListener(TabChangeListener listener);
  interface TabChangeListener{
    void tabRemoved();
    void selectionChanged();
  }
}
